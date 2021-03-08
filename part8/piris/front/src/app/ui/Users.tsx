import React, {useContext, useEffect} from 'react';
import {observer} from 'mobx-react-lite';
import {RootStoreContext} from 'app/RootStoreContext';
import {UserComponent} from 'app/ui/UserComponent';
import {useHistory} from 'react-router';
import {Routes} from 'app/constants/Routes';
import AllClients from "./ClientsList/AllClients";

export const Users = observer(() => {

    const {clientsStore} = useContext(RootStoreContext);

    useEffect(() => {
        clientsStore.fetchItems();
    }, [clientsStore])

    const history = useHistory();

    const handleEdit = (id: number) => {
        history.push(Routes.editUser(id));
    }
    const handleDelete = (id: number) => {
        clientsStore.delete(id).then(() => {
        });
    }

    return (
        <div className="site-layout-background" style={{padding: 24}}>
           {/* {clientsStore.items.map((item) => {
                return <UserComponent key={item.id} user={item} onDelete={handleDelete} onEdit={handleEdit}/>
            })}*/}
            <AllClients clients={clientsStore.items} onDelete={handleDelete} onEdit={handleEdit}/>
        </div>
    )
})