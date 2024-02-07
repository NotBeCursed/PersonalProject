#!/usr/bin/python3

import os
import docker
import string
import random
import sys
import mariadb
from getpass import getpass

def install_config():

    image = "mariadb:10.5.23"
    caracters = string.ascii_letters + string.digits + string.punctuation
    root_password = ""

    # Generate Random Root Password
    
    for i in range (31):
        root_password += caracters[random.randint(0, len(caracters)-1)] 

    # Get Credentials 
    if len(sys.argv) != 2:
        user = input("Username : ")
    else :
        user = sys.argv[1]

    if len(sys.argv) == 3:
        user = sys.argv[1]
        password = sys.argv[2]
    else :
        user = input("Username : ")
        password = confirm_password(get_password())

    clear()
    print("==="*15)
    print(" "*17,"INSTALATION"," "*17)
    print("==="*15)
    print("")
    print("\t","> DATABASE CREATION")

    # Database Creation
    client = docker.from_env()
    client.images.pull(image)
    print("\t   ","> DOWNLOAD CONTAINERS IMAGE")
    env = [f"MARIADB_PASSWORD={password}",f"MARIADB_DATABASE={user}",f"MARIADB_USER={user}", f"MARIADB_ROOT_PASSWORD={root_password}"]
    client.containers.run(image, detach=True, hostname="database", name="password_manager", environment=env, auto_remove=True, ports={"3306/tcp":3306})
    database = client.containers.get("password_manager")
    ipaddress = database.attrs["NetworkSettings"]["IPAddress"]

    print("\t","> DATABASE CONFIGURATION")
    print("\t   ","> PREFERENCES TABLE CREATION")
    try:
        conn = mariadb.connect(
            user = user,
            password = password,
            host = "localhost",
            port = 3306,
            database = user)
    
    except mariadb.Error as e:
        print(f"Error connecting to MariaDB Platform: {e}")
        sys.exit(1)
    
    cursor = conn.cursor()
    cursor.execute("CREATE TABLE preferences (password_length INT, uppercases BOOLEEN, lowercases BOOLEEN, digits BOOLEEN, punctuations BOOLEEN);")
    print("\t   ","> MAIN TABLE CREATION")
    cursor.execute("CREATE TABLE passwords (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255),login VARCHAR(255),password VARCHAR(255));")

    print("==="*15)
    print(" "*17,"SUCCESSFULL"," "*17)
    print("==="*15)
    #database.stop()

def clear():
    os.system("clear")

def confirm_password(password):

    # Confirm Master Password
    while True:
        clear()
        confirm_password = getpass("Confirm Master Password : ")
        if confirm_password == password:
            break
        else:
            print("The passwords are different.")
            password = get_password()
    return password

def get_password():


    # Check Password Strong
    while True:
        password = getpass("Master Password : ")
        if (len(password)>=12):
            lower, upper, digit, special = 0, 0, 0, 0

            for i in password:
                if (i in string.ascii_lowercase):
                    lower += 1
                if (i in string.ascii_uppercase):
                    upper += 1
                if (i in string.digits):
                    digit += 1
                if (i in string.punctuation):
                    special += 1

            if lower<1 or upper<1 or digit<1 or special<1 :
                clear()
                print("Your master password must be at least 12 characters long and contain 1 lowercase, 1 uppercase, 1 digit and 1 special character.")
            else :
                break
        else :
            clear()
            print("Your master password must be at least 12 characters long and contain 1 lowercase, 1 uppercase, 1 digit and 1 special character.")
        
    return password

if __name__ == "__main__":
    install_config()