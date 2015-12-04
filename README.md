v2020
=====

A protoype application with REST interface powered by Neo4j and Spring Boot.

Build and run
-------------

*Prerequisite:* Download install and run a Neo4j: [http://neo4j.com/download/](http://neo4j.com/download/)

*Clone and build project:*
```bash
git clone https://github.com/murygin/v2020.git
cd v2020
mvn package
```

*Import data:*
```bash
cd v2020-vna-import
java -jar target/v2020-vna-import-0.1.0-SNAPSHOT.jar -f ../v2020-service/src/test/resources/BusinessImpactInheritenceTest.vna
```

*Start REST web service:*
```bash
cd ../v2020-rest
java -jar target/v2020-rest-0.1.0-SNAPSHOT.ja
```

Open [http://localhost:8080/v2020/node/1](http://localhost:8080/v2020/node/0) to load node with id 1.
