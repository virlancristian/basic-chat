import { deleteMessage } from "../../events/delete-message"
import Message from "../../objects/message"

export default function DeleteMessageWindow({ visible, message, setDeleteWindowVisibility }: { visible: boolean, message: Message, setDeleteWindowVisibility: () => void }) {
    return visible
        ? <div className="delete-message-window-wrapper">
            <div className="delete-message-window">
                <h3>Delete message?</h3>
                <div className="message-to-be-deleted">
                    <p>{message.message}</p>
                </div>
                <div className="delete-actions">
                    <div className="cancel-option" onClick={setDeleteWindowVisibility}>Cancel</div>
                    <div className="delete-option" onClick={() => deleteMessage(message, setDeleteWindowVisibility)}>Delete</div>
                </div>
            </div>
        </div>
        : <></>
}