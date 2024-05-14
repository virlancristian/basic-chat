export function scrollMessageList(): void {
    const messageListDiv: HTMLDivElement = document.querySelector('.message-list') || new HTMLDivElement;

    messageListDiv.scrollTop = messageListDiv.scrollHeight;
}