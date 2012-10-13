## Risk Analyzer

This repository hosts the source code of a start-up project I took part in few months ago.
The application requirement was for a supply chain management system that is accessible through a Web browser.

The users of the application are supply chain managers who in the course of their work need to view and manage information regarding
a distribution network, i.e. the network of facilities (suppliers and target companies) and distribution channels (paths from one facility to another).
The application supports the following use cases:

1. View the distribution network
2. View information pertaining to an existing facility
3. View information pertaining to an existing distribution channel
4. Update information pertaining to an existing facility
5. Update information pertaining to an existing distribution channel
6. Add a new facility to the distribution network
7. Add a new distribution channel to the distribution network
8. Delete an existing facility
9. Delete an existing distribution channel
10. Export the distribution network to XML
11. Import the distribution network from XML
12. View frequency distribution analysis performed on the distribution network

Risk Analyzer presents information using an [Ext JS 4.1](http://www.sencha.com/products/extjs)-based graphical user interface
in combination with [Spring 3.1.x](http://static.springsource.org/spring/docs/3.1.x/spring-framework-reference/html),
[Hibernate](http://www.hibernate.org), RESTful Web Services, and a custom network analysis engine exposing the SOAP interface.
The result is a visually rich, enterprise system that is both quick and secure. It is fully controlled, tested,
and deployed through a continuous integration infrastructure powered by [BuildHive](https://buildhive.cloudbees.com).
You can see the latest build status by clicking here
[![Build Status](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/badge/icon)](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/). 

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/risk-analyzer.png)

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/node-dialog.png)

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/edge-dialog.png)

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/simulation.png)

## Deployment
The Risk Analyzer application is divided into a set of functional units named modules. These module can be deployed in
various configurations. For instance, the application can be run on a local machine with the [relational database (RDBMS)]
(http://en.wikipedia.org/wiki/Relational_database) as data storage, or on Google App Engine with its schemaless
[High Replication Datastore (HRD)](https://developers.google.com/appengine/docs/java/datastore/overview).

The most common deployment configurations are described in the following sections.

### Deploying Risk Analyzer on a Local Machine

1. Create a working directory, for example C:\RiskAnalyzer, hereafter referred to as RISK_ANALYZER_HOME.
2. Create a sub-directory for the Backend Web application in RISK_ANALYZER_HOME\backed.
3. Create a sub-directory for the Frontend Web application in RISK_ANALYZER_HOME\frontend.
4. Downlaod the [Backend Web](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/lastStableBuild/com.scirisk$risk-analyzer-backend-web/artifact/com.scirisk/risk-analyzer-backend-web/0.0.1-SNAPSHOT/risk-analyzer-backend-web-0.0.1-SNAPSHOT-exec-war.jar) application to RISK_ANALYZER_HOME\backend.
5. Download the [Frontend Web](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/lastStableBuild/com.scirisk$risk-analyzer-web/artifact/com.scirisk/risk-analyzer-web/0.0.1-SNAPSHOT/risk-analyzer-web-0.0.1-SNAPSHOT-exec-war.jar) application to RISK_ANALYZER_HOME\frontend.
6. Launch the Backend Web application by executing java -jar risk-analyzer-backend-web-0.0.1-SNAPSHOT-exec-war.jar -httpPort 9090
7. Open this URL [http://localhost:9090/risk-analyzer-backend-web/soap/risk-analyzer.wsdl](http://localhost:9090/risk-analyzer-backend-web/soap/risk-analyzer.wsdl) in your favorite Web browser to make sure that the backend application is up and running.
8. Launch the Frontend Web application by executing java -jar risk-analyzer-web-0.0.1-SNAPSHOT-exec-war.jar
9. Open this URL [http://localhost:8080/risk-analyzer-web](http://localhost:8080/risk-analyzer-web) in your favorite Web browser to make sure that the frontend application is up and running.

### Deploying Risk Analyzer on Google App Engine

[http://risk-analyzer-frontend.appspot.com](http://risk-analyzer-frontend.appspot.com)

### Deploying Risk Analyzer on CloudBees

[Frontend Web](http://risk-analyzer-frontend-web.pacak-daniel.cloudbees.net)

[Backend Web](http://risk-analyzer-backend-web.pacak-daniel.cloudbees.net/soap/risk-analyzer.wsdl)

