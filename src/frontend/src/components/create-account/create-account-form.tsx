import { useInput } from '../../hooks/use-input';
import { createAccount } from '../../events/create-account';
import { checkForKey } from '../../events/key-press';

export default function CreateAccountForm() {
    const { input: username, changeInput: changeUsername } = useInput();
    const { input: password, changeInput: changePassword } = useInput();

    return <div className="create-account-form">
        <div className="username">
            <p>Username</p>
            <input type="text" className="username-field" name="username" value={username} onChange={changeUsername}/>
        </div>
        <div className="password">
            <p>Password</p>
            <input type="password" className="password-field" name="password" value={password} onChange={changePassword} onKeyUp={(event) => checkForKey('Enter', event, createAccount, { account: {username, password}})}/>
        </div>
        <div className="create-account-button" onClick={() => createAccount({username, password})}>
            Create account
        </div>
    </div>
}