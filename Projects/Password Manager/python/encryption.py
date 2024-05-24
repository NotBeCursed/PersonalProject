from cryptography.fernet import Fernet
import hashlib
import uuid
import os
import os.path
from getpass import getpass


def FirstLaunch() -> None:
    
    attempts = 0
    while attempts <= 2:
        os.system('cls' if os.name == 'nt' else 'clear')
        MasterPasswd = getpass("Enter a master password : ")
        ConfirmMasterPassword = getpass("Confirm your master password : ")
        attempts += 1

        if MasterPasswd == ConfirmMasterPassword:
            break
        elif MasterPasswd != ConfirmMasterPassword and attempts == 3:
            print("Error : Not same password !")
            quit()
    
    salt = uuid.uuid4().hex
    HashMasterPasswd = hashlib.sha256(salt.encode()+MasterPasswd.encode()).hexdigest()+":"+salt
    with open("key.key","w") as KeyFile:
        KeyFile.write(HashMasterPasswd)


def oauth(key:str) -> bool:
    
    MasterPasswd, Salt = key.split(":")
    attempts = 0
    while attempts <= 2:
        os.system('cls' if os.name == 'nt' else 'clear')
        EnterPasswd = getpass("Enter a master password : ")
        attempts += 1
        if MasterPasswd == hashlib.sha256(Salt.encode()+EnterPasswd.encode()).hexdigest():
            return True
        elif MasterPasswd != hashlib.sha256(Salt.encode()+EnterPasswd.encode()).hexdigest() and attempts == 3:
            return False




if __name__ == "__main__":

    
    if not os.path.exists("key.key"):
        FirstLaunch()
    elif os.path.exists("key.key"):
        with open("key.key","r") as KeyFile:
            key = KeyFile.read()
    print(key, len(key))
    if len(key) == 0:
        print("fgfdgf")
        FirstLaunch()

    if not oauth(key):
        print("Access Denied !")
        quit()
        
    print("Welcome !")
    