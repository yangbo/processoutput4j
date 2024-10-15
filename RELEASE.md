How to make a release
=====================

* Switch to Java 9

* Run the following command to deploy the artifact:

  ```
  mvn release:clean release:prepare release:perform
  ```

* Push all changes

ChangeLog
=====================

* 2024-10-15 Bob

  Make the jar to JDK9 module named 'com.github.fracpete.processoutput4j'.
  Change the source and target level to 1.9.
