import { useEffect } from 'react';
import HomeHeader from '../components/home/home-header';
import UserInbox from '../components/home/user-inbox';
import CreateConversationForm from '../components/home/create-conversation-form';

import '../css/home.css';
import { useFormVisbility} from '../hooks/use-form-visibility';
import MessageBox from '../components/home/message-box';
import { useMessages } from '../hooks/use-messages';

export default function Home() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const {visible, setVisibility} = useFormVisbility(false);
    const { messages, conversation, setConversation } = useMessages();

    useEffect(() => {
        if (username === "") {
            window.location.href = '/auth';
        }
    }, []);

    return <>
        <div className="home-wrapper">
            <HomeHeader />
            <div className="home-content">
                <UserInbox setVisibility={setVisibility} setConversation={setConversation}/>
                <MessageBox messages={messages} conversation={conversation}/>
            </div>
            <CreateConversationForm username={username} visible={visible} setVisibility={setVisibility} setConversation={setConversation}/>
        </div>
    </>;
}
