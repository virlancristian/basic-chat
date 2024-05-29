import { useState, Dispatch, SetStateAction, KeyboardEvent, useEffect } from "react";
import Message from "../../objects/message";
import { useInput } from "../../hooks/use-input";
import { checkForKey } from "../../events/key-press";
import { updateMessageProcess } from "../../events/update-message-process";
import { scrollMessageList } from "../../events/scroll-message-list";
import { detectTopScroll } from "../../events/detect-top-scroll";
import Conversation from "../../objects/conversation";

import '../../css/message-list.css';

export default function MessageList({ messages, conversation, setDeleteWindowVisibility, setDeleteMessage, messageListDiv }: { messages: Message[], conversation: Conversation, setDeleteWindowVisibility: () => void, setDeleteMessage: Dispatch<SetStateAction<Message>>, messageListDiv: React.RefObject<HTMLDivElement> }) {
    const username: string = window.localStorage.getItem('bchat-username') || ``;
    const { input, changeInput } = useInput();
    const [messageId, setMessageId] = useState<number>(0);
    const [lastMessageId, setLastMessageId] = useState<number>(0);
    const [hoveredMessageIndex, setHoveredMessageIndex] = useState<number>(0);

    return <div className="overflow-y-scroll bg-gray-700 bg-opacity-30 flex flex-col rounded-xl my-3" id='message-list' ref={messageListDiv}>
        {
            messages.map((message, index) => (
                message.receiver === username
                    ? <div className="bg-gray-900 bg-opacity-40 rounded-xl m-3 break-words" id='other-recipient-message-box' key={message.messageId}>
                        <p className="text-gray-500 ml-3">{message.sender}</p>
                        {
                            message.contentType === 1 ?
                            <p className="text-gray-300 overflow-wrap mx-3">{message.message}</p> :
                            <img src={`${message.url}?image_id=${message.imageNumber}`} id='image-message'/>
                        }
                        <p className="text-gray-500 ml-3">{message.hour.substring(0, 5)}</p>
                    </div>
                    :
                        <div className="break-words rounded-xl m-3 bg-green-800" 
                            id='user-recipient-message-box' 
                            key={message.messageId}
                            onMouseEnter={() => setHoveredMessageIndex(index)}
                            onMouseLeave={() => setHoveredMessageIndex(-1)}>
                            <div className="flex flex-row">
                                <p className="text-gray-500 mx-3">You</p>
                                {hoveredMessageIndex === index && message.contentType === 1 && <p className="cursor-pointer mr-3 hover:underline" onClick={() => setMessageId(message.messageId || 0)}>Edit</p>}
                                {hoveredMessageIndex === index && <p className="cursor-pointer mr-3 hover:underline hover:text-red-600 hover:font-semibold" onClick={() => {
                                    setDeleteMessage(message);
                                    setDeleteWindowVisibility();
                                }}>Delete</p>}
                            </div>
                            {
                                message.contentType === 1 ?
                                
                            <p className="text-gray-300 mx-3 outline-none border-none" 
                            contentEditable={messageId === message.messageId} 
                            onKeyUp={(event: KeyboardEvent<HTMLDivElement>) => {
                                changeInput(event);
                                checkForKey('Enter', event, updateMessageProcess, { message, input, setMessageId });
                            }}>
                                {message.message}
                        </p> : <img src={`${message.url}?image_id=${message.imageNumber}`} id='image-message'/>
                            }
                            {
                                messageId === message.messageId
                                ? <div className="flex">
                                    <p className="cursor-pointer ml-3 hover:underline" onClick={() => {updateMessageProcess(message, input, setMessageId)}}>Update</p>
                                    <p className="cursor-pointer ml-3 hover:underline" onClick={() => setMessageId(0)}>Cancel</p>
                                  </div>
                                : <></>
                            }
                            {messageId !== message.messageId && <p className="text-gray-500 ml-3">{message.hour.substring(0, 5)}</p>}
                        </div>
            ))
        }
    </div>
}