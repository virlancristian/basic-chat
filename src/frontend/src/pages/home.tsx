import { useEffect } from 'react';
import HomeHeader from '../components/home/home-header';
import UserInbox from '../components/home/user-inbox';
import CreateConversationForm from '../components/home/create-conversation-form';

import '../css/home.css';
import { useFormVisbility} from '../hooks/use-form-visibility';

export default function Home() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const {visible, setVisibility} = useFormVisbility(false);

    useEffect(() => {
        if (username === "") {
            window.location.href = '/auth';
        }
    }, []);

    return <>
        <div className="home-wrapper">
            <HomeHeader />
            <div className="home-content">
                <UserInbox setVisibility={setVisibility}/>
            </div>
            <CreateConversationForm username={username} visible={visible} setVisibility={setVisibility}/>
        </div>
    </>;
}
