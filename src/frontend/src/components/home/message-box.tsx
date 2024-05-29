import { Dispatch, SetStateAction } from "react";

import Conversation from "../../objects/conversation";
import Message from "../../objects/message";
import ConversationHeader from "./conversation-header";
import MessageList from "./message-list";
import WriteMessageBox from "./write-message-box";

export default function MessageBox({ messages, conversation, setDeleteWindowVisibility, setDeleteMessage, messageListDiv }: { messages: Message[]; conversation: Conversation, setDeleteWindowVisibility: () => void, setDeleteMessage: Dispatch<SetStateAction<Message>>, messageListDiv: React.RefObject<HTMLDivElement> }) {
    return ( 
        messages.length > 0 || conversation.conversationId !== 0 ?
    <div className="w-full h-full flex flex-col ml-3" id="message-box">
        <ConversationHeader conversation={conversation} />
        <MessageList messages={messages} conversation={conversation} setDeleteWindowVisibility={setDeleteWindowVisibility} setDeleteMessage={setDeleteMessage} messageListDiv={messageListDiv}/>
        <WriteMessageBox conversation={conversation}/>
    </div>
    : <div>
        <div className="hidden" id='message-list'></div>
    </div>
    )
}