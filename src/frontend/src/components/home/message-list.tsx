import Message from "../../objects/message"

export default function MessageList({ messages }: { messages: Message[] }) {
    const username: string = window.localStorage.getItem('bchat-username') || ``;

    return <div className="message-list">
        {
            messages.map((message) => (
                message.receiver === username
                    ? <div className="other-recipient-message-box" key={message.messageId}>
                        <p className="other-recipient-username">{message.sender}</p>
                        <p className="other-recipient-message">{message.message}</p>
                    </div>
                    : <div className="user-recipient-message-box" key={message.messageId}>
                        <p className="user-recipient-username">You</p>
                        <p className="user-recipient-message">{message.message}</p>
                    </div>
            ))
        }
    </div>
}