# Eaton Example
## Description
This example let you to connect device and observe messages which sent by device.
#### First you should add create device by using endpoint after you should login device to server to send messages then
#### you can send messages and see the count of messages  by using rest endpoint.
There is a `Dockerfile` in file directory you can run it on docker or you kubernetes environment.
just you will need to a thing which is port-forwarding belong to pod to reach from outside
you can call all endpoints by using  `Eaton.postman_collection.json` file  on Postman.
you can run code using `example-0.0.1-SNAPSHOT.jar` file you should run command
`java -jar example-0.0.1-SNAPSHOT.jar`
Of course you can run project on intellJ and Eclipse.

For Run Test `mvn test`

For Reach h2 Db For web-ui `http://localhost:9091/h2-ui/`