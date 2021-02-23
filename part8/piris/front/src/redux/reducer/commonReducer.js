import api from "../../api/api";

const initialState = {
    disabilities: [],
    marital_statuses: [],
    citizenship: [],
    cities: [],
    isFetching: false
};

const SET_DISABILITIES = "common/GET_DISABILITIES";
const SET_CITIES = "common/GET_CITIES";
const SET_CITIZENSHIP = "common/GET_CITIZENSHIP";
const SET_MARITAL_STATUSES = "common/GET_MARITAL_STATUSES";
const TOGGLE_IS_FETCHING = "common/TOGGLE_IS_FETCHING";


function toggleIsFetching(state, isFetching) {
    return {
        ...state,
        isFetching
    }
}

function setCities(state, cities) {
    return {
        ...state,
        cities: [...cities]
    }
}

function setCitizenship(state, citizenship) {
    return {
        ...state,
        citizenship: [...citizenship]
    }
}

function setDisabilities(state, disabilities) {
    return {
        ...state,
        disabilities: [...disabilities]
    }
}

function setMaritalStatuses(state, maritalStatuses) {
    return {
        ...state,
        marital_statuses: [...maritalStatuses]
    }
}

export default (state = initialState, action) => {
    switch (action.type) {
        case SET_CITIES:
            return setCities(state, action.cities);
        case SET_DISABILITIES:
            return setDisabilities(state, action.disabilities);
        case SET_CITIZENSHIP:
            return setCitizenship(state, action.citizenship);
        case SET_MARITAL_STATUSES:
            return setMaritalStatuses(state, action.marital_statuses);
        case TOGGLE_IS_FETCHING:
            return toggleIsFetching(state, action.isFetching);
        default:
            return state;
    }
};


export function setCitiesAction(cities) {
    return {
        type: SET_CITIES,
        cities
    }
}

export function setCitizenshipAction(citizenship) {
    return {
        type: SET_CITIZENSHIP,
        citizenship
    }
}

export function setDisabilitiesAction(disabilities) {
    return {
        type: SET_DISABILITIES,
        disabilities
    }
}

export function setMaritalStatusesAction(marital_statuses) {
    return {
        type: SET_CITIES,
        marital_statuses
    }
}

export function toggleIsFetchingAction(isFetching) {
    return {
        type: TOGGLE_IS_FETCHING,
        isFetching
    }
}

export function getDisabilities() {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const disabilities = await api.getDisabilities();
        dispatch(toggleIsFetchingAction(false));
        dispatch(setDisabilitiesAction(toggleIsFetchingAction));
    };
}

export function getCitizenship() {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const citizenship = await api.getCitizenship();
        dispatch(toggleIsFetchingAction(false));
        dispatch(setCitizenshipAction(citizenship));
    };
}

export function getCities() {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const cities = await api.getCities();
        dispatch(toggleIsFetchingAction(false));
        dispatch(setCitiesAction(cities));
    };
}

export function getMaritalStatuses() {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const maritalStatuses = await api.getMaritalStatuses();
        dispatch(toggleIsFetchingAction(false));
        dispatch(setMaritalStatusesAction(maritalStatuses));
    };
}