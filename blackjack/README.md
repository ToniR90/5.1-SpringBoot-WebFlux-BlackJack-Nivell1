# Blackjack API 🃘

This is a reactive Java API developed with **Spring Boot** and **WebFlux** to simulate a **Blackjack game**.  
The application is connected to two separate databases: **MongoDB** and **MySQL**, and includes full game logic, player management, and rule enforcement.

## Features

- Reactive API using **Spring WebFlux**
- **Global Exception Handling**
- Integration with both **MongoDB** (reactive) and **MySQL**
- Full **Blackjack game logic**
- **Unit tests** using **JUnit** and **Mockito**
- **Dockerized** deployment

---

## Level 1: Basic Implementation

### ✅ Reactive Architecture

- Built with **Spring WebFlux**
- Reactive MongoDB integration using `ReactiveMongoRepository`
- Controllers and Services follow reactive programming practices (`Mono`, `Flux`)

### ✅ Global Exception Handling

A `GlobalExceptionHandler` class handles all uncaught exceptions across the application and returns proper HTTP status codes.

### ✅ Database Configuration

- **MongoDB**: Stores game state, cards, hands, etc.
- **MySQL**: Stores user/player data, ranking, and historical results.

### ✅ Unit Testing

- Unit tests for at least one controller and one service using:
  - **JUnit 5**
  - **Mockito**

---

## API Endpoints

### 🎩 Create New Player

- **Method**: `POST`
- **Endpoint**: `/players`
- **Request Body**: `{ "playerName": "Toni" }`
- **Response**: `200 Created`


### 👤 Get Players by ID

- **Method**: `GET`
- **Endpoint**: `/players/{id}`
- **Response**: `200 Created`


### 🙋‍♂️ 🙋‍♀️

- **Method**: `GET`
- **Endpoint**: `/players`
- **Response**: `200 Created`


### 🎲 Create New Game

- **Method**: `POST`
- **Endpoint**: `/games`
- **Request Body**: `{ "playerId": 1 }`
- **Response**: `200 OK` 


### 🎮 Get Game Details

- **Method**: `GET`
- **Endpoint**: `/games/{id}`
- **Path Variable**: `id` (game identifier)
- **Response**: `200 OK` with full game details


### Get All Games

- **Method**: `GET`
- **Endpoint**: `/games`
- **Response**: `200 OK` with full game details


### 🃏 Play Turn - Hit//Stand

- **Method**: `PUT`
- **Endpoint**: `/game/{id}/hit//Stand`
- **Path Variable**: `id`
- **Response**: `200 OK` with play result

### ❌ Delete Game

- **Method**: `DELETE`
- **Endpoint**: `/game/{id}`
- **Path Variable**: `id`
- **Response**: `204 No Content`

### 🏆 Player Ranking

- **Method**: `GET`
- **Endpoint**: `/players/ranking`
- **Response**: `200 OK` with ordered player ranking

---


## Level 2: Dockerization

### 🐳 Docker Setup

Docker file: https://hub.docker.com/r/tonir90/blackjack-app

1. Install Docker and login.
2. Build Docker image:
   ```bash
   docker pull tonir90/blackjack-app:latest
   docker run -p 8080:8080 tonir90/blackjack-app:latest

---

## 🔧 Technologies 

- **Java 24**
- **Spring Boot**
- **Spring WebFlux**
- **Reactive MongoDB**
- **Spring Data JPA (MySQL)**
- **JUnit 5 + Mockito**
- **Docker**

---

## ❗⚠️ Before starting ⚠️❗

Set your MySql password on application-dev.yml 

---

## 🙋‍♂️ Author

**Toni Romero**

Project developed as part of a practical exercise for backend development with Java and Spring Boot.