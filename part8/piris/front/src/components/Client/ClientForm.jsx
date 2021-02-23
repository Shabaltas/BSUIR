import React from "react";
import {Form, reduxForm} from "redux-form";
import Client from "./Client";

const ClientForm = reduxForm({
    // a unique name for the form
    form: 'clientData'
})((props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            <Client {...props.clientInfo}/>
            <button type="submit">{props.clientId ? 'Update': 'Save'}</button>
        </Form>
    )
});

export default ClientForm;

