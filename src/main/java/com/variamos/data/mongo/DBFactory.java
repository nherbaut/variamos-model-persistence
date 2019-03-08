package com.variamos.data.mongo;

import java.net.UnknownHostException;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Component
public class DBFactory {

	@Autowired
	@Value("${local.mongodb.host}")
	private String mongoHost;

	@Autowired
	@Value("${local.mongodb.port}")
	private int mongoPort;

	@Bean
	public ServerAddress getServerAddress() {
		try {
			return new ServerAddress(mongoHost, mongoPort);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	
	public DB getInstance(String userName) {
		MongoClient client = new MongoClient(getServerAddress());
		return client.getDB("variamos-" + userName);
	}

}
