import Conversation from "../../objects/conversation";

export default function ConversationHeader({ conversation }: {conversation: Conversation}) {
    const username: string = window.localStorage.getItem('bchat-username') || ``;
    
    return <div className="h-12 bg-gray-800 bg-opacity-40 rounded-xl flex items-center">
        <h3 className="text-white font-semibold ml-3">{conversation.firstParticipant !== username
            ? conversation.firstParticipant
            : conversation.secondParticipant}</h3>
    </div>
}