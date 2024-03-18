import { KeyboardEvent } from "react";

export function checkForKey(mappedKey: string, event: KeyboardEvent<HTMLInputElement>, action: any, args: any) {
    if(event.key === mappedKey) {
        action(...Object.values(args));
    }
}