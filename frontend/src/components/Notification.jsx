import React, {useEffect, useState} from 'react';
import {Avatar, Dropdown, DropdownItem, DropdownMenu, DropdownTrigger} from "@nextui-org/react";
import {Divider} from "@nextui-org/divider";

import { IoMdNotifications } from "react-icons/io";
import useAuth from "../hook/useAuth.js";
import axios from "../api/axios.js";
import {useAtom} from "jotai";
import {isNotification} from "../context/GlobalProvider.jsx";

const Notification = (props) => {
    const [notifications, setNotifications] = useState([]);
    const [user,setUser] = useState({});
    const [isNotif, setIsNotif] = useAtom(isNotification);
    const {auth} = useAuth();
    const getUser = async () => {
        try {
            const response = await axios.get('/users/user?email=' + auth.email, {
                headers: {
                    'Authorization': 'Bearer ' + auth.token,
                }
            });
            const data = response.data.utilisateur
            setUser(data);
        }
        catch (error) {
            console.log(error)
        }
    }
    const getNotifications = async () => {
        try {
            const response = await axios.get('/notifications?recepteurId=' + user.id, {
                headers: {
                    'Authorization': 'Bearer ' + auth.token,
                }
            });
            setNotifications(response.data);
        }catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        getUser().then(r => r).catch(e => e);
    }, []);
    useEffect(() => {
        if(user.id) getNotifications().then(r => r).catch(e => e);

    }, [user]);
    return(
        <div className="relative flex items-center">
            <div>
                <IoMdNotifications className="text-[1.6rem] cursor-pointer " onClick={()=>{
                    setIsNotif(!isNotif)
                }} />
            </div>
            {isNotif ?
                <>
                    <div className="triangle absolute  top-10 -right-1"></div>
                    <div
                        className="min-w-72 max-h-96 overflow-scroll z-40 text-black bg-white rounded-md md:-right-[4px] -right-[12px]  top-12  absolute pb-4  pt-4 flex flex-col ">
                        <div>
                            {
                                notifications?.length > 0 ?
                                    notifications.map(
                                        (item, value) => {
                                            return (
                                                <div>
                                                    <div
                                                        className="flex pt-4 p-2 rounded  px-6 gap-3 cursor-pointer hover:bg-gray-300">
                                                        {
                                                            item.emeteur?.imageUrl ? <Avatar size="sm"  showFallback isBordered color="secondary" src={item.emeteur.imageUrl}/> :
                                                                <Avatar className=" " size="sm" showFallback isBordered  color="secondary" src='https://images.unsplash.com/broken'/>
                                                        }
                                                        <div className="flex flex-col items-center  ">
                                                            <p className="text-[10px] font-semibold ">{item?.emeteur?.prenom} {item?.emeteur?.nom}</p>
                                                            <p className="text-[10px]">{item.message}</p>
                                                            <p className="text-[10px] ">montant : {item.montant} Dh</p>
                                                        </div>
                                                    </div>
                                                    <Divider className="my-4"/>
                                                </div>

                                            )
                                        }
                                    ) :
                                    <div className="flex items-center justify-center text-sm font-normal"><p> Vous avez
                                        pas
                                        de notifications </p></div>
                            }
                        </div>
                    </div>
                </>
                : null
            }
        </div>
    )
}

export default Notification;