import React, {useContext} from 'react';
import {RouteComponentProps} from 'react-router';
import {UserForm} from 'app/ui/form/UserForm';
import {RootStoreContext} from 'app/RootStoreContext';
import {User} from 'app/entity/User';
import {Routes} from "../constants/Routes";


interface MatchId {
    id?: string
}

export const EditUserComponent = (props: RouteComponentProps<MatchId>) => {
    const id = props.match.params.id;
    const {clientsStore} = useContext(RootStoreContext);

    const onSave = (user: User) => {
        clientsStore.updateItem(user.id, user).then(() => {
            props.history.goBack();
        });
    }

    const onOpenDeposit = (user: User) => {
        props.history.push(Routes.createDeposit(user.id));
    }

    const onCancel = () => {
        props.history.goBack();
    }

    const user = clientsStore.items.find(user => Number(user.id) === Number(id));
    return (
        <UserForm user={user}
                  onSave={onSave} onCancel={onCancel}
                  onOpenDeposit={onOpenDeposit}/>
    )
}