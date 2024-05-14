import { useState, useEffect } from 'react';
import Message from "../objects/message";

export const useConversationHistory = (recentMessages: Message[]) => {
    const [messageHistory, setMesageHistory] = useState<Message[]>([]);

    const addMessages = (newMessages: Message[]) => {
        setMesageHistory([...messageHistory, ...newMessages]);
    }

    useEffect(() => {
        addMessages(recentMessages);
    }, []);

    return {
        messageHistory,
        addMessages
    }
};