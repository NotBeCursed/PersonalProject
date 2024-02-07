# User Authentification Portal

---

**How it is work ?**
Most applications and websites require users to identify themselves. Authentication pages are now commonplace, and need to be secure. To achieve this, user passwords are not stored directly in a database. The user's password first passes through a [[Projects/Hash Function/README|hash function]], and only the hash of the user's password is stored in the database. To validate authentication, all that's needed is to compare the hash of the password entered with that of the registered password.

**Development**
For this project, we're going to deploy a small **web application**, allowing a user to register and connect to an authentication page. For this web application, we'll deploy an **Nginx** web server and a **MariaDB** database on **Docker** containers. 
This project will use several programming languages to create a web page that communicates with a database (**HTML**, **CSS**, **PHP** and **SQL**).

First, we'll deploy our web server and database. To do this, we'll write a **docker-compose** file.

```yaml
version: "3.5"

services:
 db:
  container_name: auth_db
  hostname: auth_database
  restart: unless-stopped
  image: docker.io/mariadb:11.2.2
  environment:
   MARIADB_ROOT_PASSWORD: ${MARIADB_ROOT_PASSWORD}
   MARIADB_DATABASE: ${MARIADB_DATABASE}
   MARIADB_USER: ${MARIADB_USER}
   MARIADB_PASSWORD: ${MARIADB_PASSWORD} 
  volumes:
   - volume_auth_db:/var/lib/mysql

 app:
  container_name: auth_app
  hostname: auth_app
  image: nginx:alpine
  restart: unless-stopped
  depends_on: 
   - db
  volumes :
   - volume_auth_app:/usr/share/nginx/html
  ports:
   - 8080:80

volumes:
 volume_auth_db:
  name: volume_auth_db
 volume_auth_app:
  name: volume_auth_app
```

Now that our web server and database have been deployed, we can start building our authentication portal. 
Let's start by creating a web page with a login form and a registration form. To do this, we can use **HTML** and **CSS**.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="login-container">
    <h2>Connexion</h2>
    <form class="login-form" action="#" method="post">
        <div class="form-group">
            <label for="username">Username :</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password :</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <button type="submit">Log in</button>
        </div>
        <div class="form-group">
            <a href="register.html">Sign in</a>
        </div>
    </form>
</div>
</body>
</html>
```

```css
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.login-container {
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    width: 300px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}

.login-container h2 {
    text-align: center;
    color: #333;
}

.login-form {
    display: flex;
    flex-direction: column;
    text-align: center;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    font-weight: bold;
    margin-bottom: 5px;
    display: block;
}

.form-group input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.form-group button {
    background-color: #007bff;
    color: #fff;
    padding: 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.form-group button:hover {
    background-color: #0056b3;
}

.form-group a {
    color: #333;
    text-decoration: none;
}
```