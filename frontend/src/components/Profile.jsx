import React, {useEffect, useState} from 'react';
import {Avatar} from "@nextui-org/react";
import useAuth from "../hook/useAuth.js";
import axios from "../api/axios.js";
import { MdEmail,MdSettings } from "react-icons/md";
import {useAtom} from "jotai";
import {isActiveProfile} from "../context/GlobalProvider.jsx";
import {Divider} from "@nextui-org/divider";
import { CgProfile } from "react-icons/cg";
import { TbLogout2 } from "react-icons/tb";
import {useNavigate} from "react-router-dom";
import EditProfile from "./EditProfile.jsx";
const Profile = () => {
    const {auth,logout} = useAuth();
    const navigate = useNavigate()
    const [user, setUser] = useState({});
    const [isActiveProfil, setIsActiveProfile] = useAtom(isActiveProfile);
    //get user
    const  getUser = async () => {
        try{
            const response = await axios.get('/users/user?email='+auth.email,{
                headers:{
                    'Authorization': 'Bearer ' + auth.token,
                }
            });
            setUser(response.data.utilisateur);
        }catch (error){
            console.log(error)
        }
    }
    useEffect(() => {
           getUser().then(r => r).catch(e => e);
    }, [auth]);
    return(
        <div className="relative">
            <div>
                {
                    user.imageUrl ? <Avatar className="cursor-pointer"  showFallback isBordered onClick={
                        () => setIsActiveProfile(!isActiveProfil)
                        } color="secondary" src={user.imageUrl}/> :
                        <Avatar className="cursor-pointer" showFallback isBordered onClick={
                            () => setIsActiveProfile(!isActiveProfil)} color="secondary" src='https://images.unsplash.com/broken'/>
                }
            </div>
            {isActiveProfil ?
                <div className="min-w-72 z-40 text-black bg-white rounded-md  right-[2px] top-14 border  absolute pb-3  pt-4 flex flex-col ">
                    <div className="triangle absolute  -top-2 right-0"></div>
                    <div className='flex gap-3  px-6'>
                        {
                            user.imageUrl ? <Avatar className=""  showFallback isBordered
                                color="secondary" src={user.imageUrl}/> :
                                <Avatar showFallback isBordered color="secondary" src='https://images.unsplash.com/broken'/>
                        }
                        <div>
                            <p className="text-[14px] font-semibold ">{user.nom} {user.prenom}</p>
                            <p className="font-medium text-[10px] ">{user.role}</p>
                        </div>
                    </div>
                    <Divider className="my-4"/>
                    <div className="flex  flex-col">
                        <div onClick={()=>{
                            navigate("/profile", { replace: true })
                        }} className="flex cursor-pointer p-2 hover:bg-gray-300 text-[14px] font-medium items-center px-6 gap-2 ">
                            <CgProfile className="text-[18px] font-bold "/>
                            <p>Profile</p>
                        </div>
                        <EditProfile user={user}/>
                    </div>
                    <Divider className="my-4"/>
                    <div onClick={()=>{
                        logout()
                    }} className="flex text-[14px] font-medium gap-2 cursor-pointer p-2 hover:bg-gray-300  items-center px-6">
                        <TbLogout2 className="text-[18px] "/>
                        <p>Deconnecter</p>
                    </div>
                </div> : null
            }
        </div>
    )
}

export default Profile;