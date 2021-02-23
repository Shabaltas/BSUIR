import React from "react";
import Preloader from "../components/Preloader/Preloader";

const withSuspense = (Component) => (props) => (
    <React.Suspense fallback={<Preloader/>}>
        <Component {...props}/>
    </React.Suspense>
);

export default withSuspense;
