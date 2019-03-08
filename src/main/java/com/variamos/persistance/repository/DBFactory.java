package com.variamos.persistance.repository;

import java.net.UnknownHostException;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
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
		try {
			return Mongo.connect(new DBAddress(mongoHost, mongoPort, "variamos-" + userName));
		} catch (UnknownHostException e) {
			throw new BeanCreationException("failed to create connection to the DB");
		}
	}

	public RepositoryFactorySupport getRepositoryFactory(String userName) {

		MongoDbFactory fact = new SimpleMongoDbFactory(this.getInstance(userName).getMongo(), "variamos-" + userName);
		MongoOperations operations = new MongoTemplate(fact);
		return new MongoRepositoryFactory(operations);
	}

}
