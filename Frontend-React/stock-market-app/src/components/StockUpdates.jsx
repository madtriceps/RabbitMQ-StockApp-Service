import React from "react";
import useWebSocket from "../hooks/useWebSocket";

const StockUpdates = () => {
    const messages = useWebSocket();

    return (
        <div>
            <h2>Real-Time Stock Updates We are pulling from RabbitMQ !!!</h2>
            {console.log(messages)}
            <ul>
                {messages.map((msg, index) => (
                    <li key={index}>{msg}</li>
                ))}
            </ul>
        </div>
    );
};

export default StockUpdates;
