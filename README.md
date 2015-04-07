Garrit Hub
==========

Provides pipe-like Garrit negotiation capabilities.

Installation
------------

After checking out the repository, it can be built with
[Maven](http://maven.apache.org/):

```
mvn package
```

This will generate an executable JAR, `./target/hub-1.0.0-SNAPSHOT.jar`.

Usage
-----

Make a copy of
[`config-example.yml`](https://github.com/Garrit/hub/blob/master/config-example.yml):

```
cp config-example.yml config.yml
```

and customize it as necessary:

```
editor config.yml
```

At minimum, you'll need to change the `executor`, `judge`, and `reporter`
properties to indicate the appropriate endpoints within your environment.

Then, to launch the negotiator:

```
java -jar /path/to/hub-1.0.0-SNAPSHOT.jar server /path/to/config.yml
```