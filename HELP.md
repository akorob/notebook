Simple notes storage app via spring boot, h2 and angularjs. Implements db search and paging.
Run app via gradle run task or as jar and go to://localhost:8080.

- gradlew.bat bootRun

- gradlew.bat build
  cd build\libs\
  java -jar notebook-0.0.1-SNAPSHOT.jar

H2-console here http://localhost:8080/h2-console. Make sure that jdbc url is jdbc:h2:mem:testdb.
