import React from 'react';
import s from "./Header.module.css";
/*
import {NavLink} from "react-router-dom";
*/

const Header = (props) => {
    return (
        <header className={s.header}>
            <div className={s.loginBlock}>
                <span>Ok header</span>
{/*
                {props.isAuth ? <div>{props.login}<button onClick={props.logout}> Logout</button></div> : <NavLink to='/login'>Login</NavLink>}
*/}
            </div>
        </header>
    )
};

export default Header;
