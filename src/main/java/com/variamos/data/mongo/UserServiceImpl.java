package com.variamos.data.mongo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.variamos.persistence.Empty;
import com.variamos.persistence.User;
import com.variamos.persistence.UserServiceGrpc.UserServiceImplBase;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

@GRpcService(interceptors = MongoInterceptor.class)
public class UserServiceImpl extends UserServiceImplBase {

	@Autowired
	DBFactory factory;

	@Override
	public void createUser(User request, StreamObserver<Empty> responseObserver) {

		DB db = factory.getInstance(request.getName());
		final BasicDBObject createUserCommand = new BasicDBObject("createUser", request.getName()).append("pwd", "mypassword")
				.append("roles",
						Collections.singletonList(new BasicDBObject("role", "dbOwner").append("db", "variamos-"+request.getName())));
		CommandResult res = db.command(createUserCommand);

		if (!res.ok()) {

			responseObserver
					.onError(new StatusRuntimeException(Status.INTERNAL.augmentDescription(res.getErrorMessage())));
			return;
		}

		responseObserver.onNext(Empty.newBuilder().build());
		responseObserver.onCompleted();
	}

}
