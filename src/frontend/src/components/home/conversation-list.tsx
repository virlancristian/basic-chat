import React from "react";
import { ConversationInbox } from "../../objects/conversation-inbox";

export default function ConversationList({ inbox, username }: { inbox: ConversationInbox[]; username: string }) {
    return <div className="conversation-list">
        {
            inbox.map((conversation: ConversationInbox) => (
                <div className="conversation" key={conversation.conversationId}>
                    <p id='recipient'>{conversation.firstParticipant !== username ? conversation.firstParticipant : conversation.secondParticipant}</p>
                    <p id='message'>{
                        conversation.message !== undefined
                        ? (
                            conversation.contentType == 1 
                            ? conversation.message
                            : `Image`
                        ) : <></>
                    }</p>
                </div>
            ))
        }
    </div>
}