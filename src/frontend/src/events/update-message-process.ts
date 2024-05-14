import { Dispatch, SetStateAction } from "react";
import Message from "../objects/message";
import { BasicAPIResponse } from "../objects/basic-api-response";
import API_RESPONSE_MESSAGES from "../cache/api-responses";

async function updateMessage(message: Message) {
    const BACKEND_SERVER_PORT: string = process.env.REACT_APP_BACKEND_SERVER_PORT || `8080`;
    const BACKEND_SERVER_URL: string  = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

    const response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${message.conversationId}/message/update`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message)
    });
    const data: BasicAPIResponse = await response.json();

    console.log(API_RESPONSE_MESSAGES[data.validationMessage]);
}

export function updateMessageProcess(message: Message, newMessage: string, setMessageId: Dispatch<SetStateAction<number>>) {
    message.updatedMessage = newMessage;
    setMessageId(0);
    updateMessage(message);
}