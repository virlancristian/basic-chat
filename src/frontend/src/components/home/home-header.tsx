import { logout } from '../../events/logout';

export default function HomeHeader() {
    return <div className='home-header'>
        <h2>Basic Chat v0.1.0b</h2>
        <div className='log-out-button' onClick={logout}>Log out</div>
    </div>
}