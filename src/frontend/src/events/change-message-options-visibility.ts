import { Dispatch, SetStateAction } from "react";
import MessageOptions from "../components/home/message-options";

export function changeMessageOptionVisibility(currentId: number, messageId: number, setMessageId: Dispatch<SetStateAction<number>>) {
    setMessageId(currentId !== messageId ? messageId : 0);
}