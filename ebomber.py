import smtplib
from time import sleep
from os import name, system
from getpass import getpass
import sys

def clear():
    if name == 'nt':
        system('cls')
    else:
        system('clear')

clear()

print("""

░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░

    ███████              ████                     ███             
  ███░░░░░███           ░░███                    ░░░              
 ███     ░░███ ████████  ░███   ██████   ███████ ████  █████ █████
░███      ░███░░███░░███ ░███  ███░░███ ███░░███░░███ ░░███ ░░███ 
░███      ░███ ░███ ░███ ░███ ░███ ░███░███ ░███ ░███  ░░░█████░  
░░███     ███  ░███ ░███ ░███ ░███ ░███░███ ░███ ░███   ███░░░███ 
 ░░░███████░   ░███████  █████░░██████ ░░███████ █████ █████ █████ 𝟚𝟘𝟚𝟚
   ░░░░░░░     ░███░░░  ░░░░░  ░░░░░░   ░░░░░███░░░░░ ░░░░░ ░░░░░ 
               ░███                     ███ ░███                  
               █████                   ░░██████                   
              ░░░░░                     ░░░░░░                    

░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄▄░▄▄
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░


  ___ 
 |\__\   - 𝔼𝕞𝕒𝕚𝕝 𝕓𝕠𝕞𝕓𝕖𝕣 
 \|__|   - 𝕄𝕒𝕜𝕖 𝕤𝕦𝕣𝕖 𝕥𝕠 𝕋𝕦𝕣𝕟 𝕠𝕟 𝕃𝕖𝕤𝕤 𝕊𝕖𝕔𝕦𝕣𝕖 𝔸𝕔𝕔𝕖𝕤𝕤 𝕚𝕟 𝕪𝕠𝕦𝕣 𝕖𝕞𝕒𝕚𝕝 𝕤𝕖𝕥𝕥𝕚𝕟𝕘𝕤 
           𝕓𝕖𝕗𝕠𝕣𝕖 𝕪𝕠𝕦 𝕥𝕖𝕝𝕝 𝕞𝕖 𝕚𝕥 𝕕𝕠𝕖𝕤𝕟'𝕥 𝕨𝕠𝕣𝕜. 
         - 𝕋𝕙𝕚𝕤 𝕚𝕤 𝕔𝕣𝕖𝕒𝕥𝕖𝕕 𝕗𝕠𝕣 𝕖𝕕𝕦𝕔𝕒𝕥𝕚𝕠𝕟 𝕡𝕦𝕣𝕡𝕠𝕤𝕖𝕤.

""")

print("Choose your email provider: ")
print("(1) Gmail")
print("(2) Outlook/Hotmail")
print('(3) Yahoo')
print('(4) Other')
provider = int(input("\n>> "))

if provider == 1:
    smtp_server = "smtp.gmail.com"
elif provider == 2:
    smtp_server = "smtp.office365.com"
elif provider == 3:
    smtp_server = "smtp.mail.yahoo.com: "
elif provider == 4:
    smtp_server = input("Enter custom STMP server: ")
else:
    print("Invalid choice!")
    sys.exit()

email_id = input("Enter sender's email address: ")
password = getpass("Enter the password: ")

server = smtplib.SMTP(smtp_server, 587)
server.ehlo()
server.starttls()

try:
    server.login(email_id, password)
    print("Login successful!")
except smtplib.SMTPAuthenticationError:
    print("Login failed!\nInvalid username or password.")
    sys.exit()

rec = input("Enter receiver's email address: ")
name = input("Enter a name: ")
subject = input("Enter the subject: ")
body = input("Enter the message: ")
msg = f"From: {name}\nTo: {rec}\nSubject: {subject}\n\n{body}"

n = int(input("Enter the number of emails to send: "))

for i in range(n):
    server.sendmail(email_id, rec, msg)
    print("Emails sent: " + str(i+1))
print(f"{n} emails have been sent from {email_id} to {rec}")

input("Press enter to exit...")

