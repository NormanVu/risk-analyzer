# Risk Analyzer

* [Introduction](#introduction)
* [Packages](#packages)
* [Screenshots](#screenshots)
* [Building](#building)
* [Deploying](#deploying)
 * [Deploying locally](#deploying-locally)
 * [Deploying on Google App Engine](#deploying-on-google-app-engine)
 * [Deploying on CloudBees](#deploying-on-cloudbees)

## Introduction
The application requirement was for a supply chain management system that is accessible through a Web browser.

The users of the application are supply *chain managers* who in the course of their work need to view and manage information regarding
a *distribution network*, i.e. the network of *facilities* (suppliers and target companies) and *distribution channels* (paths
from one facility to another). The application supports the following use cases:

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
[Hibernate](http://www.hibernate.org), and a custom network analysis engine implementing the REST API.
The result is a visually rich, enterprise system that is both quick and secure. It is fully controlled, tested,
and deployed through a continuous integration infrastructure powered by [BuildHive](https://buildhive.cloudbees.com).
You can see the latest build status by clicking here
[![Build Status](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/badge/icon)](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/). 

## Packages
The project is composed of two web applications named *backend* and *frontend*. Those two applications communicate
by exchanging JSON data over the HTTP(S) protocol. The backend application is supposed to handle computationally
intensive requests, e.g. calculating a frequency distribution, whereas the fronted application is meant
to render the UI. The reason for such a split was driven by common pricing models offered by cloud/hosting providers
which are proportional to the CPU/memory consumption of a given application.

## Screenshots
The screenshot below shows the main view of the frontend application. It's a workspace that allows a user
to create/edit/delete facilities and distribution channels. A distribution network is presented as a tree
as well as rendered on the map.

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/risk-analyzer.png)

The screenshot below shows the dialog window for creating a new facility. Note that in case of any validation
errors a corresponding form field will be marked with a red border.
  
![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/node-dialog.png)

The screenshot below shows the dialog window for creating a new distribution channel.

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/edge-dialog.png)

The screenshot below shows a frequency distribution chart for a sample network. The data displayed in the chart
are calculated by the backend application. As mentioned before, the frontend just renders the UI.

![Risk Analyzer Screenshot](https://github.com/danielpacak/risk-analyzer/raw/master/README/simulation.png)

## Building
The project is built using [Apache Maven](http://maven.apache.org). To build frontend and backend web
applications run the following command from the project root directory

`$ mvn clean install`

If everything went well, you'll find the frontend WAR file in the *frontend/deployment/deployment.dev/target* directory.
Similarly, the backend WAR file can be found in the *backend/deployment/deployment.dev/target* directory.

## Deploying
Both frontend and backend applications are divided into a set of functional modules. These module can be deployed in
various configurations. For instance, the frontend application can be run locally with the [relational database (RDBMS)]
(http://en.wikipedia.org/wiki/Relational_database) as data storage, or on Google App Engine with its schemaless
[High Replication Datastore (HRD)](https://developers.google.com/appengine/docs/java/datastore/overview).

The most common deployment configurations are described in the following sections.

### Deploying locally

**This has changed recently. Needs to be updated**

1. Create a working directory, for example C:\RiskAnalyzer, hereafter referred to as RISK_ANALYZER_HOME.
2. Create a sub-directory for the Backend Web application in RISK_ANALYZER_HOME\backed.
3. Create a sub-directory for the Frontend Web application in RISK_ANALYZER_HOME\frontend.
4. Downlaod the [Backend Web](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/lastStableBuild/com.scirisk$risk-analyzer-backend-web/artifact/com.scirisk/risk-analyzer-backend-web/0.0.1-SNAPSHOT/risk-analyzer-backend-web-0.0.1-SNAPSHOT-exec-war.jar) application to RISK_ANALYZER_HOME\backend.
5. Download the [Frontend Web](https://buildhive.cloudbees.com/job/danielpacak/job/risk-analyzer/lastStableBuild/com.scirisk$risk-analyzer-web/artifact/com.scirisk/risk-analyzer-web/0.0.1-SNAPSHOT/risk-analyzer-web-0.0.1-SNAPSHOT-exec-war.jar) application to RISK_ANALYZER_HOME\frontend.
6. Launch the Backend Web application by executing java -jar risk-analyzer-backend-web-0.0.1-SNAPSHOT-exec-war.jar -httpPort 9090
7. Open this URL [http://localhost:9090/risk-analyzer-backend-web/soap/risk-analyzer.wsdl](http://localhost:9090/risk-analyzer-backend-web/soap/risk-analyzer.wsdl) in your favorite Web browser to make sure that the backend application is up and running.
8. Launch the Frontend Web application by executing java -jar risk-analyzer-web-0.0.1-SNAPSHOT-exec-war.jar
9. Open this URL [http://localhost:8080/risk-analyzer-web](http://localhost:8080/risk-analyzer-web) in your favorite Web browser to make sure that the frontend application is up and running.

### Deploying on Google App Engine
For demonstration purpose Risk Analyzer is already deployed on Google App Engine. It's configured
as a [one-click deployment](https://pacak-daniel.ci.cloudbees.com/job/risk-analyzer-deployment-google)
with [Jenkins](http://jenkins-ci.org/) provided by CloudBees.

The frontend is accessible at [http://risk-analyzer-frontend.appspot.com](http://risk-analyzer-frontend.appspot.com).

The backend is accessible at [http://risk-analyzer-backend.appspot.com](http://risk-analyzer-backend.appspot.com/soap/risk-analyzer.wsdl).

### Deploying on CloudBees
Risk Analyzer is also deployed on the CloudBees platform. Similarly, it's configured as a
[one-click deployment](https://pacak-daniel.ci.cloudbees.com/job/risk-analyzer-deployment-cloudbees).

The frontend is accessible at [http://risk-analyzer-frontend-web.pacak-daniel.cloudbees.net](http://risk-analyzer-frontend-web.pacak-daniel.cloudbees.net).

The backend is accessible at [http://risk-analyzer-backend-web.pacak-daniel.cloudbees.net](http://risk-analyzer-backend-web.pacak-daniel.cloudbees.net/soap/risk-analyzer.wsdl).

## Backend API
This section describes the resources that make up the Backend API. The API is accessible over HTTP(S). The data is sent and received
as JSON.

## Frequency Distribution API
This API allows you to perform the frequency distribution simulation on a given supply chain.

`POST /frequency-distribution`

### Input
```javascript
{
  "network": {
    facilities: [],
    channels: []
  },
  "inputParams": []
}
```javascript

### Response
Status: 201 Accepted

```javascript
{
  data: [{x:1,y:2}]
}
```javascript