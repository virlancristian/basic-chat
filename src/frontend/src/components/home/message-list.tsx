import { useState, Dispatch, SetStateAction, KeyboardEvent, useEffect } from "react";
import Message from "../../objects/message";
import { useInput } from "../../hooks/use-input";
import { checkForKey } from "../../events/key-press";
import { updateMessageProcess } from "../../events/update-message-process";
import { scrollMessageList } from "../../events/scroll-message-list";
import { detectTopScroll } from "../../events/detect-top-scroll";
import Conversation from "../../objects/conversation";

export default function MessageList({ messages, conversation, setDeleteWindowVisibility, setDeleteMessage }: { messages: Message[], conversation: Conversation, setDeleteWindowVisibility: () => void, setDeleteMessage: Dispatch<SetStateAction<Message>> }) {
    const username: string = window.localStorage.getItem('bchat-username') || ``;
    const { input, changeInput } = useInput();
    const [messageId, setMessageId] = useState<number>(0);
    const [lastMessageId, setLastMessageId] = useState<number>(0);

    return <div className="message-list">
        {
            messages.map((message) => (
                message.receiver === username
                    ? <div className="other-recipient-message-box" key={message.messageId}>
                        <p className="other-recipient-username">{message.sender}</p>
                        {
                            message.contentType === 1 ?
                            <p className="other-recipient-message">{message.message}</p> :
                            <img src={`${message.url}?image_id=${message.imageId}`} id='image-message'/>
                        }
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
                            {
                                message.contentType === 1 ?
                                
                            <p className="user-recipient-message" 
                            contentEditable={messageId === message.messageId} 
                            onKeyUp={(event: KeyboardEvent<HTMLDivElement>) => {
                                changeInput(event);
                                checkForKey('Enter', event, updateMessageProcess, { message, input, setMessageId });
                            }}>
                                {message.message}
                        </p> : <img src={`${message.url}?image_id=${message.imageId}`} id='image-message'/>
                            }
                            {
                                messageId === message.messageId
                                ? <p className="update-message-button" onClick={() => {updateMessageProcess(message, input, setMessageId)}}>Update</p>
                                : <></>
                            }
                            <p className="hour">{message.hour.substring(0, 5)}</p>
                        </div>
            ))
        }
    </div>
}