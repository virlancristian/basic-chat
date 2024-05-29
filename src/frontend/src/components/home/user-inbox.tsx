import { useEffect, Dispatch, SetStateAction } from 'react';
import { useConversationsInbox } from '../../hooks/use-conversations-inbox';
import ConversationList from './conversation-list';
import CreateConversationWindowOpen from './create-conversation-button';
import SearchConversation from './search-conversation';
import Conversation from '../../objects/conversation';

import '../../css/user-inbox.css';

export default function UserInbox({ setVisibility, setConversation }: { setVisibility: () => void; setConversation: Dispatch<SetStateAction<Conversation>> }) {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const { fullInbox, visibleInbox, changeInbox } = useConversationsInbox(username);

    return <div className="overflow-y-auto bg-gray-800 bg-opacity-40 rounded-xl" id='user-inbox'>
        <div className="search-conversation-wrapper">
            <SearchConversation inbox={fullInbox} changeInbox={changeInbox}/>
            <CreateConversationWindowOpen setVisibility={setVisibility}/>
        </div>
        <ConversationList inbox={visibleInbox} username={username} setConversation={setConversation}/>
    </div>
}