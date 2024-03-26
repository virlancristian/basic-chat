import { KeyboardEvent } from "react";

export function checkForKey(mappedKey: string, event: KeyboardEvent<HTMLElement>, action: any, args: any) {
    console.log(event.key);
    if(event.key === mappedKey) {
        action(...Object.values(args));
    }
}