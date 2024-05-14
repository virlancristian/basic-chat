import { UIEventHandler } from "react";
import Message from "../objects/message";

export function detectTopScroll(messages: Message[], addMessages: (newMessages: Message[], position: string) => void): UIEventHandler<HTMLDivElement> {
    const messageListDiv: HTMLDivElement = document.querySelector('.message-list') || new HTMLDivElement();

    if(messageListDiv.scrollTop === 0) {
        getTimestampedMessages(messages).then((newMessages) => {
            console.log(newMessages);
            console.log(messages);
            if(newMessages.length > 0) {
                if(messages[0].messageId !== newMessages[0].messageId) {
                    addMessages(newMessages.reverse(), 'start')
                    messageListDiv.scrollTop = messageListDiv.scrollHeight / 2;
                }
            }
        });
    }

    return () => {};
}

async function getTimestampedMessages(messages: Message[]): Promise<Message[]> {
    const BACKEND_SERVER_PORT: string = process.env.REACT_APP_BACKEND_SERVER_PORT || `8080`;
    const BACKEND_SERVER_URL: string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

    const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${messages[0].conversationId}/message?timestamp=${messages[0].date}%20${messages[0].hour}`);
    const data: Message[] = await response.json();

    return data;
}