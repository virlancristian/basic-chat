import { useInput } from "../../hooks/use-input";
import { createConversation } from "../../events/create-conversation";
import { checkForKey } from "../../events/key-press";

export default function CreateConversationForm({username, visible, setVisibility}: { username: string; visible: boolean; setVisibility: () => void }) {
    const { input, changeInput } = useInput();

    return visible ?
            <div className="create-conversation-form-wrapper">
                <div className="create-conversation-form">
                    <div className="close-create-conversation-form" onClick={setVisibility}>X</div>
                    <h3>Start a conversation</h3>
                    <input type="text" className="search-user" placeholder="Search username" onChange={changeInput} onKeyDown={(event) => checkForKey('Enter', event, createConversation, {username, input})}/>
                    <div className="create-conversation-button" onClick={() => createConversation(username, input)}>Start conversation</div>
                </div>
            </div>
            : <></>
}