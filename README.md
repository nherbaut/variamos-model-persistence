

# Goal

this project represents the persistence for models. Is exposes a GRPC API that can be used to create new users, save (update), list and retreive binary blobs from a mongoDB

# usage

## wipe up everything
```bash
make clean
```
## compile the java and the docker image

```bash
make build
```

## run the docker containers

```bash
make run
```

## stops docker containers

```bash
make stop
```

# prerequisites

## running

* docker
* docker compose
* python

## compiling and developing

* gnu make
* ~~protocolbuffer tools~~ should be downloaded automatically through maven
* mongodb (for development only)
* java 10 (for development only)
* maven (for development only)

# client tools

* I used [BloomRPC](https://github.com/uw-labs/bloomrpc) for debug 
![BloomGRPC screenshot of the API ](screnshot.png?raw=true "BloomGRPC screenshot of the API")

# Todo
* see issues
