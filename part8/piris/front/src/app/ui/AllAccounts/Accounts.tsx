import React, {useContext, useEffect} from 'react';
import {observer} from 'mobx-react-lite';
import {RouteComponentProps, useHistory} from 'react-router';
import {Routes} from 'app/constants/Routes';
import {RootStoreContext} from "../../RootStoreContext";
import AccountsList from "./AccountsList";
import {Button} from "antd";

export const Accounts = observer((props: RouteComponentProps) => {

    const {accountsStore, depositsStore} = useContext(RootStoreContext);

    useEffect(() => {
        accountsStore.fetchItems();
    }, [accountsStore]);

    const history = useHistory();

    const handleView = (id: number) => {
        history.push(Routes.viewAccount(id));
    };

    const handleCloseDay = () => {
        depositsStore.closeDay().then(() => {
            accountsStore.fetchItems();
        });
    };

    return (
        <div className="site-layout-background" style={{padding: 24}}>
            <AccountsList accounts={accountsStore.items} onView={handleView}/>
            <Button type="default" htmlType="button" onClick={handleCloseDay}>Close Day</Button>
        </div>
    )
})