import Conversation from "../../objects/conversation";
import Message from "../../objects/message";
import ConversationHeader from "./conversation-header";
import MessageList from "./message-lis";
import WriteMessageBox from "./write-message-box";

export default function MessageBox({ messages, conversation }: { messages: Message[]; conversation: Conversation }) {
    return <div className="message-box">
        <ConversationHeader conversation={conversation} />
        <MessageList messages={messages} />
        <WriteMessageBox conversation={conversation}/>
    </div>
}