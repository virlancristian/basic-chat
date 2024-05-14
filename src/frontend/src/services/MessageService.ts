import { API_URL } from "../cache/MiscConstants";
import ImageUploadResponse from "../objects/ImageUploadResponse";
import Conversation from "../objects/conversation";
import { getDate, getHour } from "../util/time";

async function uploadImage(file: File, conversation: Conversation) {
    const formData: FormData = new FormData();
    formData.append('image', file);

    const response: Response = await fetch(`${API_URL}/api/conversation/${conversation.conversationId}/image/upload`, {
        method: 'POST',
        body: formData
    });

    if(response.status === 200) {
        const data: ImageUploadResponse = await response.json();
        const username: string = localStorage.getItem(`bchat-username`) || '';

        const body: any = {
            message: `${API_URL}/api/conversation/${conversation.conversationId}/image/${data.fileName}`,
            imageNumber: data.imageId,
            sender: username,
            receiver: conversation.firstParticipant !== username ? conversation.firstParticipant : conversation.secondParticipant,
            contentType: 2,
            date: getDate(),
            hour: getHour()
        };

        const messageAddResponse: Response = await fetch(`${API_URL}/api/conversation/${conversation.conversationId}/message/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });

        console.log(messageAddResponse.status);
    }
} 

export const MessageService = {
    uploadImage
}