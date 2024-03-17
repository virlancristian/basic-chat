import React, { useState, useEffect } from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';

import HomeHeader from '../components/home/home-header';
import UserInbox from '../components/home/user-inbox';
import CreateConversationForm from '../components/home/create-conversation-form';

import '../css/home.css';
import { useFormVisbility} from '../hooks/use-form-visibility';

export default function Home() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const navigate: NavigateFunction = useNavigate();
    const {visible, setVisibility} = useFormVisbility(false);

    useEffect(() => {
        if (username === "") {
            navigate("/auth", {});
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
