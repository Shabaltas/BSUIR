const initialState = { error: null};

const SET_ERROR = "error/SET_ERROR";
const CLEAR_ERROR = "error/CLEAR_ERROR";

function setError(state, error) {
    return {
        ...state,
        error
    }
}

export default (state = initialState, action) => {
    switch (action.type) {
        case SET_ERROR:
            return setError(state, action.error);
        case CLEAR_ERROR:
            return setError(state, null);
        default:
            return state;
    }
}

export function setErrorAction(error) {
    return {
        type: SET_ERROR,
        error
    }
}

export function clearErrorAction() {
    return {
        type: CLEAR_ERROR
    }
}