# templateSpringProject

## About this Project
This is template for Spring Boot

## Precondition
The following software are installed
- Java 1.8
- Vitrual Box
- Vagrant
- Gradle

## Dependencies
Dependencies are already listed at build.gradle.
- Spring Boot 1.5.2
- JPA
- PostgreSQL
- Thymeleaf
- Web
- Flyway

## Database
PostgreSQL runs on Virtual Box.

DB name is demodb
1. $ vagrant up
2. $ vagrant ssh
3. $ psql demodb

If you'd like reinstall PostgreSQL, Please execute command below.

$ vagrant provision

## How to start application
Please execute the following command at the terminal.

1. $vagrant up (The first time it takes time to download BOX (ubuntu / trusty64) and install PostgreSQL)
2. $gradle flywayMigrate
3. $gradle bootRun

Access localhost:8080 using the browser.

It is displayed Hello World

