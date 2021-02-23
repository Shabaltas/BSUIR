import React from 'react';
import s from "./Header.module.css";
import MyMenu from "../Menu/Menu";
/*
import {NavLink} from "react-router-dom";
*/

const Header = (props) => {
    return (
        <header className={s.header}>
            <div className={s.loginBlock}>
                <MyMenu/>
                    <h2 className={s.header_name}>Piris Bank</h2>
                {/*
                {props.isAuth ? <div>{props.login}<button onClick={props.logout}> Logout</button></div> : <NavLink to='/login'>Login</NavLink>}
*/}
            </div>
        </header>
    )
};

export default Header;
