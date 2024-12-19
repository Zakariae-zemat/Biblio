import React from 'react';
import {isActiveLogin, isActiveRegister} from "../context/GlobalProvider.jsx";
import { useAtom } from 'jotai';
import users from '../assets/users.png';


const Header = () => {
    const [isActiveLoged, setIsActiveLogin] = useAtom(isActiveLogin);
    const [isActiveRegisted, setIsActiveRegister] = useAtom(isActiveRegister);
    return (
        <div className={`h-[100%] flex-col flex gap-y-14  items-center justify-center`} >
                <h1 className="font-bold shd md:text-4xl sm:text-3xl text-center text-2xl  w-2/3">
                    Découvrer l'univers des documents avec notre bibliothèque
                </h1>
                <div className="flex items-center justify-center gap-2">
                    <img src={users} alt="users" className="h-10"/>
                    <p className="text-sm font-medium ">
                        Rejoigner-nous +5400 use this app
                    </p>
                </div>
                <div className="flex gap-6">
                    <button className="bg-[#564592] py-3 font-semibold rounded-md px-7 " onClick={()=>{setIsActiveRegister(true)
                    }}>
                        S'inscrire
                    </button>
                    <button className="border border-[#564592]  py-3 rounded-md px-7" onClick={() => {
                        setIsActiveLogin(!isActiveLoged)
                    }}>
                        Se Connecter
                    </button>
                </div>

        </div>
    )
}

export default Header;