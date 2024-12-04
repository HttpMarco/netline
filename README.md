
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

### 3.2 Send the first request
With any component that has a `TrackingProvider`, you can send a request to a specific channel.
In the following example, we send a synchronous request using the previously defined scheme. We send a String and receive a long in return.
```java
var timeResuelt = client.request(myRequestScheme).send("TestProfile").sync();
```