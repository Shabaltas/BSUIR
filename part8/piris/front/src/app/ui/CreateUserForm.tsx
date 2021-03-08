import React, {useContext} from 'react';
import {RootStoreContext} from 'app/RootStoreContext';
import {User} from 'app/entity/User';
import {UserForm} from 'app/ui/form/UserForm';
import {useHistory} from 'react-router';
import {Routes} from 'app/constants/Routes';


export const CreateUSerForm = () =>{
    const {clientsStore} = useContext(RootStoreContext);
    const history = useHistory();

    const onSave = (user: User) => {
        clientsStore.createItem(user).then(() => {
            history.push(Routes.clients);
        });
    }

    const onCancel = () => {
        history.push(Routes.clients);
    }

    return (
        <UserForm onSave={onSave} onCancel={onCancel}/>
    )
}