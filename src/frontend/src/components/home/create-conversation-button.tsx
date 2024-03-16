import React from "react";
import { openCreateConvoWindow } from "../../events/open-create-conversation-window";

interface Props {
  setVisibility: () => void;
}

export default function CreateConversationWindowOpen({ setVisibility }: Props) {
    return (
        <div className="create-conversation-window-open" onClick={setVisibility}>
            +
        </div>
    )
}
