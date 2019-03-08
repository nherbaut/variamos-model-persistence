package com.variamos.data.mongo;

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
import io.grpc.Metadata.Key;
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
		Map<String, Object> commandArguments = new HashMap<String, Object>();
		commandArguments.put("createUser", request.getName());
		commandArguments.put("pwd", "password123");
		String[] roles = { "readWrite" };
		commandArguments.put("roles", roles);
		BasicDBObject command = new BasicDBObject(commandArguments);
		CommandResult res = db.command(command);

		if (!res.ok()) {

		
			responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.augmentDescription(res.getErrorMessage())));
			return;
		}

		responseObserver.onNext(Empty.newBuilder().build());
		responseObserver.onCompleted();
	}

}
