import { ConversationInbox } from "../objects/conversation-inbox";

export function searchConversation(recipient: string, inbox: ConversationInbox[], changeInbox: (inbox: ConversationInbox[]) => void) {
    if(recipient === '') {
        changeInbox(inbox);

        return;
    }

    for(const conversation of inbox) {
        if(conversation.firstParticipant === recipient || conversation.secondParticipant === recipient) {
            changeInbox([conversation]);

            return;
        }
    }
}