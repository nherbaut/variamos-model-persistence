DOCKER_IMAGE_NAME=nherbaut/variamos-model-persistence
DOCKER_IMAGE_TAG=latest

all: build run

build: java docker

clean: stop
	@sudo rm -rf ./mongo-volume
	@MONGO_VOLUME=$$(realpath ./mongo-volume) docker-compose down
	@mvn clean
	@docker rmi -f $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)
	
java:
	@mvn package

docker: java
	@docker build . -t $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

push:
	@docker push $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)

run: 
	@mkdir -p ./mongo-volume
	@MONGO_VOLUME=$$(realpath ./mongo-volume) docker-compose up

stop:
	@MONGO_VOLUME=$$(realpath ./mongo-volume) docker-compose down