import { useState, useEffect } from 'react';
import Message from '../objects/message';
import Conversation from '../objects/conversation';
import { ConversationInbox, compare } from '../objects/conversation-inbox';

export const useConversationsInbox = (username: string) => {
    const BACKEND_SERVER_PORT: string = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
    const BACKEND_SERVER_URL: string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;
    const [visibleInbox, setInbox] = useState<ConversationInbox[]>([]);
    const [fullInbox, setFullInbox] = useState<ConversationInbox[]>([]);
    
    const fetchInboxMessage = async () => {
        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/user/${username}/conversation`);
        const conversations: Conversation[] = await response.json();
        let inboxArray:ConversationInbox[] = []; 

        if(response.status === 200) {
            for(const conversation of conversations) {
                const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${conversation.conversationId}/message?type=inbox`)
                const message: Message[] = await response.json();
    
                if(response.status === 200) {
                    const newInbox: ConversationInbox = {
                        conversationId: conversation.conversationId,
                        firstParticipant: conversation.firstParticipant,
                        secondParticipant: conversation.secondParticipant,
                        contentType: message[0].contentType,
                        message: message[0].message,
                        url: message[0].url,
                        date: message[0].date,
                        hour: message[0].hour,
                        receiver: message[0].receiver
                    }

                    inboxArray = [...inboxArray, newInbox];
                }
            }

            inboxArray.sort(compare);
            setFullInbox(inboxArray);
        }
    }

    const changeInbox = (conversations: ConversationInbox[]) => {
        setInbox(conversations);
    }

    useEffect(() => {
        const interval = setInterval(fetchInboxMessage, 1000);

        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        if(visibleInbox.length === 0 || (visibleInbox.length === fullInbox.length && fullInbox.length > 0)) {
            setInbox(fullInbox);
        }
    }, [fullInbox])

    return { fullInbox, visibleInbox, changeInbox };
}