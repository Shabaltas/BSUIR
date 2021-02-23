import React, {useEffect, useState} from "react";
import {connect} from "react-redux";
import Profile from "./Profile";
import {withRouter} from "react-router-dom";
import {compose} from "redux";
import {getAuthorizedId, getIsAuth, getPosts, getProfile, getProfileStatus} from "../../selectors/stateSelector";
import {getProfileInfo, savePhoto, updateProfile, updateProfileStatus} from "../../redux/reducer/profileReducer";

const ProfileContainer = (props) => {
   useEffect(() => {
        let userId = props.match.params.userId || props.authorizedUserId;
        if (userId)
            props.getProfileInfo(userId);
        else
            props.history.push('/login');
    }, [props.match.params.userId]);
    return <Profile {...props} authedUser={(props.match.params.userId || props.authorizedUserId) == props.authorizedUserId}/>
};
let mapStateToProps = (state) => {
    return {
        profile: getProfile(state),
        posts: getPosts(state),
        status: getProfileStatus(state),
        authorizedUserId: getAuthorizedId(state),
        isAuth: getIsAuth(state)
    }
};

export default compose(
    withRouter,
    connect(mapStateToProps, {
        getProfileInfo, updateProfileStatus, savePhoto, updateProfile
    }),
)(ProfileContainer);
