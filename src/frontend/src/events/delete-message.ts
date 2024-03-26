import { MouseEventHandler } from "react";
import Message from "../objects/message";
import API_RESPONSE_MESSAGES from "../cache/api-responses";
import { BasicAPIResponse } from "../objects/basic-api-response";

export function deleteMessage(message: Message, closeDeleteWindow: () => void): MouseEventHandler {
    const requestDelete = async () => {
        const BACKEND_SERVER_PORT = process.env.REACT_APP_BACKEND_SERVER_PORT || `8080`;
        const BACKEND_SERVER_URL = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

        const response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${message.conversationId}/message/delete`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(message)
        })

        if(response.status === 200) {
            closeDeleteWindow();
        } else {
            const data: BasicAPIResponse = await response.json();

            window.alert(API_RESPONSE_MESSAGES[data.validationMessage]);
        }
    }

    requestDelete();

    return () => {}
}