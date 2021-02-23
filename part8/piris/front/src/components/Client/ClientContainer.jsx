import React, {useEffect, useState} from "react";
import {connect} from "react-redux";
import ClientForm from "./ClientForm";
import {withRouter} from "react-router-dom";
import {compose} from "redux";
import withFormReset from "../../hoc/withFormReset";
import {getClientInfo} from "../../redux/reducer/clientReducer";

const ClientContainer = (props) => {
   useEffect(() => {
        let clientId = props.match.params.clientId ;
        props.getClientInfo(clientId);
    }, [props.match.params.clientId]);
    return <ClientForm clientInfo={{...props.clientInfo}}/>
};
let mapStateToProps = (state) => {
    return {
        clientInfo: getClientInfo(state),
    }
};

export default compose(
    withRouter,
    withFormReset,
    connect(mapStateToProps, {
        getClientInfo
    }),
)(ClientContainer);
