import { ChangeEvent, useState } from "react";

export const useRecipient = () => {
    const [recipient, setRecipient] = useState<string>('');
    
    const changeRecipient = (event: ChangeEvent<HTMLInputElement>) => {
        const input: string = event.target.value;

        setRecipient(input);
        console.log(input);
    };

    return {
        recipient, 
        changeRecipient 
    };
}