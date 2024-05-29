import { KeyboardEvent } from "react";
import { checkForKey } from "../../events/key-press";
import { sendMessage, sendMessagesProcess } from "../../events/send-message-process";
import Conversation from "../../objects/conversation";
import { useInput } from "../../hooks/use-input";
import { handleUploadImage } from "../../events/HandleUploadImage";
import { handleFileChange } from "../../events/HandleFileChange";
import '../../css/write-message-box.css';

export default function WriteMessageBox({ conversation }: { conversation: Conversation }) {
    const { input, changeInput } = useInput();
    const username: string = window.localStorage.getItem('bchat-username') || ``;

    return ( 
        <div className="flex w-full justify-between">
            <button
                className="bg-green-800 hover:bg-green-900 w-24 h-8 rounded-xl text-white font-bold" 
                onClick={handleUploadImage}>Add image</button>
            <input className='hidden' id='upload-image-input' type="file" onChange={(event) => handleFileChange(event, conversation)}/>
            <div className="rounded bg-gray-700 bg-opacity-30 text-white pl-3 items-center overflow-y-auto" contentEditable='true'
                id='write-message-box'
                onKeyUp={(event: KeyboardEvent<HTMLDivElement>) => {
                    changeInput(event);
                    checkForKey('Enter', event, sendMessagesProcess, { conversation, username, input, event, changeInput });
                }}>
            </div>
        <button className="h-8 rounded-xl text-white font-bold w-32 bg-green-800 hover:bg-green-900" onClick={() => {
                    const enterMessageFieldDiv = document.querySelector('#write-message-box');

                    const event: any = {
                        currentTarget: enterMessageFieldDiv
                    };

                    sendMessagesProcess(conversation, username, input, event, changeInput);
                } 
            }>Send message</button>
        </div>
    )
}