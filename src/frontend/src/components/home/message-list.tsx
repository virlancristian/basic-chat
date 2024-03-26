import { useState, Dispatch, SetStateAction, KeyboardEvent } from "react";
import Message from "../../objects/message";
import { useInput } from "../../hooks/use-input";
import { checkForKey } from "../../events/key-press";
import { updateMessageProcess } from "../../events/update-message-process";

export default function MessageList({ messages, setDeleteWindowVisibility, setDeleteMessage }: { messages: Message[], setDeleteWindowVisibility: () => void, setDeleteMessage: Dispatch<SetStateAction<Message>> }) {
    const username: string = window.localStorage.getItem('bchat-username') || ``;
    const { input, changeInput } = useInput();
    const [messageId, setMessageId] = useState<number>(0);

    return <div className="message-list">
        {
            messages.map((message) => (
                message.receiver === username
                    ? <div className="other-recipient-message-box" key={message.messageId}>
                        <p className="other-recipient-username">{message.sender}</p>
                        <p className="other-recipient-message">{message.message}</p>
                        <p className="hour">{message.hour.substring(0, 5)}</p>
                    </div>
                    :
                        <div className="user-recipient-message-box" key={message.messageId}>
                            <div className="user-recipient-message-box-header">
                                <p className="user-recipient-username">You</p>
                                <p className="edit-button" onClick={() => setMessageId(message.messageId || 0)}>Edit</p>
                                <p className="delete-button" onClick={() => {
                                    setDeleteMessage(message);
                                    setDeleteWindowVisibility();
                                }}>Delete</p>
                            </div>
                            <p className="user-recipient-message" 
                                contentEditable={messageId === message.messageId} 
                                onKeyUp={(event: KeyboardEvent<HTMLDivElement>) => {
                                    changeInput(event);
                                    checkForKey('Enter', event, updateMessageProcess, { message, input, setMessageId });
                                }}>
                                    {message.message}
                            </p>
                            {
                                messageId === message.messageId
                                ? <p className="update-message-button" onClick={() => {updateMessageProcess(message, input, setMessageId)}}>Send</p>
                                : <></>
                            }
                            <p className="hour">{message.hour.substring(0, 5)}</p>
                        </div>
            ))
        }
    </div>
}