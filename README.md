<div align="center">
  <h1 align="center">Sparesti</h1>
  <h3 align="center">idatt2106_2024_3_backend </h3>
  <p align="center">
  This is the backend repository made by Magnus Rindal, Håkon Fredrik Fjellanger, Isak Kallestad Mandal, Sverre Frogner Haugen, Tini Tran, Torbjørn Antonsen, Vilde Min Vikan and Jens Martin Jahle, for the subject System Development with agile methods (IDATT2106) for the spring semester 2024.
  </p>
</div>

## Description

Sparesti is a web application that helps users save money by providing a platform for users to
create and manage savings goals, perform challenges, and track their progress.
The application is designed to be user-friendly and intuitive, making it easy for users to save
money and reach their financial goals.

This repository contains the backend code for the Sparesti application. The backend is built using
Java and Spring Boot, and it provides the API endpoints for the frontend to interact with.
The backend is responsible for handling user authentication, managing savings goals, challenges, and
transactions, and providing data to the frontend.
The backend also interacts with a MySQL database to store and retrieve data.

## Badges

<a href="https://gitlab.stud.idi.ntnu.no/scrum_team_3/idatt2106_2024_03_backend/-/commits/main"><img alt="pipeline status" src="https://gitlab.stud.idi.ntnu.no/scrum_team_3/idatt2106_2024_03_backend/badges/main/pipeline.svg" /></a>

<a href="https://gitlab.stud.idi.ntnu.no/scrum_team_3/idatt2106_2024_03_backend/-/commits/main"><img alt="coverage report" src="https://gitlab.stud.idi.ntnu.no/scrum_team_3/idatt2106_2024_03_backend/badges/main/coverage.svg" /></a>

## Visuals

Depending on what you are making, it can be a good idea to include screenshots or even a video (
you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out
Asciinema for a more sophisticated method.

## Installation and execution

> **Note:** The database and openAI API key will be deleted after June 2024, so the project will not
> work as intended after this.

### Prerequisites

To run this program you need:

- Docker

### Installation

1. Clone the repository using Git:
   ```sh
   git clone https://gitlab.stud.idi.ntnu.no/scrum_team_3/idatt2106_2024_03_backend
    ```

2. Alternatively, you can download the ZIP file and extract it.
   https://gitlab.stud.idi.ntnu.no/scrum_team_3/idatt2106_2024_03_backend/-/archive/main/idatt2106_2024_03_backend-main.zip

### Running the repository

> Note that docker must be running in the background
>

1. Navigate to the root folder of the project

2. Run the following command to build the Docker image:
   ```sh
   docker build -t sparesti-backend .
   ```
3. Run the following command to start the Docker container:
   ```sh
    docker run -p 8080:8080 sparesti-backend
    ```
4. The backend should now be running on http://localhost:8080

> Note that this needs to be done within the network of NTNU, because the backend accesses the
> database which is hosted within a virtual machine.
> If you are not within the network of NTNU, you can run the backend locally by changing the
> application.properties file in the resources folder to use a local database.

### Database connection

The default connection to the database is set up to use our database hosted within a virtual
machine.
This is the recommended way to run the project, as it is the most up-to-date version of the
database.
If you want to run the database locally, you can change the database connection to use a local
database by following these steps:

1. Navigate to root folder of the project
2. Make sure you have docker installed and running
3. Navigate to the scripts folder:
   ```sh
    cd scripts
   ```
4. Run the following command to start the database:
   ```sh
    cmd.exe /c run_database.cmd
   ```
5. The database should now be running on localhost:3306
6. Change the database connection in the application.properties file in the resources folder to use
   the local database by commenting out the current connection and uncommenting the LOCALHOST
   connection.
7. (OPTIONAL) Test data is located in the scripts folder and can be added to the database through
   sql:

## Usage

The backend provides several API endpoints that the frontend can use to interact with the
application. The API endpoints are documented using Swagger, and you can access the Swagger
documentation by navigating to http://localhost:8080/swagger-ui/index.html in your web browser.

## Authors and acknowledgment

This project was made by the following students at NTNU:

Magnus Rindal,
Håkon Fredrik Fjellanger,
Isak Kallestad Mandal,
Sverre Frogner Haugen,
Tini Tran,
Torbjørn Antonsen,
Vilde Min Vikan and
Jens Martin Jahle

## Project status

This project is currently finished and will not be maintained.
There are no plans to continue development on this project in the future.

The openAI API key will be invalidated after June 2024, so the project will not work as intended
after this.
Feel free to change the API key located in the application.properties file to continue using the
project.

The database will be deleted after June 2024, so the project will not work as intended after this.
Feel free to change the database connection located in the application.properties file to continue
using the project.

### Running swagger endpoint documentation:

Paste http://localhost:8080/swagger-ui/index.html into web browser while the project runs, (8080 is
the default port in
which the backend runs)

### Display test coverage:

1. Navigate to the root folder of the project
2. Navigate to the jacoco folder

```sh
 cd target/site/jacoco
```

3. Open the index.html file in a web browser

```sh
  start index.html
  ```

4. The test coverage report will be displayed in the web browser

### Additional information

In TransactionService the attribute "testInterval" is used to determine the time limit for the
retrieval of transactions
from the bank API. This attribute is set high to always retrieve the transactions from the bank API.
This is done to
ensure that the test transactions are always retrieved. In a real-world scenario, this attribute
should not be present and removed from the codebase.
The attribute is only used for testing purposes.