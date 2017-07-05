//package com.springboot.demo.config;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.Watcher.Event.KeeperState;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
///**
// * ]
// * 
// * @author <a href="mailto:lizhen@vpgame.cn>LZ</a>
// * @data 2017年7月5号
// *
// */
//
//public class GenerateZKNodeAndData implements Watcher {
//	/**
//	 * 生成本地的ZKnode和相应的数据库数据
//	 */
//	public ZooKeeper zookeeper;
//	private static int SESSION_TIME_OUT = 2000;
//	private CountDownLatch countDownLatch = new CountDownLatch(1);
//
//	/**
//	 * 连接zookeeper
//	 * 
//	 * @param host
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	public void connectZookeeper(String host) throws IOException, InterruptedException {
//		zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
//		countDownLatch.await();
//		System.out.println("zookeeper connect ok");
//	}
//
//	/**
//	 * 实现watcher的接口方法，当连接zookeeper成功后，zookeeper会通过此方法通知watcher
//	 * 此处为如果接受到连接成功的event，则countDown，让当前线程继续其他事情。
//	 */
//	@Override
//	public void process(WatchedEvent event) {
//		if (event.getState() == KeeperState.SyncConnected) {
//			System.out.println("watcher receiver event");
//			countDownLatch.countDown();
//		}
//	}
//
//	/**
//	 * 根据路径创建节点，并且设置节点数据
//	 * 
//	 * @param path
//	 * @param data
//	 * @return
//	 * @throws KeeperException
//	 * @throws InterruptedException
//	 */
//	public String createNode(String path, byte[] data) throws KeeperException, InterruptedException {
//		return this.zookeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//	}
//
//	/**
//	 * 根据路径获取所有孩子节点
//	 * 
//	 * @param path
//	 * @return
//	 * @throws KeeperException
//	 * @throws InterruptedException
//	 */
//	public List<String> getChildren(String path) throws KeeperException, InterruptedException {
//		return this.zookeeper.getChildren(path, false);
//	}
//
//	public Stat setData(String path, byte[] data, int version) throws KeeperException, InterruptedException {
//		return this.zookeeper.setData(path, data, version);
//	}
//
//	/**
//	 * 根据路径获取节点数据
//	 * 
//	 * @param path
//	 * @return
//	 * @throws KeeperException
//	 * @throws InterruptedException
//	 */
//	public byte[] getData(String path) throws KeeperException, InterruptedException {
//		return this.zookeeper.getData(path, false, null);
//	}
//
//	/**
//	 * 删除节点
//	 * 
//	 * @param path
//	 * @param version
//	 * @throws InterruptedException
//	 * @throws KeeperException
//	 */
//	public void deleteNode(String path, int version) throws InterruptedException, KeeperException {
//		this.zookeeper.delete(path, version);
//	}
//
//	/**
//	 * 关闭zookeeper连接
//	 * 
//	 * @throws InterruptedException
//	 */
//	public void closeConnect() throws InterruptedException {
//		if (null != zookeeper) {
//			zookeeper.close();
//		}
//	}
//
//	// 下面是定义主方法，然后运行该类去生成相应的ZKnode和相应的数据
//	public static void main(String args[]) throws IOException, InterruptedException, KeeperException {
//		// 建立一个客户端连接ZK
//		GenerateZKNodeAndData client = new GenerateZKNodeAndData();
//		String host = "localhost:2181";
//
//		// 下面开始连接ZK
//		client.connectZookeeper(host);
//
//		// 连接好了以后现在开始创建节点和数据,这里的数据我在这里先定义好,这里给出的接口里必须放byte类型的数组,
//		//这里创建一次过后，就不要在创建了，否则会报异常，所以这里我将创建节点的代码注释了
//		String str = "{\"username\":\"lizhen\",\"password\":\"lizhen\",\"host\":\"127.0.0.1\",\"dbname\":\"springboot\",\"port\":3306}";
//		byte[] data = str.getBytes();
//		//String isCreate = client.createNode("/config/mysqldb/springboot", data);
//		//因为之前弄的数据库密码弄成lizhen1992815了，正确是lizhen，所以这里需要将节点的数据set一下，经过测试，目前数据已经修改过来了，现在将更新数据的代码注释掉，否则每更新一次对应的dataversion就会增加一次
////		Stat stat = client.setData("/config/mysqldb/springboot", data, 0);
////		// 这里返回节点的路径
////		System.out.println(stat);
//
//		// 下面是从zk中获取数据
//		byte[] mydata = client.getData("/config/mysqldb/springboot");
//		String result = new String(mydata);
//		System.out.println(result);
//	}
//}
