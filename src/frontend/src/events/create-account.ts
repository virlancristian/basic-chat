import { MouseEventHandler } from "react";
import { Account } from "../objects/account";
import { BasicAPIResponse } from "../objects/basic-api-response";
import API_RESPONSE_MESSAGES from "../cache/api-responses";

export function createAccount(account: Account): MouseEventHandler<HTMLDivElement> {
    const requestAccountCreation = async () => {
        const BACKEND_SERVER_PORT: string = process.env.REACT_APP_BACKEND_SERVER_PORT || "8080";
        const BACKEND_SERVER_URL: string = process.env.REACT_APP_BACKEND_SERVER_URL || `http://localhost:${BACKEND_SERVER_PORT}`;

        const response: Response = await fetch(`${BACKEND_SERVER_URL}/api/user/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(account)
        });
        const data: BasicAPIResponse = await response.json();

        window.alert(API_RESPONSE_MESSAGES[data.validationMessage]);

        if(response.status === 200) {
            window.localStorage.setItem('bchat-username', account.username);
            window.location.href = '/';
        }
    }

    requestAccountCreation();

    return () => {}
}