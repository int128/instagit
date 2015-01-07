InstaGit: instant Git server
============================

InstaGit is an instant Git server based on [JGit](http://eclipse.org/jgit/) and [Jetty](http://eclipse.org/jetty/).

It requires Java 6 or later and provides Git access via HTTP.


## How to Use

Download the release and run on JVM.

```bash
curl -LO https://github.com/int128/instagit/releases/download/v1.0/instagit-1.0-all.jar
java -jar instagit-1.0-all.jar
```

The server will provide HTTP access to repositories in the current directory.
For example, if the current directory has `repo` directory, invoke following to clone it.

```bash
git clone http://localhost:8080/repo
```

Also open `http://localhost:8080` on your browser to see list of repositories.

### Options

It accepts following options:

* `-r` - base path of repositories; default is `.` (current directory)
* `-b` - listening host; default is `localhost`
* `-p` - listening port; default is `8080`

Example:

```bash
java -jar instagit-1.0-all.jar -r ..
```

## Caveat

This server provides Git access without any authentication.

## Contribution

This is an open source software licensed under the Apache License Version 2.0. Any issues or pull requests are welcome.
