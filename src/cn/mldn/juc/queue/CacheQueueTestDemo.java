package cn.mldn.juc.queue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 数据缓存模型基础设计
 * @author lishangxing
 */
public class CacheQueueTestDemo {
	public static void main(String[] args) throws Exception {
		Cache<String, News> cache = new Cache<String, News>();
		cache.put("小月月", new News("小月月", "这是一个很好的新闻消息！"), 3, TimeUnit.SECONDS);
		cache.put("小兔子", new News("小兔子", "你是一个很乖的小兔子哦。"), 6, TimeUnit.SECONDS);
		System.out.println(cache.get("小月月"));
		TimeUnit.SECONDS.sleep(5);
		System.out.println(cache.get("小月月"));
		System.out.println(cache.get("小兔子"));
	}
}
/**
 * 定义缓存操作类：该类需要用户设置保存的key类型与value类型
 * @author lishangxing
 * @param <K> 新闻的标题
 * @param <V> 新闻的对象内容
 */
class Cache<K, V> {
	// 如果需要考虑多个线程并发访问操作，则必须使用ConcurrentHashMap子类
	private ConcurrentMap<K, V> cacheObjectMap = new ConcurrentHashMap<>();
	private DelayQueue<DelayItem<Pair>> delayQueue = new DelayQueue<DelayItem<Pair>>();
	public Cache() { // 如果要想清空不需要的缓存数据，则需要使用守护线程
		Runnable daemonTask = () -> {
			while (true) { // 守护线程需要一直执行，当已经超时之后可以取出数据
				DelayItem<Pair> item = Cache.this.delayQueue.poll(); // 通过延迟队列弹出数据
				if (item != null) { // 已经有超时数据了
					Pair pair = item.getItem();
					Cache.this.cacheObjectMap.remove(pair.key, pair.value);
				}
			}
		};
		Thread daemonThread = new Thread(daemonTask, "缓存守护线程");
		daemonThread.setDaemon(true); // 设置为守护线程
		daemonThread.start(); // 启动守护线程
	}
	class Pair { // 定义内部类，该类可以保存队列之中的K与V类型
		private K key;
		private V value;
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	/**
	 * 将数据写入到缓存之中，如果一个对象被重复保存了，则应该重置缓存的失效时间
	 * @param key 要写入的K的内容
	 * @param value 要入的V的内容
	 * @param time 保存的时间
	 * @param unit 保存的时间单位
	 */
	public void put(K key, V value, long time, TimeUnit unit) {
		// 如果发现原本的key存在，则会用新的内容替换旧的内容，同时返回旧的内容
		V oldValue = this.cacheObjectMap.put(key, value); // 将数据保存进去
		if (oldValue != null) { // 原本的内容已经保存过了
			this.delayQueue.remove(key);
		}
		this.delayQueue.put(new DelayItem<Pair>(new Pair(key, value), time, unit));
	}
	public V get(K key) { // 根据key获取内容
		return this.cacheObjectMap.get(key); // Map集合根据key查询
	}
}
/**
 * 数据缓存类别
 * @author lishangxing
 * @param <T> 从外部传入的缓存类别
 */
class DelayItem<T> implements Delayed {
	private T item; // 设置要保存的数据内容
	private long delay; // 设置保存的缓存时间
	private long expire; // 设置缓存数据的失效时间
	public DelayItem(T item, long delay, TimeUnit unit) {
		this.item = item;
		this.delay = TimeUnit.MILLISECONDS.convert(delay, unit);
		this.expire = System.currentTimeMillis() + this.delay;
	}

	@Override
	public int compareTo(Delayed o) {
		return (int) (this.delay - this.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
	
	public T getItem() {
		return this.item;
	}
}
class News {
	private String title;
	private String note;
	public News(String title, String note) {
		this.title = title;
		this.note = note;
	}
	public String toString() {
		return "【新闻数据】title = " + this.title + "、note = " + this.note;
	}
}
