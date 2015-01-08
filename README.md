InstaGit
========

InstaGit is an instant Git server based on [JGit](http://eclipse.org/jgit/) and [Jetty](http://eclipse.org/jetty/).

It requires Java 6 or later.


## How to Use

Download the release and run.

```sh
curl -LO https://github.com/int128/instagit/releases/download/v1.0/instagit-1.0-all.jar
java -jar instagit-1.0-all.jar
```

The server provides HTTP access to repositories in the current directory.
For example, if the current directory has `repo` directory, invoke following to clone it.

```sh
git clone http://localhost:8080/repo
```

Also open `http://localhost:8080` on your browser to see list of repositories.

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

The server provides Git HTTP access without any authentication.

## Contribution

This is an open source software licensed under the Apache License Version 2.0. Any issues or pull requests are welcome.
