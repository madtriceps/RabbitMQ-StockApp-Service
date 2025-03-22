```md
# RabbitMQ WebSocket Stock Update System

This project is a Spring Boot application that integrates **RabbitMQ** and **WebSockets** to provide real-time stock updates.  
Stock prices are generated automatically every 2 seconds and pushed to connected WebSocket clients.

## Prerequisites

Ensure you have the following installed before running the project:

- **Docker** (for RabbitMQ)
- **Java 17** (or a compatible version)
- **Maven**
- **Node.js & npm** (for frontend)

---

## **1. Setup RabbitMQ with Docker**

Run the following command to start RabbitMQ with a management dashboard:

Docker Command:
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management


```

- RabbitMQ runs on **port 5672**  
- Management UI is accessible at: **http://localhost:15672**  
  - Username: `guest`  
  - Password: `guest`  

---

## **2. Backend Setup (Spring Boot)**
### **Clone and Build the Project**
```sh
git clone https://github.com/your-repo.git
cd RabbitMQ_Demo
mvn clean install (optional)
```

### **Required Maven Dependencies**
Ensure these dependencies are present in `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-scheduling</artifactId>
</dependency>
```

### **Run the Backend through mvn cli or simply IDE**
```sh
mvn spring-boot:run
```

By default, the server runs on **port 8080**.

---

## **3. Frontend Setup (React)**
### **Install Required Packages**
In the frontend directory, run:

```sh
npm install @stomp/stompjs sockjs-client
```

### **WebSocket Connection (React Hook)**
Ensure your frontend WebSocket client subscribes correctly:

```js
import { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";

const useWebSocket = () => {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        const client = new Client({
            brokerURL: "ws://localhost:8080/ws/websocket",
            onConnect: () => {
                console.log("Connected to WebSocket");
                client.subscribe("/topic/stocks", (message) => {
                    console.log("Received:", message.body);
                    setMessages((prev) => [...prev, message.body]);
                });
            },
            onStompError: (frame) => console.error("STOMP Error:", frame),
        });

        client.activate();

        return () => client.deactivate();
    }, []);

    return messages;
};

export default useWebSocket;
```

---

## **4. Testing the System**
### **Check if Stock Updates Are Being Sent**
The `StockProducer` sends stock updates **every 2 seconds**.  
Check the logs in the backend terminal:
```
Sent stock update: AAPL:120.34
Sent stock update: AAPL:118.45
```

### **Verify WebSocket Messages**
Run the frontend and check if stock updates appear in the browser console.

### **Manually Send a Message (Optional)**
If needed, use **Postman** or `curl` to manually send a message:

```sh
curl -X POST http://localhost:8080/send
```

---

## **5. RabbitMQ Web UI**
To verify messages in RabbitMQ:
- Open **http://localhost:15672**
- Navigate to **Queues → myQueue**
- Click **Get Messages** to see messages in the queue.

---

## **6. Troubleshooting**
### **Scheduler Not Running?**
Make sure `@EnableScheduling` is added:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
}
```

### **WebSocket Not Receiving Messages?**
1. Ensure RabbitMQ is running:
   ```sh
   docker ps
   ```
2. Restart both backend and frontend.
3. Check the **browser console logs** for WebSocket connection errors.
4. Verify the correct topic subscription:  
   - Should be `/topic/stocks` in both **backend** and **frontend**.

---

## **7. Summary**
| Component | Description |
|-----------|-------------|
| **RabbitMQ** | Handles message queueing |
| **Spring Boot** | Backend logic with WebSocket & RabbitMQ |
| **React** | Frontend subscribing to WebSocket |

Everything should now be working!   

---------Flow Explained ---------------------------


Flow Explanation:
Stock Producer (Scheduler in Spring Boot)

Sends stock updates to RabbitMQ Exchange every 2 seconds.

RabbitMQ Exchange & Queue

Routes the message to the correct queue where the consumer listens.

Stock Consumer (RabbitMQ Listener)

Receives messages and forwards them to WebSocket subscribers.

WebSocket Broker (Spring Boot WebSocket)

Broadcasts the stock updates to React frontend subscribers via /topic/stocks.

React Frontend (STOMP.js WebSocket Client)

Subscribes to /topic/stocks and displays real-time updates.
