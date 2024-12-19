import React, {useEffect, useState} from 'react'
import NavbarLayout from "../components/NavbarLayout.jsx";
import axios from "../api/axios.js";
import useAuth from "../hook/useAuth.js";
import {Avatar} from "@nextui-org/react";
import {formatDate} from "./BookDetails.jsx";
import Pdf from "../components/documents/Pdf.jsx";
import ClientCarte from "../components/ClientCarte.jsx";
import {useNavigate} from "react-router-dom";
import {useAtom} from "jotai";
import {isLoadingData} from "../context/GlobalProvider.jsx";
const ProfileDisplay = (props) =>{
    const { auth } = useAuth();
    const navigate = useNavigate();
    const [isLoading, setLoading] = useState(false);
    const [user,setUser] = useState({});
    const [isCarteClient,setIsCarteClient] = useState(false);
    const [isLoadingDataa,setIsLoadingDataa] = useAtom(isLoadingData);
    const  getUser = async () => {
        setIsLoadingDataa(true)
        try{
            const response = await axios.get('/users/user?email='+auth.email,{
                headers:{
                    'Authorization': 'Bearer ' + auth.token,
                }
            });
            setUser(response.data.utilisateur);
            setIsLoadingDataa(false)
        }catch (error){
            console.log(error)
        }
    }
    useEffect(() => {
        getUser().then(r=>e).catch(e => e);
    }, [auth])
    return(
        <NavbarLayout>
            <div className="py-6">
                <div>
                    <h1 className="text-2xl font-semibold ">
                        Votre Profile
                    </h1>
                </div>
                <div className=" flex flex-col  w-full gap-4 ">
                    <div className="flex flex-col md:flex-row gap-5 w-full" >
                        <div className="py-5  w-full text-black mt-4 px-5 bg-white rounded-xl">
                            <div className="flex gap-4 items-center">
                                {
                                    user.imageUrl ? <Avatar className=" h-32 w-32 "  showFallback isBordered color="secondary" src={user.imageUrl}/> :
                                        <Avatar className=" h-32 w-32 " showFallback isBordered  color="secondary" src='https://images.unsplash.com/broken'/>
                                }
                                <div>
                                    <p className="text-lg font-semibold">{user.prenom} {user.nom}</p>
                                    <p className="text-sm">{user.role}</p>
                                </div>
                            </div>
                        </div>
                        <div className="flex  w-full lg:flex-row  flex-col lg:gap-4">
                            <div className="py-5  w-full  text-black mt-4 px-5 bg-white rounded-xl">
                                <div className="flex flex-col gap-3 ">
                                    <div className=" flex items-center gap-3">
                                        <p className="text-xl text-[#4A4E69] font-semibold">Email : </p>
                                        <p className="text-md">{user.email}</p>
                                    </div>
                                    <div className=" flex items-center gap-3">
                                        <p className="text-xl text-[#4A4E69] font-semibold">Tel : </p>
                                        <p className="text-md">{user?.telephone ? user?.telephone : "<VIDE>"}</p>
                                    </div>
                                    <div className=" flex items-center gap-3">
                                        <p className="text-xl text-[#4A4E69] font-semibold">Ville : </p>
                                        <p className="text-md">{user?.addresse ? user?.addresse : "<VIDE>"}</p>
                                    </div>
                                    <div>
                                        <div className="flex items-center gap-3">
                                            <p className="text-xl text-[#4A4E69] font-semibold">Cin : </p>
                                            <p className="text-md">{user?.cin ? user?.cin : "<VIDE>"}</p>
                                        </div>
                                        <div className=" flex items-center gap-3">
                                            <p className="text-xl text-[#4A4E69] font-semibold">Naissance : </p>
                                            <p className="text-md">{user?.naissance ? formatDate(user?.naissance) : "<VIDE>"}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="flex flex-col gap-4">
                        <div className="flex items-center gap-6">
                            {
                                user.estRegistered ? <button onClick={
                                    () => {
                                        setIsCarteClient(!isCarteClient)
                                    }
                                } className="bg-[#564592] text-white rounded-md w-32 py-3 px-4">
                                    {
                                        isCarteClient ? "Hide Carte" : "Voir Carte"
                                    }
                                </button> : null
                            }
                            <button onClick={
                                () => {
                                    navigate('/')
                                }
                            } className="bg-[#564592]  text-white rounded-md py-3 px-4" style={{marginLeft: 'auto'}
                            }>
                                Retour Vers Page Principale
                            </button>
                        </div>
                        {
                            isCarteClient ?
                                (<div className="bg-white rounded-md py-5 px-4 ">
                                    <ClientCarte client={user}/>
                                    <button className="bg-[#564592] text-white rounded-md py-3 px-4 "
                                            onClick={() => Pdf("carte")}>Download PDF
                                    </button>
                                </div>)
                                : null
                        }
                    </div>
                </div>
            </div>
        </NavbarLayout>
    )
}
export default ProfileDisplay;