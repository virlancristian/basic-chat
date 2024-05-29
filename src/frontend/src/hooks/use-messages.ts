import { useState, useEffect, useRef } from "react"
import SockJS from "sockjs-client";
import { CompatClient, IFrame, Stomp } from "@stomp/stompjs";

import Message from "../objects/message"
import Conversation from "../objects/conversation";
import { API_URL } from "../cache/MiscConstants";
import SocketResponse from "../objects/SocketResponse";

export const useMessages = () => {
    const [messages, setMessages] = useState<Message[]>([]);
    const [conversation, setConversation] = useState<Conversation>({
        conversationId: 0,
        firstParticipant: '',
        secondParticipant: ''
    });
    const [loadingMessages, setLoadingMessages] = useState<boolean>(false);
    const messageListDiv = useRef<HTMLDivElement>(null);
    const [client, setClient] = useState<CompatClient | null>(null);
    const [modifiedMessageID, setModifiedMessageID] = useState<number>(0);
    const [modificationType, setModificationType] = useState<string>('');
    const [socketResponse, setSocketResponse] = useState<SocketResponse>({
        updatedMessageID: 0,
        updateType: ''
    });

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

        const messageListDiv:HTMLDivElement = document.querySelector('#message-list') as HTMLDivElement;

        if(messageListDiv.scrollTop === messageListDiv.scrollHeight) {
            messageListDiv.scrollTop = messageListDiv.scrollHeight;
        }
    }

    const subscribeConversationSocket = () => {
        const socket: WebSocket = new SockJS(`${API_URL}/socket`);
        const client: CompatClient = Stomp.over(socket);

        client.onConnect = (frame: IFrame) => {
            console.log(`Connected to socket! ${frame}`);

            client.subscribe(`/socket/conversation/${conversation.conversationId}`, (response: any) => {
                const binaryData = response.binaryBody;
                const text = new TextDecoder().decode(binaryData);
                const responseBody: SocketResponse = JSON.parse(text);
  
                setSocketResponse(responseBody);
            });
        }

        client.activate();
        setClient(client);
    }

    useEffect(() => {
        fetchConversationRecentMessages();
        subscribeConversationSocket();
    }, [conversation]);

    useEffect(() => {
        const interval = setInterval(fetchMostRecentMessage, 750);

        return () => clearInterval(interval);
    }, [messages])

    useEffect(() => {
        const interval = setInterval(() => {
            const messageListDiv:HTMLDivElement = document.querySelector('#message-list') as HTMLDivElement;
        
            if(messageListDiv.scrollTop === 0 && messages.length > 0) {
                fetchOlderMessages();
                messageListDiv.scrollTop = 100;
            }
        }, 750);

        return () => clearInterval(interval);
    }, [messages])

    useEffect(() => {
        if(messages.length <= 105 && messageListDiv.current) {
            setTimeout(() => {
                messageListDiv.current!.scrollTop = messageListDiv.current!.scrollHeight;
            }, 0);
        }
    }, [messages]);

    useEffect(() => {
        try {
            let updatedMessages: Message[] = [];

            if(socketResponse.updateType === 'deleted') {
                updatedMessages = messages.filter((message: Message) => message.messageId !== socketResponse.updatedMessageID && message.imageId !== socketResponse.updatedMessageID);
            } else {
                for(const message of messages) {
                    if(message.messageId === socketResponse.updatedMessageID) {
                        updatedMessages.push({
                            ...message,
                            message: socketResponse.newMessage
                        });
                    } else {
                        updatedMessages.push(message);
                    }
                }
            }

            setMessages(updatedMessages);
        } catch(error) {
            console.log(`Error while trying to update messages: ${messages}`);
        } finally {
            console.log(messages);
            //setModifiedMessageID(0);
        }
    }, [socketResponse])

    return {
        messages,
        loadingMessages,
        conversation,
        setConversation,
        messageListDiv
    };
}