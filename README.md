
Malformed Data Fixer-Upper
==========================

This example shows how ingest and manually correct data using Red Hat JBoss Fuse & Red Hat JBoss BPMS. 

It will do the following:

- Ingest a file
- Validate the contents
- If valid
 - Log a message
- If invalid
 - Log a message
 - Invoke a jBPM process with a Human Task node allowing you to fix the data and resubmit it

Requirements
------------

- [Apache Maven 3.x](http://maven.apache.org)
- [JBoss Fuse 6.2.x](http://www.jboss.org/products/fuse/overview/)
- [JBoss BPM Suite 6.1.x](http://www.jboss.org/products/bpmsuite/overview/)

Building Example
----------------

Run the Maven build

```
~$ cd $MDFU_PROJECT_ROOT
~$ mvn clean install
```

Running in JBoss Fuse
---------------------

Start the JBoss Fuse server

```
~$ $JBOSS_FUSE_HOME/bin/fuse
```

From the JBoss Fuse console, enter the following to install the example application

```
JBossFuse:karaf@root> osgi:install -s mvn:org.jboss.poc/mdfu-fusvc-api/1.0.0-SNAPSHOT
JBossFuse:karaf@root> osgi:install -s mvn:org.jboss.poc/mdfu-fusvc-cb-impl/1.0.0-SNAPSHOT
JBossFuse:karaf@root> osgi:install -s mvn:org.jboss.poc/mdfu-mp/1.0.0-SNAPSHOT
JBossFuse:karaf@root> osgi:install -s mvn:org.jboss.poc/mdfu-fp/1.0.0-SNAPSHOT
````

To see what is happening within the JBoss Fuse server, you can continuously view the
log (tail) with the following command

```
JBossFuse:karaf@root> log:tail
```

Setup JBoss BPM Suite
---------------------

Create an application user and give them an "analyst" role

```
~$ $JBOSS_BPMS_HOME/bin/add-user.sh -a -u 'bpmsAdmin' -p 'admin1!!' -g 'admin,analyst' -r 'ApplicationRealm'
```

Start the JBoss BPMS server

```
~$ $JBOSS_BPMS_HOME/bin/standalone.sh
```

Browse to the [JBoss BPMS console](http://localhost:8080/business-central)

Import the [Malformed Data Fixer-Upper BPMS Git Repository](https://github.com/joshdreagan/mdfu-bpms)

Build & Deploy the BPMS project from the 'Project Editor'

Deploy the JAX-WS service

```
~$ cd $MDFU_PROJECT_ROOT/mdfu-fusvc-impl
~$ mvn jboss-as:deploy
```

Testing
-------

Copy test data files to be ingested

```
~$ cd $MDFU_PROJECT_ROOT/mdfu-fp/src/test/data
```

To test a valid input file

```
~$ cp valid.txt /tmp/input/
```

You should see the text "Message was valid... :)" printed to the JBoss Fuse logs.

To test an invalid input file

```
~$ cp invalid.txt /tmp/input/
```

You should see the text "Message was invalid... :(" printed to the JBoss Fuse logs.

Browse to the [JBoss BPMS console](http://localhost:8080/business-central) and view the "Tasks" page. You should see a new task has been created. Claim the task and correct the data by changing the text to "valid message" (without quotes) and resubmitting. You should now see the text "Message was valid... :)" printed to the JBoss Fuse logs.
