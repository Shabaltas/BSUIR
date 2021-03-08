import React from 'react';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import s from "./MyMenu.module.css";
import {NavLink} from "react-router-dom";
import {Routes} from "../../constants/Routes";

const MyMenu = (props) => {
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };
    return (
        <div className={s.menu}>
            <Button aria-controls="simple-menu" aria-haspopup="true" onClick={handleClick}>
                Menu
            </Button>
            <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
            >
                <NavLink to={Routes.clients}><MenuItem onClick={handleClose}>All Clients</MenuItem></NavLink>
                <NavLink to={Routes.createClient}><MenuItem onClick={handleClose}>Create</MenuItem></NavLink>
            </Menu>
        </div>
    )
};

export default MyMenu;
