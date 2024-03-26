import { useInput } from "../../hooks/use-input";
import { createConversation } from "../../events/create-conversation";
import { checkForKey } from "../../events/key-press";
import { Dispatch, SetStateAction, KeyboardEvent } from "react";
import Conversation from "../../objects/conversation";

export default function CreateConversationForm({username, visible, setVisibility, setConversation}: 
                                                { username: string; 
                                                visible: boolean; 
                                                setVisibility: () => void; 
                                                setConversation: Dispatch<SetStateAction<Conversation>> }) {
    const { input, changeInput } = useInput();

    return visible ?
            <div className="create-conversation-form-wrapper">
                <div className="create-conversation-form">
                    <div className="close-create-conversation-form" onClick={setVisibility}>X</div>
                    <h3>Start a conversation</h3>
                    <input type="text" 
                            className="search-user" 
                            placeholder="Search username" 
                            onChange={changeInput} 
                            onKeyUp={(event) => checkForKey('Enter', event, createConversation, {username, input, setVisibility, setConversation})}/>
                    <div className="create-conversation-button" 
                            onClick={() => createConversation(username, input, setVisibility, setConversation)}>
                        Start conversation
                    </div>
                </div>
            </div>
            : <></>
}