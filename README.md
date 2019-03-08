# Goal

this project represents the persistence for model. Is exposes a GRPC API that can be used to create new users, save (update), list and retreive binary blobs from a mongoDB

# Usage

```bash
mvn clean pacakge
java -jar ./target/model-persistence-1.0.0-SNAPSHOT.jar  -Drun.arguments=--local.mongodb.port=27017 --local.mongodb.host=localhost --local.grpc.port=8081
```

to create an executable jar, and expose a GRPC service on the 8081 port. Connects to the specified mongoDB instance.

![BloomGRPC screenshot of the API ](screnshot.png?raw=true "BloomGRPC screenshot of the API")

# Todo
* see issues
