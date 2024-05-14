import { Dispatch, SetStateAction } from "react";
import { ConversationInbox } from "../../objects/conversation-inbox";
import Conversation from "../../objects/conversation";

export default function ConversationList({ inbox, username, setConversation }: { inbox: ConversationInbox[]; username: string; setConversation: Dispatch<SetStateAction<Conversation>> }) {
    return <div className="conversation-list">
        {
            inbox.map((conversation: ConversationInbox) => (
                <div className="conversation" key={conversation.conversationId} onClick={() => {
                    setConversation(conversation);
                }}>
                    <p id='recipient'>{conversation.firstParticipant !== username ? conversation.firstParticipant : conversation.secondParticipant}</p>
                    <p id='message'>{
                        (
                            conversation.receiver !== username
                            ? `You: `
                            : `${conversation.firstParticipant !== username ? conversation.firstParticipant : conversation.secondParticipant}: `
                        )
                         + (conversation.message !== undefined || conversation.url !== undefined
                        ? (
                            conversation.contentType === 1 
                            ? conversation.message
                            : `Image`
                        ) : <></>)
                    }</p>
                </div>
            ))
        }
    </div>
}