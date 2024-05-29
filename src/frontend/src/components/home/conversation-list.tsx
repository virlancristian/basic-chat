import { Dispatch, SetStateAction } from "react";
import { ConversationInbox } from "../../objects/conversation-inbox";
import Conversation from "../../objects/conversation";

export default function ConversationList({ inbox, username, setConversation }: { inbox: ConversationInbox[]; username: string; setConversation: Dispatch<SetStateAction<Conversation>> }) {
    return <div className="grid grid-cols-1">
        {
            inbox.length > 0 && inbox[0].conversationId !== 0 ? inbox.map((conversation: ConversationInbox) => (
                <div className="hover:bg-gray-800 border-b-2 my-2" key={conversation.conversationId} onClick={() => {
                    setConversation(conversation);
                }}>
                    <p className='text-white text-lg ml-3' id='recipient'>{conversation.firstParticipant !== username ? conversation.firstParticipant : conversation.secondParticipant}</p>
                    <p className='text-gray-500 ml-3 overflow-hidden' id='message'>{
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
            )) : <></>
        }
    </div>
}