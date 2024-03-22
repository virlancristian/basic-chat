import { useState, useEffect } from "react"
import Message from "../objects/message"
import Conversation from "../objects/conversation";

export const useMessages = () => {
    const [messages, setMessages] = useState<Message[]>([]);
    const [conversation, setConversation] = useState<Conversation>({
        conversationId: 0,
        firstParticipant: '',
        secondParticipant: ''
    });

    const fetchMessages = async () => {
        const BACKEND_SERVER_PORT = process.env.REACT_APP_BACKEND_SERVER_PORT || `8080`;
        const BACKEND_SERVER_URL = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${conversation.conversationId}/message`)
        const data: Message[] = await response.json();

        setMessages(data.reverse());
    }

    useEffect(() => {
        const interval = setInterval(() => {
            fetchMessages();
        }, 750);

        return () => clearInterval(interval);
    }, [conversation]);

    return {
        messages,
        conversation,
        setConversation
    }    
}