This project is built continuously in the cloud powered by [BuildHive](https://buildhive.cloudbees.com).
Click here [![Build Status](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/badge/icon)](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/) to see the latest build result.
Click [here](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/com.scirisk$risk-analyzer-web/ws/target/site/cobertura/index.html) to see the latest code coverage report powered by [Cobertura](http://cobertura.sourceforge.net).
Note that the report might be temporarily unavailable when the build is in progress.

## Risk Analyzer

This repository hosts the source code of a startup project I took part in few months ago.
The application requirement was for a supply chain management system that is accessible through a Web browser. 

The users of the application are supply chain managers who in the course of their work need to view and manage information regarding
the network of suppliers for their company. The application supports the following use cases:

1. View the supply chain, i.e. network of suppliers and the target company
2. View information pertaining to a supplier
3. View information pertaining to a target company
4. Update information pertaining to a supplier
5. Update information pertaining to a target company
6. Add a new supplier to the supply chain
7. Add a new target company to the supply chain
8. Delete an existing supplier
9. Delete an existing target company
10. Export the supply chain to XML
11. Import the supply chain from XML
12. View frequency distribution* analysis performed on the supply chain

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/risk-analyzer.png)

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/node-dialog.png)

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/edge-dialog.png)

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/simulation.png)

## Deployment
The Risk Analyzer application is divided into a set of functional units named modules. These module can be deployed in
various configurations. For instance, the application can be run on a local machine with an embedded HSQL database
as the entities repository, or on Google App Engine with a Big Data storage.

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