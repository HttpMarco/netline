
## 3. Request and Response

### 3.1 Define request scheme
To handle and identify every request, you need to define a request scheme.
1. Request ID: The request ID defines the communication channel.
2. Request Type: Specifies the type of the request being sent. You can use either custom packet classes or Java objects.
3. Response Type: Specifies the type of the response. Similarly, you can use custom packet classes or Java objects.

Example of a Simple Request Scheme:
```java
// example with the request type 'String' and the response 'Long'
RequestScheme.from("request_id", String.class, Long.class);
```

### 3.2 Define the response of a request
If you send a request to the server, the server must handle this request. This can be achieved using responders.
Responders process the incoming request and allow you to generate an appropriate response. The response type must match the type defined in the request scheme.
```java
// register the response for the defined scheme. Result here is Long.
server.waitFor(myRequestScheme, (id, channel) -> 99);
```

### 3.3 Send the first request
With any component that has a `TrackingProvider`, you can send a request to a specific channel.
In the following example, we send a synchronous request using the previously defined scheme. We send a String and receive a long in return.
```java
var timeResuelt = client.request(myRequestScheme).send("TestProfile").sync();
```


[ ] subscribe tracking and send the server the information about the tracking 
[ ] tracking readme
[ ] dependency readme
[ ] update id 
[ ] remove request if server close the connection
