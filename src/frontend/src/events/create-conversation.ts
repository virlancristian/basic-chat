import { Dispatch, MouseEventHandler, SetStateAction } from 'react';
import { BasicAPIResponse } from '../objects/basic-api-response';
import API_RESPONSE_MESSAGES from '../cache/api-responses';
import Conversation from '../objects/conversation';

export function createConversation(firstRecipient: string, secondRecipient: string, setVisibility: () => void, setConversation: Dispatch<SetStateAction<Conversation>>): MouseEventHandler<HTMLButtonElement> {
    const requestConversationCreation = async () => {
        const BACKEND_SERVER_PORT = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
        const BACKEND_SERVER_URL = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;
        
        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/user/${firstRecipient}/conversation/add?recipient=${secondRecipient}`, {
            method: 'POST'
        });
        const data: BasicAPIResponse = await response.json();

        window.alert(API_RESPONSE_MESSAGES[data.validationMessage]);

        if(response.status === 200) {
            setVisibility();

            const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/user/${firstRecipient}/conversation?recipient=${secondRecipient}`);
            const conversation: Conversation[] = await response.json();

            setConversation(conversation[0]);
        }
    }

    requestConversationCreation();

    return () => {}
}