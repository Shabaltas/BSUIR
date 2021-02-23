import {getClients} from "./clientsReducer";

const initialState = {
    initialized: false,
    disabilities: [],
    marital_statuses: [],
    citizenship: [],
    cities: []
};

const INITIALIZED_SUCCESSFULLY = "app/INITIALIZED_SUCCESSFULLY";

export default (state = initialState, action) => {
    switch (action.type) {
        case INITIALIZED_SUCCESSFULLY:
            return {
                ...state,
                initialized: true
            };
        default:
            return state;
    }
};

export function onInitializedAction() {
    return {
        type: INITIALIZED_SUCCESSFULLY
    }
}

export function initializeApp() {
    return async dispatch => {
        try {
            await dispatch(getClients());

            dispatch(onInitializedAction());
        } catch {}
    }
}