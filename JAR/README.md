## Packaged CloudWrite

Run the jar from the command line:

```bash
java -jar -Djava.security.egd=file:/dev/./urandom cloudwrite-0.0.1-SNAPSHOT.jar
```

From a browser, go to [localhost:5000](http://localhost:5000) (port selection is set in [application.properties](../src/main/resources/application.properties)) and enter one of the login details:

+ Admin user: "admin" with password "admin123"
+ User: "user" with password "user123"

The usernames and passwords can be set in the [DataLoader class](../src/main/java/com/example/cloudwrite/bootstrap/DataLoader.java#L133) on repackage.
