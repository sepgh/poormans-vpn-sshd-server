# poormans-vpn-sshd-server
SSHD server for poormans' VPN

This project is written (in dirtiest way possible :smile:) to create a SSH server with no shell and only dynamic proxy support to run as substitude of default ssh server of your linux machine. However, I think this should work on Windows too (I say I think because I haven't tested it, but it's written in Java).

## How to setup

First make sure java runtime (11+) is installed on your machine.

1. Download the jar file from [releases](https://github.com/sepgh/poormans-vpn-sshd-server/releases).
2. Move jar file to a directory of your choice and run the command below (you need a service to keep this command running, or you can use `screen` session)

```shell
java -jar poorman-vpn-sshd-1.1.jar PORT PATH_TO_SSH_PRIVATE_KEY PATH_TO_USERS_FILE
```
 
 Example (opens port 1080):
 
```shell
java -jar poorman-vpn-sshd-1.1.jar 1080 ./ssh.pk ./users
```
 
 
To add a new user, edit `users` file and enter `username password` with a space in between to separate them in each line. Example:

```
peter 123456
john john-pass
```

