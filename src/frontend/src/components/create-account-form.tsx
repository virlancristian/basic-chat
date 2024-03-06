import React, { ChangeEventHandler, KeyboardEventHandler, useState } from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';

import API_RESPONSES from '../cache/api-responses';

export default function CreateAccountForm() {
    const BACKEND_SERVER_PORT : string = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
    const BACKEND_SERVER_URL : string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

    const navigate: NavigateFunction = useNavigate();
    const [body, setBody] = useState({
        username: '',
        password: ''
    });

    const changeRequestBody: ChangeEventHandler = (event : any) => {
        const {name, value} = event.target;

        setBody((prevBody) => ({
            ...prevBody,
            [name]: value
        }))
    }

    const verifyKey: KeyboardEventHandler = (event: any) => {
        if(event.key === 'Enter') {
            createAccount();
        }
    }

    const createAccount: any = async () => {
        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/user/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
        const data: any = await response.json();

        window.alert(API_RESPONSES[data['validationMessage']]);

        if(response.status === 200) {
            window.localStorage.setItem('bchat-username', body.username);
            navigate("/", {});
        }
    }


    return <div className="create-account-form">
        <div className="username">
            <p>Username</p>
            <input type="text" className="username-field" name="username" value={body.username} onChange={changeRequestBody}/>
        </div>
        <div className="password">
            <p>Password</p>
            <input type="password" className="password-field" name="password" value={body.password} onChange={changeRequestBody} onKeyUp={verifyKey}/>
        </div>
        <div className="create-account-button" onClick={createAccount}>
            Create account
        </div>
    </div>
}