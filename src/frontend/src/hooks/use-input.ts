import { ChangeEvent, useState } from "react";

export const useInput = () => {
    const [input, setInput] = useState<string>('');
    
    const changeInput = (event: ChangeEvent<HTMLInputElement>) => {
        const newInput: string = event.target.value;

        setInput(newInput);
    };

    return {
        input, 
        changeInput 
    };
}