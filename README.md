# Smith
Team allocation API.

###info
Written in Java, using Springboot, Maven and an in-memory h2 database

###environment setup
1) Ensure maven (https://maven.apache.org/) is installed on deployment server/local machine
2) From a terminal/command line, navigate to project root (you should see pom.xml file)
3) Execute 'mvn clean install'

###deployment - linux
From the project root, execute `./mvnw spring-boot:run`.
###deployment - windows
From the project root, execute `mvnw.cmd spring-boot:run`.
* <b>default url:</b> http://localhost:8080
###how to use api
See API documentation: 
* <b>See API documentation:</b> http://<host>:<port>/swagger-ui.html (e.g.http://localhost:8080/swagger-ui.html)