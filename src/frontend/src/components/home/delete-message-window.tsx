import { deleteMessage } from "../../events/delete-message"
import Message from "../../objects/message"

import '../../css/delete-message-window.css';

export default function DeleteMessageWindow({ visible, message, setDeleteWindowVisibility }: { visible: boolean, message: Message, setDeleteWindowVisibility: () => void }) {
    return visible
        ? <div className="fixed w-screen h-screen bg-black bg-opacity-70 flex justify-center items-center">
            <div className="bg-gray-800 bg-opacity-60 w-4/12 h-48 rounded-xl">
                <h3 className="text-white font-bold text-3xl m-5">Delete message?</h3>
                <div className="break-words rounded m-3 bg-green-800 w-48 overflow-y-auto " id='message-to-be-deleted-div'>
                    {
                        message.contentType === 1 ?
                        <p className="text-gray-300 m-3">{message.message}</p> :
                        <img src={`${message.url}?image_id=${message.imageNumber}`} id='image-message'/>
                    }
                </div>
                <div className="delete-actions">
                    <button className="w-14 h-8 font-bold rounded text-white bg-red-600 hover:bg-red-700 ml-3" onClick={() => deleteMessage(message, setDeleteWindowVisibility)}>Delete</button>
                    <button className="w-14 h-8 font-bold rounded text-white bg-gray-600 hover:bg-gray-700 ml-3" onClick={setDeleteWindowVisibility}>Cancel</button>
                </div>
            </div>
        </div>
        : <></>
}