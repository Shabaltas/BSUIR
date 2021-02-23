import React, {useEffect, useState} from "react";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";
import {compose} from "redux";
import withFormReset from "../../hoc/withFormReset";
import {getClients} from "../../redux/reducer/clientsReducer";
import AllClients from "./AllClients";

const AllClientsContainer = (props) => {
    return <AllClients rows={props.clients}/>
};

let mapStateToProps = (state) => {
    return {
        clients: getClients(state),
    }
};

export default compose(
    withRouter,
    withFormReset,
    connect(mapStateToProps, {
        getClients
    }),
)(AllClientsContainer);
