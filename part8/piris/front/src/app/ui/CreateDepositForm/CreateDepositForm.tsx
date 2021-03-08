import React, {useContext, useEffect} from 'react';
import {RootStoreContext} from 'app/RootStoreContext';
import {RouteComponentProps, useHistory} from 'react-router';
import {Routes} from 'app/constants/Routes';
import {DepositContract} from "../../entity/DepositContract";
import {DepositForm} from "./DepositForm";
import {observer} from "mobx-react-lite";

interface CreateDepositFormProps {
    clientId?: string
}

export const CreateDepositForm = observer((props: RouteComponentProps<CreateDepositFormProps>) => {
    const {depositContractsStore, clientsStore} = useContext(RootStoreContext);
    const history = useHistory();

    const onSave = (depositContract: DepositContract) => {
        depositContractsStore.createItem(depositContract).then(() => {
            history.push(Routes.clients);
        });
    }
    const id = props.match.params.clientId;
    const user = clientsStore.items.find(user => Number(user.id) === Number(id));

    return (
        <DepositForm onSave={onSave} user={user}/>
    )
})