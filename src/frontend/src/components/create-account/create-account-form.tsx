import { useInput } from '../../hooks/use-input';
import { createAccount } from '../../events/create-account';
import { checkForKey } from '../../events/key-press';

export default function CreateAccountForm() {
    const { input: username, changeInput: changeUsername } = useInput();
    const { input: password, changeInput: changePassword } = useInput();

    return <div className="bg-gray-700 bg-opacity-70 rounded-xl w-3/12 h-2/6 p-10">
        <div className="username">
            <p className='font-semibold text-white text-xl'>Username</p>
            <input type="text" className="h-8 w-72 mt-5 outline-1 ring-green-500 rounded" name="username" value={username} onChange={changeUsername}/>
        </div>
        <div className="password">
            <p className='font-semibold text-white text-xl mt-5'>Password</p>
            <input type="password" className="h-8 w-72 mt-5 rounded" name="password" value={password} onChange={changePassword} onKeyUp={(event) => checkForKey('Enter', event, createAccount, { account: {username, password}})}/>
        </div>
        <button className="bg-green-700 hover:bg-green-800 font-bold mt-5 w-32 h-12 rounded-xl text-white" onClick={() => createAccount({username, password})}>
            Create account
        </button>
    </div>
}