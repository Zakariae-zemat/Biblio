import React, {useContext, useState} from 'react';
import {isActiveLogin, isActiveRegister, isLoadingData} from "../../context/GlobalProvider.jsx";
import {useAtom} from "jotai";
import axios from "../../api/axios.js"
import useAuth from '../../hook/useAuth';
import { useNavigate } from 'react-router-dom';
import AuthContext from "../../context/AuthProvider"
const Login = (props) => {
    const { setAuth } = useContext(AuthContext);
    const navigation = useNavigate()
    const [isActiveLoged, setIsActiveLogin] = useAtom(isActiveLogin);
    const [isActiveRegisted, setIsActiveRegister] = useAtom(isActiveRegister);
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const [error,setError] = useState("");
    const [isLoadingDataa,setIsLoadingDataa] = useAtom(isLoadingData);
    const handleSubmit = async (event) => {
        setIsLoadingDataa(true)
        event.preventDefault();
        const user = {
            email : email,
            password : password
        }
        try {
            const response = await axios.post('/auth/login', user,{
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const token = response.data?.token;
            const role = response?.data?.role;
            const email = response?.data?.email;
            localStorage.setItem('token', token);
            setAuth({role,email,token,isAuthenticated:true});
            setIsLoadingDataa(false)
            setIsActiveLogin(false)
            if(role === "CLIENT") {
                navigation('/mostPopulaire')
            } else {
                if (role === "ADMIN") {
                    navigation('/admin')
                } else {
                    if (role === "BIBLIOTHECAIRE") {
                        navigation('/documents')
                    }
                }
            }
        } catch (error) {
            setIsLoadingDataa(false)
            if (error.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                setError(error.response.data.error);
            } else if (error.request) {
                // The request was made but no response was received
                // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                // http.ClientRequest in node.js
                console.log(error.request);
            } else {
                // Something happened in setting up the request that triggered an Error
                console.log('Error', error.message);
            }
            console.log(error.config);
        }
    };
    return(
        <div className="bg-white font-medium flex flex-col items-center justify-center gap-8  text-black bg-opacity-90  rounded-xl py-8 px-6">
            <h1 className="text-xl text-center ">Se Connecter</h1>
            <form className="flex flex-col gap-4 " onSubmit={handleSubmit}>
                {error && <p className="text-red-600 text-[12px] font-normal text-left">{error}</p>}
                <input className="pl-2 w-72 font-normal py-2 rounded text-md focus:outline-none" value={email}
                       type="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)}/>
                <input className="pl-2 font-normal py-2 rounded text-md focus:outline-none" value={password}
                       type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/>

                        <button type="submit" className="bg-[#564592] text-white py-2 px-6 rounded-md">Login</button>

            </form>
            <div>
                <p className="text-sm font-normal">Vous n'avez pas de compte ? <span
                    className="text-[#564592] cursor-pointer" onClick={() => {
                    setIsActiveRegister(true)
                    setIsActiveLogin(false)
                }
                }>S'inscrire</span></p>
            </div>
        </div>
    )
}

export default Login;