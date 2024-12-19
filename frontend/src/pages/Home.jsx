import React from 'react';
import NavbarLayout from "../components/NavbarLayout.jsx";
import Header from "../components/Header.jsx";
import {isActiveLogin, isActiveRegister} from "../context/GlobalProvider.jsx";
import { useAtom } from 'jotai'
import Register from "../components/auth/Register.jsx";
import Login from "../components/auth/Login.jsx";
const Home = () => {
    return(
        <NavbarLayout  >
            <Header/>
        </NavbarLayout>
    );
};

export default Home;