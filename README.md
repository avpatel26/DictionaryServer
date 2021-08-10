# Multi-threaded Dictionary Server

This repository presents the interesting use of socket programming and threading through implementing the Multi-threaded dictionary server. Utilizing the client-server architecture, this server allows concurrent users to search the meaning(s) of a word, add a new word, and remove an existing word.

## Functionalities

* Query the meaning(s) of a given word
* Add a new word
* Remove an existing word 
* Update meaning of an existing word

## Setup of server

### 1st step: clone the project

```
git clone https://github.com/avpatel26/DictionaryServer.git
cd DictionaryServer/
```

### 2nd step: Start a Dictionary Server

When the server is launched, it loads the dictionary data from a file containing the initial list of words and their meanings. These data is maintained in memory in a structure that enables an efficient word search. When words are added or removed, the data structure is updated to reflect the changes.

```
java –jar DictionaryServer.jar <port> <dictionary-file>
```
port : where server listens for incoming connections\
dictionary-file : path to the initial dictionary file
  
### 3rd step: Start a Client
  
When the client is launched, it creates a TCP socket bound to the server address and port number. This socket remains open for the duration of the client-server interaction. All messages are sent/received through this socket.

```
java –jar DictionaryClient.jar <server-address> <server-port>
```

server-address : adreess of server (e.g. localhost) \
server-port : port number on that server is running 
