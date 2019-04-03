DOCKER_IMAGE_NAME=nherbaut/variamos-model-persistence
DOCKER_IMAGE_TAG=latest

all: build run

# rereate all the java goodies and package them as docker image
build: java docker

# clean up every generated artefacts, including docker volumes, java binaries and docker images
clean: stop 
	@sudo rm -rf ./mongo-volume
	@MONGO_VOLUME=$$(realpath ./mongo-volume) docker-compose down
	@mvn clean
	@sudo docker rmi -f $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)
	
# build the java binaries
java:
	@mvn package

# package the java binaries as docker images
docker: java
	@sudo docker build . -t $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

# push docker images in the docker hub
push:
	@sudo docker push $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

# launch the project and a mongodb instance through docker and docker compose
run: 
	@mkdir -p ./mongo-volume
	@MONGO_VOLUME=$$(realpath ./mongo-volume) docker-compose up

# stop the projet through docker compose
stop:
	@MONGO_VOLUME=$$(realpath ./mongo-volume) docker-compose down

doc:
	@echo generating doc
	@sudo docker run --rm -v $(PWD)/doc:/out -v $(PWD)/src/main/proto:/protos   pseudomuto/protoc-gen-doc
	@sudo chown -R $(USER):$(USER) ./doc
	@python -m webbrowser file://$(PWD)/doc/index.html


