# Task 3

In this task, we use Apache Spark for created an Inverted Index and reading data in a stream fashion.

## Execution
In order to execute a particular Junit, use the following command
```
gradle test --tests tutorial.mr.task3.Example1
gradle test --tests tutorial.mr.task3.Example2
```

Example2 requires an additional process that connects to our socket. You can do this with this command (Unix Only)
```
nc -lk 9999
```

