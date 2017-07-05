package com.springboot.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.vpgame.core.config.MysqlDbConfig;
import com.vpgame.core.utils.ZkConfigClient;

@Configuration
public class DataSourceConfig {
	
	@Resource
	private CustomProperty customProperty;
	
	@Bean(name="primaryDataSource")
	@Qualifier("primaryDataSource")
	@ConfigurationProperties(prefix="spring.datasource.primary")
	public DataSource primaryDataSource(){
		//这里的数据我想直接从zookeeper中的节点中获取,这里我使用峻峰已经封装好的zkclient类来链接本地的zk
		//这里获取一个新的zk客户端连接zk
		ZkConfigClient zkConfigClient = new ZkConfigClient(customProperty.getZookeeperUrl());
		//下面通过ZkConfigClient中的getMysqlConfig
		MysqlDbConfig mysqlConfig = zkConfigClient.getMysqlDbConfig("springboot");
		
		//下面的配置是什么我暂时还不知道
		 Map<String, String> dbUriParam = new HashMap<>();
	     dbUriParam.put("zeroDateTimeBehavior", "convertToNull");
		//下面开始生成相应的datasource
		return (DataSource) DataSourceBuilder.create()
				//这里创建DataSource时，可以在这里面加入获取的数据库的url，username，password等配置数据
				.url(MysqlDbUtil.generateDbUrl(mysqlConfig,dbUriParam))
				.username(mysqlConfig.getUsername())
				.password(mysqlConfig.getPassword())
				.build();
	}
	
	
	@Bean(name="secondaryDataSource")
	@Qualifier("secondaryDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource.secondary")
	public DataSource secondaryDataSource(){
		return (DataSource) DataSourceBuilder.create()
				.build();
	}
}
