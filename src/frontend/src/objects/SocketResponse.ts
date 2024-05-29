export default interface SocketResponse {
    updatedMessageID: number;
    updateType: string;
    newMessage?: string;
}