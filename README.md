InstaGit
========

InstaGit is an instant Git server based on [JGit](http://eclipse.org/jgit/) and [Jetty](http://eclipse.org/jetty/).

It provides HTTP access to existing Git repositories in the local filesystem.

You can publish your repositories and fetch them over the network.


## How to Use

Download the release and run. It requires Java 6 or later.

```sh
curl -LO https://github.com/int128/instagit/releases/download/v1.0/instagit-1.0-all.jar
java -jar instagit-1.0-all.jar
```

It provides access to repositories in the current directory at default.

For example, if the current directory has children as follows,

* `.` (currrent directory)
  * `./repo1` (directory of Git repository)
  * `./repo2` (directory of Git repository)

run following commands to clone them:

```sh
git clone http://localhost:8080/repo1
git clone http://localhost:8080/repo2
```

Open `http://localhost:8080` in your browser to see list of repositories.

### Options

It accepts following options:

* `-a` - listening on all interfaces
* `-b host` - listening host; default is `-b localhost` (allows access from localhost)
* `-p port` - listening port; default is `-p 8080`
* Extra argument - a base path of repositories; default is `.` (current directory)

Example:

```sh
java -jar instagit-1.0-all.jar -a ..
```

### Caveat

The server provides read-only access without any authentication.
It does not provide write access currently.

## To Do

* [ ] Provides write access with an authentication
* [ ] Friendly Web interface

## Contribution

This is an open source software licensed under the Apache License Version 2.0. Any issues or pull requests are welcome.
