import React, { useEffect } from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';

import HomeHeader from '../components/home/home-header';
import UserInbox from '../components/home/user-inbox';

import '../css/home.css';

export default function Home() {
    const username: string = window.localStorage.getItem('bchat-username') || "";
    const navigate: NavigateFunction = useNavigate();

    useEffect(() => {
        if (username === "") {
            navigate("/auth", {});
        }
    }, [navigate, username]);

    return <>
        <div className="home-wrapper">
            <HomeHeader />
            <div className="home-content">
                <UserInbox />
            </div>
        </div>
    </>;
}
