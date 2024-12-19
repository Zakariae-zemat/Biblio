import React, {useState} from 'react';
import logo from '../assets/logo.png'
import useAuth from "../hook/useAuth.js";
import Notification from "./Notification.jsx";
import Profile from "./Profile.jsx";
import { MdLocalGroceryStore } from "react-icons/md";
import {useLocation, useNavigate} from "react-router-dom";
import Store from "./Store.jsx";
import {HiMenuAlt4} from "react-icons/hi";
import {AiOutlineClose} from "react-icons/ai";
const menuBiliothecaire = [
    {label: "Documents", path: "/documents"},
    {label: "Transactions", path: "/transactions"},
    {label: "Statistiques", path: "/statistiques"},
]



const Navbar = () => {
    const user = useAuth();
    const {auth}  = useAuth();
    let location = useLocation();
    const navigation = useNavigate();
    const [toggleMenu,setToggleMenu]=useState(false); //Hooks
    return(
        <div className="flex items-center justify-between py-6 " >
            <div className="flex items-center gap-2">
                <img src={logo} alt="logo" width={40} height={40} />
                <p className="text-sm">biblio</p>
            </div>
            {
                auth?.role === "BIBLIOTHECAIRE" && (
                    <div className="md:flex hidden gap-5">
                        {
                            menuBiliothecaire.map((item, index) => (
                                <button key={index} onClick={() => navigation(item.path)}
                                        className={`py-3 px-5 text-sm hover:font-bold ${location.pathname === item.path ? "underline  font-bold  " : ""}`}>
                                    {item.label}
                                </button>
                            ))
                        }
                    </div>
                )
            }

            <div className="flex items-center">
                {
                    user?.auth?.email ? (<div className="flex gap-5 items-center">
                        {
                            user?.auth?.role === "CLIENT" && (
                              <>
                                  <Store/>
                                  <Notification/>
                              </>
                            )
                        }
                        {
                            auth?.role === "BIBLIOTHECAIRE" && (
                                <div className="md:hidden flex gap-5">
                                    <button onClick={() => setToggleMenu(!toggleMenu)} className="text-sm">
                                        {
                                            toggleMenu ? <AiOutlineClose className="w-8 h-8"/> : <HiMenuAlt4 className="w-8 h-8"/>
                                        }
                                    </button>
                                    {
                                        toggleMenu && (
                                            <div className=" absolute z-40 font-bold  top-0 bg-[#564592]  right-0 p-3 w-full h-[30rem] rounded-b-3xl shadow-2xl md:hidden liste-none flex flex-col text-white animate-slide-in  gap-6">
                                                <div className="flex justify-end">
                                                    <AiOutlineClose onClick={() => setToggleMenu(!toggleMenu)} className="w-8  h-8"/>
                                                </div>
                                                <div className="flex items-center justify-center flex-col gap-8 text-md w-full h-full">
                                                    {
                                                        menuBiliothecaire.map((item, index) => (
                                                            <button key={index} onClick={() => {
                                                                navigation(item.path);
                                                                setToggleMenu(false);
                                                            }}
                                                                    className={`py-3 px-5  hover:font-bold ${location.pathname === item.path ? "underline  font-bold  " : ""}`}>
                                                                {item.label}
                                                            </button>
                                                        ))
                                                    }
                                                </div>
                                            </div>
                                        )
                                    }
                                </div>
                            )
                        }
                    <Profile/>
                    </div>) : (<button
                        className="py-3 px-5 font-light text-sm hover:font-bold  hover:bg-[#564592] border-[#564592] border rounded-md bg-none ">
                        Se Connecter
                    </button>)
                }
            </div>
        </div>

    );
}

export default Navbar;