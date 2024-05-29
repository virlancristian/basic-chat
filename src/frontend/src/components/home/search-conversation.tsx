import { useInput } from "../../hooks/use-input";
import { ConversationInbox } from "../../objects/conversation-inbox";
import { checkForKey } from "../../events/key-press";
import { searchConversation } from "../../events/search-conversation";

export default function SearchConversation({ inbox, changeInbox }: { inbox: ConversationInbox[], changeInbox: (inbox: ConversationInbox[]) => void }) {
    const { input, changeInput } = useInput();

    return <input 
                type="text" 
                className="text-white bg-black bg-opacity-20 rounded-lg m-3 p-1 w-64 outline-none border-none" 
                name='recipient' 
                placeholder='Search conversation' 
                onChange={changeInput} 
                onKeyDown={(event) => checkForKey('Enter', event, searchConversation, {input, inbox, changeInbox})}
                onKeyUp={(event) => checkForKey('Backspace', event, searchConversation, {input, inbox, changeInbox})}/>
}