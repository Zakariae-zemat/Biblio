import React from 'react';
import Home from "../pages/Home.jsx";
import Login from "./auth/Login.jsx";
import Register from "./auth/Register.jsx";
import {useAtom} from "jotai";
import {isActiveLogin, isActiveRegister} from "../context/GlobalProvider.jsx";

const Acceuil = (props) => {
    const [isActiveLoged, setIsActiveLogin] = useAtom(isActiveLogin);
    const [isActiveRegisted, setIsActiveRegister] = useAtom(isActiveRegister);
    return (
        <div className="h-[80vh]">
            <Home/>
            <div className="absolute z-20 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
                {isActiveLoged && <Login/>}
                {isActiveRegisted && <Register/>}
            </div>
        </div>
    )
}

export default Acceuil;