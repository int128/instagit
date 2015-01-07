Minimum Git Server
==================

This is a minimum Git server based on [JGit](https://eclipse.org/jgit/) and [Jetty](http://eclipse.org/jetty/).


## How to run

```bash
java -jar gitserver-x.y.z-all.jar
```


### Options

It accepts following options:

* `-r` - base path of repositories; default is `.` (current directory)
* `-b` - listening host; default is `localhost`
* `-p` - listening port; default is `8080`

Example:

```bash
java -jar gitserver-x.y.z-all.jar -r ..
```
