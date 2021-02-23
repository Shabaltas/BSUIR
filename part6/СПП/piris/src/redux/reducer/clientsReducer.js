import api from "../../api/api";
import {stopSubmit} from "redux-form";
import {setErrorAction} from "./errorReducer";

const initialState = {
    posts: [
        {id: 1, msg: "How r u all", likes: 12},
        {id: 2, msg: "LOL", likes: 0},
        {id: 3, msg: "dead", likes: 2},
        {id: 4, msg: "...", likes: 1}
    ],
    profile: null,
    status: '',
    isFetching: false
};

const ADD_POST = "profile/ADD_POST";
const SET_PROFILE = "profile/SET_PROFILE";
const TOGGLE_IS_FETCHING = "profile/TOGGLE_IS_FETCHING";
const SET_PROFILE_STATUS = "profile/SET_PROFILE_STATUS";
const DELETE_POST = "profile/DELETE_POST";
const SAVE_PHOTO_SUCCESS = "profile/SAVE_PHOTO_SUCCESS";

function addPost(state, newPost) {
    return {
        ...state,
        posts: [...state.posts, {
            id: 5,
            msg: newPost,
            likes: 0
        }]
    };
}

function setProfile(state, profile, status) {
    return {
        ...state,
        profile,
        status
    };
}

function setStatus(state, status) {
    return {
        ...state,
        status
    };
}

function toggleIsFetching(state, isFetching) {
    return {
        ...state,
        isFetching
    }
}

function deletePost(state, postId) {
    return {
        ...state,
        posts: state.posts.filter(p => p.id !== postId)
    }
}

function updatePhotos(state, photos) {
    let profile = {...state.profile, photos};
    return {
        ...state,
        profile
    }
}

const clientReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_POST:
            return addPost(state, action.newPost);
        case SET_PROFILE:
            return setProfile(state, action.profile, action.status);
        case TOGGLE_IS_FETCHING:
            return toggleIsFetching(state, action.isFetching);
        case SET_PROFILE_STATUS:
            return setStatus(state, action.status);
        case DELETE_POST:
            return deletePost(state, action.postId);
        case SAVE_PHOTO_SUCCESS:
            return updatePhotos(state, action.photos);
        default:
            return state;
    }
};

export function deletePostAction(postId) {
    return {
        type: DELETE_POST,
        postId
    }
}

export function addPostAction(post) {
    return {
        type: ADD_POST,
        newPost: post
    }
}

export function toggleIsFetchingAction(isFetching) {
    return {
        type: TOGGLE_IS_FETCHING,
        isFetching
    }
}

export function setProfileAction(profile, status) {
    return {
        type: SET_PROFILE,
        profile,
        status
    }
}

export function updateStatusAction(newStatus) {
    return {
        type: SET_PROFILE_STATUS,
        status: newStatus
    }
}

export function updatePhotoAction(photos) {
    return {
        type: SAVE_PHOTO_SUCCESS,
        photos
    }
}

export function getProfileInfo(userId) {
    return async dispatch => {
        dispatch(toggleIsFetchingAction(true));
        const profile = await api.getProfileInfo(userId);
        const status = await api.getProfileStatus(userId);
        dispatch(toggleIsFetchingAction(false));
        dispatch(setProfileAction(profile, status));
    };
}

export function updateProfileStatus(newStatus) {
    return async dispatch => {
        const data = await api.updateProfileStatus(newStatus);
        if (data.resultCode === 0)
            dispatch(updateStatusAction(newStatus));
        else dispatch(setErrorAction(data.messages[0]))
    }
}

export function updateProfile(profile) {
    return async (dispatch, getState) => {
        const data = await api.updateProfile(profile);
        if (data.resultCode === 0)
            dispatch(getProfileInfo(getState().auth.userId));
        else {
            dispatch(stopSubmit('profileData', {_error: data.messages.length > 0 ? data.messages[0] : "Some error occurred"}));
            return Promise.reject(data.messages[0]);
        }
    }
}


export function savePhoto(photo) {
    return async dispatch => {
        const data = await api.savePhoto(photo);
        if (data.resultCode === 0)
            dispatch(updatePhotoAction(data.data.photos));
    }
}

export default clientReducer;
