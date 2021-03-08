import React from 'react';
import {Header} from 'antd/es/layout/layout';
import MyMenu from "./Menu/Menu";

export const HeaderContainer = () => {

    return (
        <Header style={{position: 'fixed', zIndex: 1, width: '100%', backgroundColor: '#DFFF93', height: '60px'}}>
            <MyMenu/>
            <h2 style={{display: 'inline', paddingLeft: '40%'}}>Piris Bank</h2>
        </Header>
    )
}