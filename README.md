# Bookstore API

A Spring Boot application providing a RESTful API for managing a library system. This API supports book management, user accounts and user authentication.

---

## Features

### Login

### Register new user

### Admin
 
- Can add books
- Can view books
- Can delete books
- Can add users
- Can delete users

### User
- Can add books
- Can view books
---
## Client application

### Login
![](/images/1.png)

### Register
![](/images/2.png)

### Admin menu 
![](/images/3.png)
#### Add book
![](/images/4.png)
#### View books
![](/images/5.png)
#### Delete book
![](/images/6.png)
#### Add user
![](/images/7.png)
#### Delete user
![](/images/8.png)

### User menu
![](/images/9.png)
#### Add book
![](/images/10.png)
#### View books
![](/images/11.png)
---
## Database

The application uses H2 as the database.
### Schema
The database schema includes tables for:
- Users
- Books


---

## Default Admin User
An admin user is initialized during startup:

```properties
username: admin
password: admin
```