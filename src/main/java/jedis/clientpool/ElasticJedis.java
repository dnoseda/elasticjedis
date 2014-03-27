package jedis.clientpool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

import redis.clients.jedis.JedisShardInfo;
/**
 * TODO: muchos master y shardeado
 * @author dnoseda
 *
 */
public class ElasticJedis {
	static List<JedisShardInfo> slaves = new CopyOnWriteArrayList<JedisShardInfo>();
	static ThreadLocal<JedisShardInfo> master = new ThreadLocal<JedisShardInfo>();
	static Thread refresher = new Thread(new Refresher());
	public static class Refresher implements Runnable{

		public void run() {
			// TODO trae del supplier
			// verifica cual esta vivo
			/**
			 * 1) master ok? sino promuevo uno, y reapunto todos
			 * 2) reviso slaves
			 * 	2.2) slave ok? sino, lo saco
			 * 3) traigo del supplier, los que no tengo los verifico y los pongo como slave
			 */
			while(true){
				
					System.out.println("corro");
					sleep(1000);
				
				
			}
			
		}
		
	}
	boolean isRead(String operation){
		// si es de lectura o escritura, de escritura al master siempre, de lectura a cualquiera
		return true;
	}
	private Supplier<List<JedisShardInfo>> supplier;
	public ElasticJedis(Supplier<List<JedisShardInfo>> supplier) {
		refresher.start();
		this.supplier = supplier;
	}
	public static void main(String[] args) {
		ElasticJedis ej = new ElasticJedis(new Supplier<List<JedisShardInfo>>() {

			public List<JedisShardInfo> get() {
				List<JedisShardInfo> list = Lists.newArrayList();
				// TODO: pegarle a la api de melicloud para obtener la lista de redis de la app
				list.add(new JedisShardInfo("localhost"));
				return list;
			}
		});
		
		while(true){
			
			sleep(100000);
		}
	}
	public static void sleep(int s){
		try{
			Thread.sleep(s);
		}catch(Exception e){
		}
	}
	

}
