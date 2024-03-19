import { MouseEventHandler } from 'react';
import { BasicAPIResponse } from '../objects/basic-api-response';
import API_RESPONSE_MESSAGES from '../cache/api-responses';

export function createConversation(firstRecipient: string, secondRecipient: string): MouseEventHandler<HTMLDivElement> {
    const requestConversationCreation = async () => {
        const BACKEND_SERVER_PORT = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
        const BACKEND_SERVER_URL = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;
        
        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/user/${firstRecipient}/conversation/add?recipient=${secondRecipient}`, {
            method: 'POST'
        });
        const data: BasicAPIResponse = await response.json();

        window.alert(API_RESPONSE_MESSAGES[data.validationMessage]);
    }

    requestConversationCreation();

    return () => {}
}