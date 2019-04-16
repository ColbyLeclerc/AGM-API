# AGM-API
API to perform CRUD operations through RESTful principles. This project began as a side project, evolved into my senior capstone project, and (possibly) will evolve into a SaaS side business. 

## Why am I releasing the source code?
Most of my software engineering work is found in proprietary code bases at my current job, thus before this project I didn't have much to show in the form of source code I've developed. My previous projects on my repo were either school projects, or side projects from when I was in high school (or earlier)


## Entity Relationship Diagram
![ER Diagram](https://github.com/ColbyLeclerc/AGM-API/blob/master/AGM_ERD.png)

## Deployment
First, ensure you have Java 1.8 and Maven installed

Next, run ``mvn package``, which will download the dependencies from the pom.xml file,
and package the application to a .jar file.

You can run the .jar file created using `java -jar <jar_file_name>`, or if you
want to have the `application.properties` file external to the compiled
jar file (recommended), then run `java -Dspring.config.location=<location_to_application.properties> -jar <jar_file_name>`

If you want to run the application as a service, here's an example of what I've done to run the application server using 
systemd:

1. Create a .service file in the systemd directory (typically `/etc/systemd/system`) for our example, we'll
name the service `agm-api.service`

2. Create a file similar to the one below (change properties where appropriate)
```
[Unit]
Description=AGM API Server (RESTful Spring Application)
After=syslog.target

[Service]
User=<your_application_server_user>
ExecStart=/usr/bin/java -Dspring.config.location=/home/<your_application_server_user>/agm-api/config/application.properties -jar /home/<your_application_server_user>/agm-api/agm-api.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target

```
Note: `<your_application_server_user>` is expected to have the minimum permissions required to run the server. If you
want to run the server off of port 80 or 443 (which would require root privileges), use either Nginx or Apache to create
a reverse proxy by having the application server listen to a local port (such as 8080), and have the web server
tunnel requests to the desired elevated port. This way, the application server sits behind a web server, and the user
running the application server does not have elevated privileges.

Obviously the settings will vary depending on the requirements of your deployment. This will at least get the server
running for testing. 
