# Superhero Sightings Web App

Spring Boot, Spring JDBC for MySQL connection, Spring validation, Thymeleaf for templating, Unit testing.
Built using Maven and Java 11.

### Overview
MVC application that keeps track of Superhero sightings, including the date and location.
Superheroes can belong to different organisations.
Sightings happen at Locations that are also recorded in the system.
Main page displays news feed of the last 10 sightings and navigation to screens for All Sightings, Supeheroes, Locations and Organisations.
The app implements CRUD operations for Superhero, Location, Organisation and Sighting.


### How to run
* ERD of the superheroes database is included.
* Run the SQL script superheroes.sql and superheroesTest.sql to create required databases.
* Run the SQL script superheroesData.sql to populate the database with a few example values.
* Change the password in main/resources/application.properties and test/resources/application.properties to the password you set up for MySQL on your computer.
* Run SuperheroesApplication and got to localhost:8080

