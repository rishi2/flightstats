## wrsflightservice

This service exposes endpoints to get flight related information for flight travellers. 
Currently this service exposed following endpoints:
* Flight status by Departure Date ( Using GRPC)
* Flight Alert by Departure (Using GRPC)
* HTTP Flight Notification endpoint and its GRPC client which will be consumed by different service.

```
Mentioned services can be exposed by HTTP/JSON endpoint and GRPC service-method for submitting request.
```
### This service requires the following to build and run:

* Access to the `github.com/wrsinc/protobuf` repo **or** the artifact (`protobuf-0.0.2-SNAPSHOT.jar`) built from it.
* Access to the `wrs.jfrog.io` Artifactory/JFrog repos.
* A machine set up with the software described on this [WRS-Wiki page](https://wiki.westfieldlabs.com/display/WL/Dev+Environment) 

### To build the service:
To build and run any unit-tests:
```
mvn clean install
```

To build and skip any unit-tests:
```
mvn clean install -DskipTests
```

### To start the service:

1. Start the PostGres database server.
```
brew services start postgresql
```

2. Create the **flightAlertRule** database, if needed.
```
createdb flightAlertRule
```

3. Start the **wrsflightservice** service.
```
mvn spring-boot:run
```

### To stop the service:

1. Stop the **wrsflightservice** service.
```
Control+C in the shell that you started the service in.
```

2. Stop the PostGres database server, if desired.
```
brew services stop postgresql
```

### Implementation details

* See the GRPC/protobuf definitions for this service in [github.com/wrsinc/protobuf/flight](https://github.com/wrsinc/protobuf/tree/master/flight)
* See the HTTP/JSON definitions for this service in [github.com/wrsinc/protobuf/generated/swagger/src/flight/service](https://github.com/wrsinc/protobuf/blob/master/generated/swagger/src/flight/service/retailer_service.swagger.json)
