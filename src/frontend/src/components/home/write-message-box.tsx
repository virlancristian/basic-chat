import { KeyboardEvent } from "react";
import { checkForKey } from "../../events/key-press";
import { sendMessage, sendMessagesProcess } from "../../events/send-message-process";
import Conversation from "../../objects/conversation";
import { useInput } from "../../hooks/use-input";

export default function WriteMessageBox({ conversation }: { conversation: Conversation }) {
    const { input, changeInput } = useInput();
    const username: string = window.localStorage.getItem('bchat-username') || ``;

    return <div className="enter-message-box">
        <div className="enter-message-field" contentEditable='true'
            onKeyUp={(event: KeyboardEvent<HTMLDivElement>) => {
                checkForKey('Enter', event, sendMessagesProcess, { conversation, username, input, event, changeInput });
            }}
            onKeyDown={changeInput}>
        </div>
        <div className="send-message-button" onClick={() => sendMessage(conversation, username, input)}></div>
    </div>
}