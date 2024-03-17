import React, { useState, useEffect, ChangeEventHandler, ChangeEvent, KeyboardEventHandler, SetStateAction } from 'react';
import { useConversationsInbox } from '../../hooks/use-conversations-inbox';
import { ConversationInbox } from '../../objects/conversation-inbox';
import ConversationList from './conversation-list';
import CreateConversationWindowOpen from './create-conversation-button';

interface Props {
    setVisibility: () => void;
};

export default function UserInbox({ setVisibility }: Props) {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const inbox: ConversationInbox[] = useConversationsInbox(username);

    return <div className="user-inbox">
        <div className="search-conversation-wrapper">
            <input type="text" className="search-conversation" name='recipient' placeholder='Search conversation'/>
            <CreateConversationWindowOpen setVisibility={setVisibility}/>
        </div>
        <ConversationList inbox={inbox} username={username}/>
    </div>
}