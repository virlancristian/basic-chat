import { useState } from "react";
import { FormVisibility } from "../objects/form-visibility";

export const useFormVisbility = (initialVisibility: boolean): FormVisibility => {
    const [visible, setVisible] = useState<boolean>(initialVisibility);

    const setVisibility = () => {
        setVisible(prevVisibility => !prevVisibility);
    };

    return {
        visible,
        setVisibility
    };
};