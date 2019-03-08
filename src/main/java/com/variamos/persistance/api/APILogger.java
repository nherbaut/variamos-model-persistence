package com.variamos.persistance.api;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

@GRpcGlobalInterceptor
public class APILogger implements ServerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(APILogger.class);

	@Override
	public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {
		LOG.info(headers.toString());
		return next.startCall(call, headers);
	}

}
