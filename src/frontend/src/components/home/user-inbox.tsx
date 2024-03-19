import { useEffect } from 'react';
import { useConversationsInbox } from '../../hooks/use-conversations-inbox';
import ConversationList from './conversation-list';
import CreateConversationWindowOpen from './create-conversation-button';
import SearchConversation from './search-conversation';

export default function UserInbox({ setVisibility }: { setVisibility: () => void }) {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const { fullInbox, visibleInbox, changeInbox } = useConversationsInbox(username);

    useEffect(() => {
        if(fullInbox.length === visibleInbox.length || visibleInbox.length === 0) {
            changeInbox(fullInbox);
        }
    }, [fullInbox]);

    return <div className="user-inbox">
        <div className="search-conversation-wrapper">
            <SearchConversation inbox={fullInbox} changeInbox={changeInbox}/>
            <CreateConversationWindowOpen setVisibility={setVisibility}/>
        </div>
        <ConversationList inbox={visibleInbox} username={username}/>
    </div>
}