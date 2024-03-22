import { MouseEventHandler } from "react";

export function logout(): MouseEventHandler<HTMLDivElement> {
    window.localStorage.removeItem('bchat-username');
    window.location.reload();

    return () => {}
}