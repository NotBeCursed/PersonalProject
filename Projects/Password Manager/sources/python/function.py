import os
import sys
from getpass import getpass
import string

def help():
    print("HELP")

def install():

    print("==="*15)
    print(" "*17,"INSTALATION"," "*17)
    print("==="*15)
    print(" ")

    

    pwd = os.system("pwd")
    print(pwd)

if __name__ == "__main__":
    install()