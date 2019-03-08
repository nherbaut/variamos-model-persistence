package com.variamos.persistance.repository;

import java.io.Serializable;

import org.bson.types.Binary;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.google.protobuf.ByteString;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.variamos.persistence.Document;

import com.variamos.persistence.Empty;
import com.variamos.persistence.User;
import com.variamos.persistence.DocumentRepositoryGrpc.DocumentRepositoryImplBase;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

@GRpcService
public class DocumentRepositoryServiceImpl extends DocumentRepositoryImplBase {

	@Autowired
	DBFactory factory;

	@Override
	public void listDocuments(User request, StreamObserver<Document> responseObserver) {

		FileRepository repo = factory.getRepositoryFactory(request.getName()).getRepository(FileRepository.class);

		for (com.variamos.persistance.entity.Document doc : repo.findAll()) {
			responseObserver.onNext(Document.newBuilder().setPath(doc.getPath()).setOwner(request).build());
		}
		responseObserver.onCompleted();

	}

	@Override
	public void getDocument(Document request, StreamObserver<Document> responseObserver) {

		FileRepository repo = factory.getRepositoryFactory(request.getOwner().getName())
				.getRepository(FileRepository.class);

		com.variamos.persistance.entity.Document doc = repo.findOne(request.getPath());
		if (doc == null) {
			responseObserver.onError(new StatusException(Status.NOT_FOUND));
			return;
		}
		Document response = Document.newBuilder(request).setPayload(ByteString.copyFrom(doc.getFile().getData())).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void saveDocument(Document request, StreamObserver<Empty> responseObserver) {
		FileRepository repo = factory.getRepositoryFactory(request.getOwner().getName())
				.getRepository(FileRepository.class);

		Binary bin = new Binary(request.getPathBytes().toByteArray());
		com.variamos.persistance.entity.Document doc = new com.variamos.persistance.entity.Document();
		doc.setFile(bin);
		doc.setPath(request.getPath());
		
			repo.save(doc);
			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		

	}
}
