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

## Running Risk Analyzer
1. Create the working directory, e.g. C:\RiskAnalyzer - further referred to as RISK_ANALYZER_HOME
2. Create two subdirectories for backedn and fronted applications, RISK_ANALYZER_HOME\backed and RISK_ANALYZER_HOME\frontend respectively
1. Downlaod [Risk Analyzer Backend Web App](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/lastStableBuild/com.scirisk$risk-analyzer-backend-web/artifact/com.scirisk/risk-analyzer-backend-web/0.0.1-SNAPSHOT/risk-analyzer-backend-web-0.0.1-SNAPSHOT-exec-war.jar) to the RISK_ANALYZER_HOME\backend directory.
2. Download [Risk Analyzer Web App](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/lastStableBuild/com.scirisk$risk-analyzer-web/artifact/com.scirisk/risk-analyzer-web/0.0.1-SNAPSHOT/risk-analyzer-web-0.0.1-SNAPSHOT-exec-war.jar) to the RISK_ANALYZER_HOME\frontend directory.
3. Launch Risk Analyzer Backend Web App by executing java -jar risk-analyzer-backend-web-0.0.1-SNAPSHOT-exec-war.jar -httpPort 9090
4. Open this URL http://localhost:9090/risk-analyzer-backend-web/soap/risk-analyzer.wsdl in your favorite Web browser to make sure that the backend application is up and running.
5. Launch Risk Analyzer Web App by executing java -jar risk-analyzer-web-0.0.1-SNAPSHOT-exec-war.jar
6. Open this URL http://localhost:8080/risk-analyzer-web in your favorite Web browser to make sure that the frontend application is up and running.