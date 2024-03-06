import React, { useEffect } from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';

export default function Home() {
    const username : string = window.localStorage.getItem('bchat-username') || "";
    const navigate : NavigateFunction = useNavigate();

    useEffect(() => {
        if (username === "") {
            navigate("/auth", {});
        }
    }, [navigate, username]);

    return <p>Home</p>;
}
