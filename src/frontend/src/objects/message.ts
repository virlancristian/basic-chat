export default interface Message {
    messageId?: number,
    conversationId: number,
    contentType: number,
    message?: string,
    url?: string,
    receiver: string,
    sender: string,
    date: string,
    hour: string
}