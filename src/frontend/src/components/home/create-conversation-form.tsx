import { useInput } from "../../hooks/use-input";
import { createConversation } from "../../events/create-conversation";
import { checkForKey } from "../../events/key-press";
import { Dispatch, SetStateAction } from "react";
import Conversation from "../../objects/conversation";

export default function CreateConversationForm({username, visible, setVisibility, setConversation}: 
                                                { username: string; 
                                                visible: boolean; 
                                                setVisibility: () => void; 
                                                setConversation: Dispatch<SetStateAction<Conversation>> }) {
    const { input, changeInput } = useInput();

    return visible ?
            <div className="absolute w-screen h-screen bg-black bg-opacity-70 flex items-center justify-center">
                <div className="bg-gray-800 bg-opacity-60 w-4/12 h-48 rounded-xl flex flex-col">
                    <div className="text-white font-bold text-lg m-3 rounded bg-red-600 bg-opacity-50 hover:bg-red-700 w-8 text-center cursor-pointer" onClick={setVisibility}>X</div>
                    <h3 className="text-white font-bold text-xl ml-5">Start a conversation</h3>
                    <input type="text" 
                            className="text-white bg-black bg-opacity-20 rounded-lg m-3 p-1 w-74 outline-none border-none" 
                            placeholder="Search username" 
                            onChange={changeInput} 
                            onKeyUp={(event) => checkForKey('Enter', event, createConversation, {username, input, setVisibility, setConversation})}/>
                    <button className="text-white font-bold bg-green-800 hover:bg-green-900 rounded w-44 h-8 ml-5" 
                            onClick={() => createConversation(username, input, setVisibility, setConversation)}>
                        Start conversation
                    </button>
                </div>
            </div>
            : <></>
}