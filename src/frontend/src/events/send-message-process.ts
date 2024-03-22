import { ChangeEvent, MouseEventHandler, KeyboardEvent } from "react";
import Conversation from "../objects/conversation";
import { getDate, getHour } from "../util/time";
import Message from "../objects/message";
import { BasicAPIResponse } from "../objects/basic-api-response";

export function sendMessage(conversation: Conversation, username: string, message: string): MouseEventHandler<HTMLDivElement> {
    const date: string = getDate();
    const hour: string = getHour();

    const newMessage: Message = {
        conversationId: conversation.conversationId,
        contentType: 1,
        message: message,
        sender: username,
        receiver: conversation.firstParticipant !== username ? conversation.firstParticipant : conversation.secondParticipant,
        date: date,
        hour: hour
    }

    const requestAddMessage = async () => {
        const BACKEND_SERVER_PORT = process.env.REACT_APP_BACKEND_SERVER_PORT || `8080`;
        const BACKEND_SERVER_URL = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${conversation.conversationId}/message/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newMessage)
        });
        const data: BasicAPIResponse = await response.json();

        if(response.status === 200) {
            console.log('Success!');
        }
    }

    requestAddMessage();

    return () => {};
}

export function clearMessageBox(event: KeyboardEvent<HTMLDivElement>, changeInput: (event: ChangeEvent<HTMLInputElement> | KeyboardEvent<HTMLDivElement>) => void): void {
    event.currentTarget.textContent = '';
    changeInput(event);
}

export function sendMessagesProcess(conversation: Conversation, 
                                    username: string, 
                                    message: string,
                                    event: KeyboardEvent<HTMLDivElement>,
                                    changeInput: (event: ChangeEvent<HTMLInputElement> | KeyboardEvent<HTMLDivElement>) => void): void {
    sendMessage(conversation, username, message);
    clearMessageBox(event, changeInput);
}