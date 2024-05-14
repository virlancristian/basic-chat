import { useEffect, useState } from 'react';
import HomeHeader from '../components/home/home-header';
import UserInbox from '../components/home/user-inbox';
import CreateConversationForm from '../components/home/create-conversation-form';
import Message from '../objects/message';

import '../css/home.css';
import { useFormVisbility} from '../hooks/use-form-visibility';
import MessageBox from '../components/home/message-box';
import { useMessages } from '../hooks/use-messages';
import DeleteMessageWindow from '../components/home/delete-message-window';

export default function Home() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const {visible, setVisibility} = useFormVisbility(false);
    const { visible: deleteWindowVisibility, setVisibility: setDeleteWindowVisibility } = useFormVisbility(false);
    const { messages, loadingMessages, setConversation, conversation } = useMessages();
    const [deleteMessage, setDeleteMessage] = useState<Message>({
        conversationId: 0,
        contentType: 0,
        sender: '',
        receiver: '',
        date: '',
        hour: ''
    });

    useEffect(() => {
        if (username === "") {
            window.location.href = '/auth';
        }
    }, [username]);

    return <>
        <div className="home-wrapper">
            <HomeHeader />
            <div className="home-content">
                <UserInbox setVisibility={setVisibility} setConversation={setConversation}/>
                <MessageBox messages={messages} 
                            conversation={conversation} 
                            setDeleteWindowVisibility={setDeleteWindowVisibility} 
                            setDeleteMessage={setDeleteMessage}/>
            </div>
            <CreateConversationForm username={username} visible={visible} setVisibility={setVisibility} setConversation={setConversation}/>
            <DeleteMessageWindow visible={deleteWindowVisibility} message={deleteMessage} setDeleteWindowVisibility={setDeleteWindowVisibility}/>
        </div>
    </>;
}
