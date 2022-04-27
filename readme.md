# Short Documentation

## Environment
Developed with Visual Studio Code on Windows so following Plugins are installed:

* Spring Boot Extension Pack
* Extension Pack for Java
* Lombok Annotations Support for VS Code

Frontend is included in /frontend Folder

Please check pom.xml for Spring Boot Dependencies and package.json for react Dependendies


## Parameters
application.properties holding the following params to change the API fetch for randomUser:
* `randomuser.url=https://randomuser.me/api/?inc=gender,name,location,email&noinfo`
* `randomuser.userSize=5`
* `randomuser.period=120`

__Changing the url will have extremly effecting the programm cause of db-models.__

## Start
Clone or Download the project. Make sure you have Java and Node installed.

* Start Spring Boot Backend via `mvnw spring-boot:run`
* Start React via `frontend/npm start`

Frontend could be reached over http://localhost:3000
Backend could be reached over http://localhost:8080

