import Conversation from "./conversation";

export interface ConversationInbox extends Conversation {
    contentType: number
    message?: string
    url?: string
    date: string,
    hour: string,
    receiver: string
}

export function compare(a: ConversationInbox, b: ConversationInbox): number {
    return (-1)*a.date.concat(' ' + a.hour).localeCompare(b.date.concat(' ' + b.hour));
}