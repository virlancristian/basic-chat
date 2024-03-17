import React from "react";
import { useRecipient } from "../../hooks/use-recipient";

interface Props {
    username: string;
    visible: boolean;
    setVisibility: () => void;
}

export default function CreateConversationForm({username, visible, setVisibility}: Props) {
    const { recipient, changeRecipient } = useRecipient();

    return visible ?
            <div className="create-conversation-form-wrapper">
                <div className="create-conversation-form">
                    <div className="close-create-conversation-form" onClick={setVisibility}>X</div>
                    <h3>Start a conversation</h3>
                    <input type="text" className="search-user" placeholder="Search username" onChange={changeRecipient}/>
                    <div className="create-conversation-button">Start conversation</div>
                </div>
            </div>
            : <></>
}