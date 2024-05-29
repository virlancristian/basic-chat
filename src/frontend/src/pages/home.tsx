import { useEffect, useState } from 'react';
import HomeHeader from '../components/home/home-header';
import UserInbox from '../components/home/user-inbox';
import CreateConversationForm from '../components/home/create-conversation-form';
import Message from '../objects/message';

import { useFormVisbility} from '../hooks/use-form-visibility';
import MessageBox from '../components/home/message-box';
import { useMessages } from '../hooks/use-messages';
import DeleteMessageWindow from '../components/home/delete-message-window';

import '../css/home.css';

export default function Home() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const {visible, setVisibility} = useFormVisbility(false);
    const { visible: deleteWindowVisibility, setVisibility: setDeleteWindowVisibility } = useFormVisbility(false);
    const { messages, loadingMessages, setConversation, conversation, messageListDiv } = useMessages();
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
            window.location.href = '/login';
        }
    }, [username]);

    return <>
        <div className="bg-black bg-opacity-90 flex flex-col h-full items-center">
            <HomeHeader />
            <div className="w-10/12 mt-3" id='main-box'>
                <table className='w-full'>
                    <tbody>
                        <tr className='w-full'>
                            <td id='user-inbox'>
                                <UserInbox setVisibility={setVisibility} setConversation={setConversation}/>
                            </td>
                            <td>
                                <MessageBox messages={messages} 
                                conversation={conversation} 
                                setDeleteWindowVisibility={setDeleteWindowVisibility} 
                                setDeleteMessage={setDeleteMessage}
                                messageListDiv={messageListDiv}/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <CreateConversationForm username={username} visible={visible} setVisibility={setVisibility} setConversation={setConversation}/>
            <DeleteMessageWindow visible={deleteWindowVisibility} message={deleteMessage} setDeleteWindowVisibility={setDeleteWindowVisibility}/>
        </div>
    </>;
}
