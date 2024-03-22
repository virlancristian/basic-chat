import { KeyboardEvent } from "react";

export function checkForKey(mappedKey: string, event: KeyboardEvent<HTMLElement>, action: any, args: any) {
    if(event.key === mappedKey) {
        action(...Object.values(args));
    }
}