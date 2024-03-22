import Conversation from "../../objects/conversation";

export default function ConversationHeader({ conversation }: {conversation: Conversation}) {
    const username: string = window.localStorage.getItem('bchat-username') || ``;
    
    return <div className="conversation-header">
        <h3>{conversation.firstParticipant !== username
            ? conversation.firstParticipant
            : conversation.secondParticipant}</h3>
    </div>
}