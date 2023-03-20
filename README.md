# Campus02 - BPJ02 Repository

## JSF - JPA Sample project

This project demonstrates how to interact with a database (CRUD) in the context of a web application.

The project uses
* [Eclipse EE](https://www.eclipse.org/downloads/packages/) (or a java development environment of your choice)
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi)
  * Download the `Core` version
  * Extract it to a folder of your choice

The following components are used - you do not need to download them
* [Apache Maven](https://maven.apache.org/) for build and dependency management (is part of Eclipse EE)
* [Hibernate](http://www.hibernate.org) - as OR mapper
* [H2 database](http://www.h2database.com) - as a leighweight local database
* [Primefaces](http://www.primefaces.org) - also visit the [ShowCase](https://www.primefaces.org/showcase/) - for UI
* [JUnit](http://junit.org) - for Unit Testing

## Get started in Eclipse

* Import the project to Eclipse
  * File - Import - Existing Maven Projects
* Integrate Apache Tomcat
  * Window - Show view - Servers
  * Right click in the Servers window and select
    * New - Server - Apache Tomcat 9
* Add your project to the server
  * Right click on the server - Add and remove ...
* Start the Apache Tomcat Server
  * Right click - Debug

Tomcat will be listening on port 8080.
* Open your browser and navigate to the [BP2 project start page](http://localhost:8080/bp2/)

## Fruther reading

A great resource for JSF is
* [JSF at work](http://jsfatwork.irian.at/book_de/introduction.html)