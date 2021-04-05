# CloudWrite

This Spring-based application provides authors with a collaborative environment from which to plan research expositions and educational articles.

The packaged jar file is included [here](./JAR/) for demonstration purposes.

### Research themed articles

The model is broken down into entities, as follows:

+ Article type: research exposition
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