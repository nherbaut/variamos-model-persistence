from java:8
COPY target/variamos-model-persistence.jar /root  
CMD java -jar /root/variamos-model-persistence.jar -Drun.arguments=--local.mongodb.port=${MONGO_PORT} --local.mongodb.host=${MONGO_HOST} --local.grpc.port=${GRPC_PORT}