import api from "../../api/api";
import {stopSubmit} from "redux-form";
import {getClients} from "./clientsReducer";

const initialState = {
    clientInfo: null,
    isFetching: false
};

const SET_CLIENT_INFO = "client/SET_CLIENT_INFO";
const TOGGLE_IS_FETCHING = "client/TOGGLE_IS_FETCHING";

function setClientInfo(state, clientInfo) {
    return {
        ...state,
        clientInfo
    };
}

function toggleIsFetching(state, isFetching) {
    return {
        ...state,
        isFetching
    }
}

const clientReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_CLIENT_INFO:
            return setClientInfo(state, action.clientInfo);
        case TOGGLE_IS_FETCHING:
            return toggleIsFetching(state, action.isFetching);
        default:
            return state;
    }
};

export function toggleIsFetchingAction(isFetching) {
    return {
        type: TOGGLE_IS_FETCHING,
        isFetching
    }
}

export function setClientInfoAction(clientInfo) {
    return {
        type: SET_CLIENT_INFO,
        clientInfo
    }
}

export function getClientInfo(clientId) {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const client = await api.getClient(clientId);
        dispatch(toggleIsFetchingAction(false));
        dispatch(setClientInfoAction(client));
    };
}

export function updateClientInfo(clientId, client) {
    return async (dispatch) => {
        const data = await api.updateClient(clientId, client);
        if (data.resultCode === 0)
            dispatch(getClients());
        else {
            dispatch(stopSubmit('clientData', {_error: data.messages.length > 0 ? data.messages[0] : "Some error occurred"}));
            return Promise.reject(data.messages[0]);
        }
    }
}

export function createClient(client) {
    return async (dispatch) => {
        const data = await api.createClient(client);
        if (data.resultCode === 0)
            dispatch(getClients());
        else {
            dispatch(stopSubmit('clientData', {_error: data.messages.length > 0 ? data.messages[0] : "Some error occurred"}));
            return Promise.reject(data.messages[0]);
        }
    }
}

export default clientReducer;
