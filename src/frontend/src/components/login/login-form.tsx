import { useInput } from '../../hooks/use-input';
import { checkForKey } from '../../events/key-press';
import { login } from '../../events/login';

export default function LoginForm() {
    const { input: username, changeInput: changeUsername } = useInput();
    const { input: password, changeInput: changePassword } = useInput();

    return <div className="login-form">
        <div className="username">
            <p>Username</p>
            <input type='text' className='username-field' name='username' value={username} onChange={changeUsername}></input>
        </div>
        <div className="password">
            <p>Password</p>
            <input type="password" className="password-field" name='password' value={password} onChange={changePassword} onKeyDown={(event) => checkForKey('Enter', event, login, { account: {username, password}})}/>
        </div>
        <div className="login-button" onClick={() => login({username, password})}>Log in</div>
    </div>
}