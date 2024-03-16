import { MouseEventHandler } from "react";

export function openCreateConvoWindow(): MouseEventHandler<HTMLElement> {
    return () => {
        const createConversationWindow = document.querySelector('.create-conversation-form-wrapper');

        createConversationWindow?.setAttribute('active', 'true');
    }
}