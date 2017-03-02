# Task 3

In this task, we use Apache Spark for created an Inverted Index and reading data in a stream fashion.

## Execution
In order to execute a particular Junit, use the following command
```
./gradlew task3:test
```

## Streaming
We have a streaming example. This example listen to localhost:9999 for text data and count the words in intervales of 10 seconds. 

```
./gradlew task3:stream
```


It requires an additional process that connects to our socket and produces text data. You can do this with this command (Unix Only)

Unix
```
nc -lk 9999
```

Windows. You need to download [netcat](https://joncraton.org/files/nc111nt.zip). Password: "nc"
```
nc -l -p 9999
```
