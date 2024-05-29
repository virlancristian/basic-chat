import { useInput } from '../../hooks/use-input';
import { checkForKey } from '../../events/key-press';
import { login } from '../../events/login';

export default function LoginForm() {
    const { input: username, changeInput: changeUsername } = useInput();
    const { input: password, changeInput: changePassword } = useInput();

    return <div className="bg-gray-700 bg-opacity-70 rounded-xl w-3/12 h-2/6 p-10">
        <div className="username">
            <p className='font-semibold text-white text-xl'>Username</p>
            <input type='text' className='h-8 w-72 mt-5 outline-1 ring-green-500 rounded' name='username' value={username} onChange={changeUsername}></input>
        </div>
        <div className="password">
            <p className='font-semibold text-white text-xl mt-5'>Password</p>
            <input type="password" className="h-8 w-72 mt-5 rounded" name='password' value={password} onChange={changePassword} onKeyDown={(event) => checkForKey('Enter', event, login, { account: {username, password}})}/>
        </div>
        <div className='w-full flex justify-between'>
            <button className='bg-green-700 hover:bg-green-800 font-bold mt-5 w-24 h-12 rounded-xl text-white'
                    onClick={() => login({ username, password })}>Log in</button>
            <button className='font-bold text-green-700 hover:text-green-900 hover:bg-gray-600 mt-5 w-32 h-12 rounded-xl'
                    onClick={() => {
                        window.location.href='/create_account'
                    }}>Create account</button>
        </div>
    </div>
}