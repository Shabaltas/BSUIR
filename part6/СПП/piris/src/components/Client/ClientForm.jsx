import React, {useEffect, useState} from "react";
import {connect} from "react-redux";
import Client from "./Client";
import {withRouter} from "react-router-dom";
import {compose} from "redux";

const ClientContainer = (props) => {
   useEffect(() => {
        let userId = props.match.params.clientId ;
        props.getClientInfo(userId);
    }, [props.match.params.clientId]);
    return <Client {...props}/>
};
let mapStateToProps = (state) => {
    return {
        //clientInfo: getClientInfo(state),
    }
};

export default compose(
    withRouter,
    connect(mapStateToProps, {
      //  getClientInfo
    }),
)(ClientContainer);
