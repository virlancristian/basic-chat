import { ChangeEvent } from "react";
import Conversation from "../objects/conversation";
import { MessageService } from "../services/MessageService";

export async function handleFileChange(event: ChangeEvent<HTMLInputElement>, conversation: Conversation) {
    const image = event.target.files?.[0];

    if(image !== undefined) {
        await MessageService.uploadImage(image, conversation);
    }
}