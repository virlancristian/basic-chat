import React, { useState, useEffect } from 'react';
import cachedb from '../../cache/cachedb';

async function getConversations(username: string) {
    const BACKEND_SERVER_PORT:string = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
    const BACKEND_SERVER_URL:string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

    const response:Response = await fetch(`${BACKEND_SERVER_URL}/api/user/${username}/conversation`);
    const data:any = await response.json();

   return data;
}

async function getInbox(conversationId:number)  {
    const BACKEND_SERVER_PORT:string = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
    const BACKEND_SERVER_URL:string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

    const response:Response = await fetch(`${BACKEND_SERVER_URL}/api/conversation/${conversationId}/message?type=inbox`);
    const data:any = await response.json();

    cachedb.addAllElements('inbox', 'conversationId', data);
}

export default function UserInbox() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            const conversations = await getConversations(username);

            for(let converstation of conversations) {
                await getInbox(converstation.conversationId);
            }

            cachedb.addAllElements('conversations', 'conversationId', conversations);
            setLoading(false);
        }

        fetchData();
    }, [])

    return <div className="user-inbox">
        <input type="text" className="search-conversation" placeholder='Search conversation' />
        {
            loading ? <></> :
            Object
            .keys(cachedb
                    .cache['conversations'])
            .map((conversation:any) => (
                <div className="conversation" key={conversation}>
                    <p id='recipient'>{
                        cachedb.cache['conversations'][conversation]['firstParticipant'] !== username
                        ? cachedb.cache['conversations'][conversation]['firstParticipant']
                        : cachedb.cache['conversations'][conversation]['secondParticipant']
                    }</p>
                    <p id='message'>
                        {
                            cachedb.cache['inbox'][conversation] !== undefined
                            ? (cachedb.cache['inbox'][conversation]['url'] !== undefined
                                ? 'Image'
                                : cachedb.cache['inbox'][conversation]['message'])
                            : <></>
                        }
                    </p>
                </div>
            ))
        }
    </div>
}