import { NavigateFunction, useNavigate } from "react-router-dom";

import '../css/auth.css'

export default function Auth() {
    const navigate : NavigateFunction = useNavigate();

    return <div className="auth-wrapper">
        <div className="auth-links">
            <div className="create-account-link" onClick={() => navigate("/create_account", {})}>
                <p>Create account</p>
            </div>
            <div className="login-link" onClick={() => navigate("/login", {})}>
                <p>Log in</p>
            </div>
        </div>
    </div>;
}