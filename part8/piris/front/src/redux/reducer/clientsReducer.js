import api from "../../api/api";
import {stopSubmit} from "redux-form";
import {setErrorAction} from "./errorReducer";

const initialState = {
    clients: [
    ],
    client: null,
    isFetching: false
};

const CREATE_CLIENT = "clients/CREATE_CLIENT";
const SET_CLIENTS = "clients/GET_CLIENTS";
const TOGGLE_IS_FETCHING = "profile/TOGGLE_IS_FETCHING";


function setClients(state, clients) {
    return {
        ...state,
        clients: [...clients]
    };
}

function toggleIsFetching(state, isFetching) {
    return {
        ...state,
        isFetching
    }
}

const clientsReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_CLIENTS:
            return setClients(state, action.clients);
        case TOGGLE_IS_FETCHING:
            return toggleIsFetching(state, action.isFetching)
        default:
            return state;
    }
};

export function setClientsAction(post) {
    return {
        type: SET_CLIENTS,
        newPost: post
    }
}

export function toggleIsFetchingAction(isFetching) {
    return {
        type: TOGGLE_IS_FETCHING,
        isFetching
    }
}

export function getClients() {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const clients = await api.getClients();
        dispatch(toggleIsFetchingAction(false));
        dispatch(setClientsAction(clients));
    };
}

export function deleteClients(clientsIds) {
    return async (dispatch) => {
        for (const clientId of clientsIds) {
            const data = await api.deleteClient(clientId);
            if (data.resultCode !== 0){
                dispatch(stopSubmit('clientData', {_error: data.messages.length > 0 ? data.messages[0] : "Some error occurred"}));
                return Promise.reject(data.messages[0]);
            }
        }
        dispatch(getClients());
    }
}

export default clientsReducer;
