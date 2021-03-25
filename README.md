# CloudWrite

This Spring-based application provides authors with a collaborative environment from which to plan research expositions and educational articles.

### Research themed articles

The model is broken down into entities, as follows:

+ Article type: research exposition or highlight
+ Article standfirst/headline
  + Current issue(s) or limitation(s)
  + How above aspect(s) is/are approached
+ Literature survey(s)
  + Purpose of research
  + Current efforts and contributions
  + Key results
  + Future work

### Fundamental based articles

The model is broken down into entities, as follows:

+ Article type: fundamentals
+ Prerequisite knowledge
+ Subtopic 
  + General justification/purpose/problem area 
  + Concept 
  + Summary/future outlook 
    
### Spring Web App features

Current ongoing plans for the app:

+ Spring security authentication (all users have equal roles)
+ H2 in-memory and MySQL persistence  
+ Thymeleaf and Bootstrap templating
+ REST API (JSON), with OpenAPI documentation
+ DOCX file formatter (independent microservice)