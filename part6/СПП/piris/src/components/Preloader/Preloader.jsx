import React from "react";
import preloaderGif from '../../resources/preloader.gif';
import s from "./Preloader.module.css";

const Preloader = () => {
    return <div><img src={preloaderGif} className={s.preloader} alt="application is loading..."/></div>
};

export default Preloader;
