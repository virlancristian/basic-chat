import { ChangeEvent, KeyboardEvent, useState } from "react";

export const useInput = () => {
    const [input, setInput] = useState<string>('');
    
    const changeInput = (event: ChangeEvent<HTMLInputElement> | KeyboardEvent<HTMLDivElement>) => {
        if('key' in event) {
            const keyboardEvent = event as KeyboardEvent<HTMLDivElement>;

            setInput(keyboardEvent.currentTarget.textContent || ``);
        } else {
            const changeEvent = event as ChangeEvent<HTMLInputElement>;

            setInput(changeEvent.currentTarget.value);
        }
    };

    return {
        input, 
        changeInput 
    };
}