import React, {useEffect, useState} from 'react';
import axios from "../api/axios.js";
import useAuth from "../hook/useAuth.js";
import {Avatar} from "@nextui-org/react";
import logo from '../assets/logo.png'
import profi from '../assets/profi.jpg'
const ClientCarte = (props) => {
    const {auth} = useAuth();
    const [qrCodeSrc, setQrCodeSrc] = useState(``);
    const [client,setClient] = useState({})
    const [carteClient,setCarteClient] = useState({})
    const getClient = async () => {
        try {
            const response = await axios.get('/clients/client?email='+auth.email,{
                    headers:{
                        'Authorization': 'Bearer ' + auth.token,
                    }
                }
            );
            console.log(response.data);
            setClient(response.data)
            console.log(client)

        } catch (error) {
            console.log(error);
        }
    }
    const getCarteClient = () =>{
        axios.get("/carteClient/client?id=" + client.idcartClient, {
            headers : {
                "Authorization" : "Bearer " + auth.token,
            }
        }).then(
            response => {
                setCarteClient(response.data)
            }
        ).catch(error => {
            console.log(error)
        }
        )
    }
    const createBareCode =  () => {
        axios.get(`/clients/qrcode?email=`+ auth.email, {
            headers : {
                "Authorization" : "Bearer " + auth.token
            },
            responseType: 'arraybuffer',
        })
            .then(response => {
                const base64 = btoa(
                    new Uint8Array(response.data).reduce(
                        (data, byte) => data + String.fromCharCode(byte),
                        '',
                    ),
                );
                setQrCodeSrc(`data:${response.headers['content-type'].toLowerCase()};base64,${base64}`);
                console.log(qrCodeSrc);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
    useEffect(() => {
        createBareCode();
        getClient().then(r => r).catch(e => e);
        if(client.idcartClient){
            getCarteClient()
        }
    }, [client.idcartClient]);
    return(
            <div id="carte" className=" p-6  bg-gray-300 shadow-md rounded-lg overflow-hidden flex flex-col  max-w-md mx-auto">
                <div className="flex items-center justify-between ">
                    <h1 className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">Carte
                        Client {carteClient?.estResident ? "Resident" : "Non Resident"}</h1>
                    <div className="flex items-center justify-center gap-2">
                        <img src={logo} alt="logo" width={20} height={20}/>
                        <p className="text-black">
                            biblio
                        </p>
                    </div>
                </div>
                <div className="flex justify-between">
                    <div className=" text-black flex flex-col gap-2 py-4">
                        <p className="block mt-1 text-lg leading-tight  font-medium text-black">{client.nom} {client.prenom}</p>
                        <p>
                           <span className="font-semibold">
                               date d'expriration :
                           </span> {carteClient.dateFin}
                        </p>
                        <img className="h-20 w-20 object-cover" src={qrCodeSrc}
                             alt="Barcode"/>
                    </div>
                    <div id="profile" className="px-6 py-4">
                        {client?.imageUrl ?
                            (<div>
                            <img className="h-20 w-20"  src={client?.imageUrl} alt="profile"/>
                        </div>) : <img  className="h-20 w-20" alt="profile"
                                          src='https://res.cloudinary.com/dbdozvm8s/image/upload/v1716015975/vlg87ihpgxwlcsjnwm0r.jpg'/>
                        }
                    </div>
                </div>
            </div>
    )
}

export default ClientCarte;