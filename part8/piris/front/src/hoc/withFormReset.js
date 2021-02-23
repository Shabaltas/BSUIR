import React from "react";
import {connect} from "react-redux";
import {reset} from "redux-form";

let mapDispatchToProps = (dispatch) => ({resetForm: formName => dispatch(reset(formName))});

const withFormReset = Component => connect(undefined, mapDispatchToProps)(Component);

export default withFormReset;