export default interface Message {
    messageId?: number,
    conversationId: number,
    contentType: number,
    message?: string,
    updatedMessage?: string,
    url?: string,
    imageId?: number,
    receiver: string,
    sender: string,
    date: string,
    hour: string
}