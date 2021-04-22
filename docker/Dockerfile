FROM centos 
 RUN yum install -y java-11-openjdk-devel 
 VOLUME /tmp
 ADD /cloudwrite-0.0.1-SNAPSHOT.jar cloudWrite.jar
 RUN sh -c 'touch /cloudWrite.jar'
 ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloudWrite.jar"]
