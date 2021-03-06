InstaGit [![Build Status](https://travis-ci.org/int128/instagit.svg?branch=master)](https://travis-ci.org/int128/instagit)
========

InstaGit is an instant Git server based on [JGit](http://eclipse.org/jgit/) and [Jetty](http://eclipse.org/jetty/).

It provides HTTP access to existing Git repositories in the local filesystem.

You can publish your repositories and fetch them over the network.


## How to Use

### Run the server

Download the release and run. It requires Java 6 or later.

```sh
curl -LO https://github.com/int128/instagit/releases/download/latest/instagit.jar
java -jar instagit.jar
```

InstaGit is available on Docker Hub. Run a container as follows.

```sh
docker run --rm -p 8080:8080 -v /repos:/repos int128/instagit
```

### Try Git access

The server provides access to repositories in the current directory at default.

For example, if the current directory has children as follows,

* `.` (currrent directory)
  * `./repo1` (directory of Git repository)
  * `./repo2` (directory of Git repository)

run following commands to clone them:

```sh
git clone http://localhost:8080/repo1
git clone http://localhost:8080/repo2
```

Following URLs will serve the list of repositories.

* Open `http://localhost:8080/` to see a Web page.
* Open `http://localhost:8080/?json` to get a JSON.

### Options

It accepts following options:

* `-b host` - listening host; default is `-b 0.0.0.0` (all interfaces)
* `-p port` - listening port; default is `-p 8080`
* `-l` - listening on localhost
* Extra argument - a base path of repositories; default is `.` (current directory)

Example:

```sh
java -jar instagit.jar -l ..
```

### Caveat

The server provides read-only access without any authentication.
It does not provide write access currently.


## How to Use in your App

InstaGit is available on [Maven Central](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.hidetake%22%20AND%20a%3A%22instagit%22) and [Bintray](https://bintray.com/int128/maven/instagit).

```groovy
// Gradle
compile 'org.hidetake:instagit:x.y'
```

Create and start a server by calling [`GitServer.create()`](src/main/java/org/hidetake/gitserver/GitServer.java) method.

```java
import org.eclipse.jetty.server.Server;
import org.hidetake.gitserver.GitServer;
import java.net.InetSocketAddress;

Server server = GitServer.create(new InetSocketAddress(8080), ".");
server.start();

server.stop();
```

See [`Main`](src/main/java/org/hidetake/gitserver/Main.java) class for example.


## Architecture

Product code is written in Java 6.
It uses [Jetty](http://eclipse.org/jetty/) server and [JGit](http://eclipse.org/jgit/) servlet.

Test code is written in Groovy and Spock.


## Contribution

This is an open source software licensed under the Apache License Version 2.0. Send me your issue or pull request.
