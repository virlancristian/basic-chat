import { useState, useEffect } from "react"
import Message from "../objects/message"
import Conversation from "../objects/conversation";
import { API_URL } from "../cache/MiscConstants";

export const useMessages = () => {
    const [messages, setMessages] = useState<Message[]>([]);
    const [conversation, setConversation] = useState<Conversation>({
        conversationId: 0,
        firstParticipant: '',
        secondParticipant: ''
    });
    const [loadingMessages, setLoadingMessages] = useState<boolean>(false);

    const fetchConversationRecentMessages = async () => {
        try {
            setLoadingMessages(true);

            const response: Response = await fetch(`${API_URL}/api/conversation/${conversation.conversationId}/message`);
            const data: Message[] = await response.json();

            setMessages(data.reverse());
        } catch(error) {
            console.log(`Error while fetching conversation messages: ${error}`);
        } finally {
            setLoadingMessages(false);
        }
    }

    const fetchMostRecentMessage = async () => {
        const response: Response = await fetch(`${API_URL}/api/conversation/${conversation.conversationId}/message?type=inbox`);
        const data: Message[] = await response.json();

        if(data.length > 0 && messages.length > 0 && 
            (data[0].messageId !== messages[messages.length - 1].messageId ||
             data[0].contentType !== messages[messages.length - 1].contentType ||
             data[0].imageId !== messages[messages.length - 1].imageId   
            )) {
            setMessages((prevMessages) => ([
                ...prevMessages,
                data[0]
            ]));
        }
    }

    const fetchOlderMessages = async () => {
        const response: Response = await fetch(`${API_URL}/api/conversation/${conversation.conversationId}/message?timestamp=${messages[0].date + ' ' + messages[0].hour}`);
        const data: Message[] = await response.json();

        setMessages((prevMessages) => ([
            ...data.reverse(),
            ...prevMessages
        ]));

        const messageListDiv:HTMLDivElement = document.querySelector('.message-list') as HTMLDivElement;

        if(messageListDiv.scrollTop === messageListDiv.scrollHeight) {
            messageListDiv.scrollTop = messageListDiv.scrollHeight;
        }
    }

    useEffect(() => {
        fetchConversationRecentMessages();
    }, [conversation]);

    useEffect(() => {
        const interval = setInterval(fetchMostRecentMessage, 750);

        return () => clearInterval(interval);
    }, [messages])

    useEffect(() => {
        const interval = setInterval(() => {
            const messageListDiv:HTMLDivElement = document.querySelector('.message-list') as HTMLDivElement;
        
            if(messageListDiv.scrollTop === 0 && messages.length > 0) {
                fetchOlderMessages();
                messageListDiv.scrollTop = 100;
            }
        }, 750);

        return () => clearInterval(interval);
    }, [messages])

    useEffect(() => {
        if(messages.length <= 105) {
            const messageListDiv:HTMLDivElement = document.querySelector('.message-list') as HTMLDivElement;
            messageListDiv.scrollTop = messageListDiv.scrollHeight;
        }
    }, [messages]);

    return {
        messages,
        loadingMessages,
        conversation,
        setConversation
    };
}