import { Dispatch, SetStateAction } from "react";

import Conversation from "../../objects/conversation";
import Message from "../../objects/message";
import ConversationHeader from "./conversation-header";
import MessageList from "./message-list";
import WriteMessageBox from "./write-message-box";

export default function MessageBox({ messages, conversation, setDeleteWindowVisibility, setDeleteMessage }: { messages: Message[]; conversation: Conversation, setDeleteWindowVisibility: () => void, setDeleteMessage: Dispatch<SetStateAction<Message>> }) {
    return <div className="message-box">
        <ConversationHeader conversation={conversation} />
        <MessageList messages={messages} setDeleteWindowVisibility={setDeleteWindowVisibility} setDeleteMessage={setDeleteMessage}/>
        <WriteMessageBox conversation={conversation}/>
    </div>
}