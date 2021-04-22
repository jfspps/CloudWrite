[![CircleCI](https://circleci.com/gh/jfspps/CloudWrite.svg?style=svg)](https://circleci.com/gh/jfspps/CloudWrite)

# CloudWrite

This Spring-based application provides authors with a collaborative environment from which to plan research expositions and educational articles.

Commits from [this point](https://github.com/jfspps/CloudWrite/tree/a3a04eba5478a445bcd0f580ab79dc5e474642a2) onwards 
use JAXB2 to generate DTO classes needed to run CloudWrite; see the [XSD files](./src/main/resources/xsd/). These classes
will be flagged as missing within an IDE. To proceed, run package from maven (or within an IDE) to generate the DTO classes
(the generated classes will be saved in the /target/generated-sources/jaxb directory).

The packaged jar file is included [here](./JAR/) for demonstration purposes.

## Running CloudRead (client) with CloudWrite (service)

[CloudRead](https://github.com/jfspps/CloudRead) is a REST client for CloudWrite and grants all users read-only access to the article plans. It consumes and maps XML to Java objects and (i) presents them in a browser or (ii) can export articles as DOCX files.

Download copies of the JAR directories and subdirectories present for both [CloudRead](https://github.com/jfspps/CloudRead/tree/main/JAR) and [CloudWrite](https://github.com/jfspps/CloudWrite/tree/main/JAR). Run both JARs in separate terminals as outlined in their respective READMEs. Then access localhost port 5000 for CloudWrite or localhost port 8080 for CloudRead.

To build a CloudWrite Docker image, follow the guidelines [here](https://github.com/jfspps/CloudWrite/tree/main/docker/README.md). To build a CloudRead image and then run both as networked containers, go [here](https://github.com/jfspps/CloudRead/tree/main/docker/README.md)).

### Research themed articles

The model is broken down into entities, as follows:

+ Article type: research article
+ Article standfirst/headline
  + Reasons/rationale for research
  + How above aspect(s) is/are approached
+ Literature survey(s)
  + Purpose of research
  + Current efforts and contributions
  + Key results (these can be listed by priority)
  + Future work
+ Citations (literature references)  

### Fundamental based articles

The model is broken down into entities, as follows:

+ Article type: fundamentals
+ Prerequisite knowledge
+ Subtopic 
  + General justification/purpose/problem area 
  + Concept(s)
  + Summary/future outlook 
    
### Spring Web App features

Currently features available:

+ Spring security authentication (admin in place to add/remove/edit user accounts; all users can edit all articles)
+ H2 in-memory and MySQL persistence  
+ Thymeleaf and Bootstrap templating
+ REST API (JSON and XML), with OpenAPI (formerly Swagger) documentation

### RESTful API

Access to the RESTapi is provided by OpenAPI (formerly Swagger), at 
[http://localhost:5000/swagger-ui.html](http://localhost:5000/swagger-ui.html). The port number by default is 8080 but
was changed to 5000 in preparation for potential AWS deployment. The port number can be changed in [application.properties](/src/main/resources/application.properties).

XML schema files (XSD) were initially generated from XML GET requests and converted with online XSD generators,
[FreeFormatter](https://www.freeformatter.com/xsd-generator.html), to XSD formats before manual refinement. The
XSD files can be used by other frameworks and languages to generate classes which align with CloudWrite's classes and 
allow other REST clients to consume XML returns from CloudWrite.

The conversion of Java object data to XML is referred to as 'marshalling'. The conversion of XML data to Java object data 
employed by REST clients is referred to as 'un-marshalling'.

### MySQL persistence

Data can be saved to a MySQL database by triggering the 'SQL' annotation in [application.properties](/src/main/resources/application.properties). 
 The SQL scripts for the user account and tables are provided in the [SQLscript directory](/src/main/resources/SQLscript). 
Changes to the [MySQL user account](/src/main/resources/SQLscript/SQLsetup.sql) must match those provided in the [application-SQL.yml](/src/main/resources/application-SQL.yml) file.
