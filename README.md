v2020
=====

A protoype application with REST interface powered by Neo4j and Spring Boot.

Development of this application ist stoped because of a missing feature in Spring Data Neo4j: [DATAGRAPH-555
Support DynamicProperties](https://jira.spring.io/browse/DATAGRAPH-555).

Build and run
-------------

*Prerequisite:*
* Download install and run a Neo4j: [http://neo4j.com/download/](http://neo4j.com/download/)

*Clone project:*
```bash
git clone https://github.com/murygin/v2020.git
cd v2020
```
Set your Neo4j server connection properties in class _v2020-data-neo4j/src/main/java/org/v2020/data/DataNeo4jConfiguration.java_

*Build project:*
```bash
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
