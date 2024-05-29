import { logout } from '../../events/logout';

export default function HomeHeader() {
    return <div className='bg-green-900 mt-10 w-10/12 h-16 flex flex-row justify-between rounded'>
        <h2 className='font-bold text-4xl text-white flex items-center ml-5'>Basic Chat v0.3.0b</h2>
        <div className='flex items-center mr-5 mt-5 p-3 font-semibold text-white text-lg cursor-pointer rounded hover:bg-green-700 h-1/2' onClick={logout}>Log out</div>
    </div>
}