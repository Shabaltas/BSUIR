import React from "react";
import Alert from "@material-ui/lab/Alert";
import AlertTitle from "@material-ui/lab/AlertTitle";

export default (props) => {
    return (
        <Alert severity="error">
            <AlertTitle>Error</AlertTitle>
            This is an error alert — <strong>{props.reason}</strong>
        </Alert>
    )
};