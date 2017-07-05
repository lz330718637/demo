package com.springboot.demo.config;

import java.util.Map;
import java.util.Map.Entry;

import com.vpgame.core.config.MysqlDbConfig;

/**
 * 
 * @author <a href="mailto:lizhen@vpgame.cn">LZ</a>
 *@data 2017年7月5号
 */

public class MysqlDbUtil {
	/**
	 * 生成mysql dburl
	 * 
	 * @param dbConfig zk中的mysql配置项
	 * @param extraParams 额外的数据库配置参数
	 * @return mysql连接url
	 */
	public static String generateDbUrl(MysqlDbConfig mysqlConfig,Map<String,String> extraParams){
		//下面定义一个变量用来存放url
		StringBuilder url = new StringBuilder("jdbc:mysql://");
		url.append(mysqlConfig.getHost());
		url.append(":");
		url.append(mysqlConfig.getPort());
		url.append("/");
		url.append(mysqlConfig.getDbname());
		url.append("?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		
		if(null != extraParams){
			for(Entry<String, String> entry : extraParams.entrySet()) {
				url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}
		return url.toString();
	}
	
	public static String generateDbUrl(MysqlDbConfig mysqlDbConfig){
		return generateDbUrl(mysqlDbConfig,null);
	}
}
