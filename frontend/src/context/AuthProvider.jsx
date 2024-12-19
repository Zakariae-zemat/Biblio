import { createContext, useState,useEffect } from "react";
import {useNavigate} from "react-router-dom";

const AuthContext = createContext({});
export const AuthProvider = ({ children }) => {
    const navigate = useNavigate()
    const [auth, setAuthState] = useState(() => {
        const storedAuth = localStorage.getItem('auth');
        return storedAuth ? JSON.parse(storedAuth) : {['role']: 'GUEST', ['email']: '', ['token']: '', ['isAuthenticated']: false};
    });
    const setAuth = (newAuth) => {
        setAuthState(newAuth);
        localStorage.setItem('auth', JSON.stringify(newAuth));
    };


    const logout = () => {
        setAuthState({['role']: 'GUEST', ['email']: '', ['token']: '', ['isAuthenticated']: false});
        localStorage.removeItem('auth');
        navigate("/")
    };
    return (
        <AuthContext.Provider value={{ auth, setAuth,logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;