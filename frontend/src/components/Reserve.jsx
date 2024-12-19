import React, { useState, useEffect } from 'react';
import axios from "../api/axios.js";
import useAuth from "../hook/useAuth.js";
import {Button, Popover, PopoverContent, PopoverTrigger} from "@nextui-org/react";
import clientCarte from "./ClientCarte.jsx";
import toast from "react-hot-toast";

const Reserve = ({type, idBook}) => {
    const [doc,setDoc] = useState({})
    const [currentDate, setCurrentDate] = useState("");
    const [currentTime, setCurrentTime] = useState("");
    const [client,setClient] = useState({})
    const [form, setForm] = useState({ userName: '', bookName: '', reservationDate: '', returnDate: '' });
    const [reservations, setReservations] = useState([]);
    const {auth} = useAuth();
    const [carteClient,setCarteClient] = useState({})

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    }
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
    const updateCarteClient = () => {
            axios.put("/carteClient/nbOperation", {
                id: carteClient.id,
                nbOperation: carteClient.nbOperation + 1
            }, {
                headers: {
                    "Authorization": "Bearer " + auth.token,
                }
            }).then(response => {
                console.log(response.data)
            }).catch(error => {
                console.log(error)
            })
    }
    const getBook = async ()=>{

        try{
            const response = await axios.get('/documents/' + idBook,{
                headers:{
                    'Authorization': 'Bearer ' + auth.token,
                }
            });

            setDoc(response.data)
        }catch (e) {
            console.log(e)
        }
    }
    const reserve = () => {
        console.log("idBook :  ",type);

        axios.post("reservations/reserve", {
            idCart: client.idcartClient,
            idDocument: idBook,
            type: type,
        }, {
            headers: {
                "Authorization": "Bearer " + auth.token,
            }
        }).then(response => {
            toast.success("Reservation effectuer avec success")
            toast.success("En va  envouyer un message aprés quelque heures si le document est disponuble sinon vous pouvez attendre le message de confirmation")
        }).catch(error => {
            console.log(error)
        })
    }
    useEffect(() => {
        getClient();
        getBook();
    }, []);
    useEffect(() => {
        if (client.idcartClient) {
            getCarteClient();
            console.log(carteClient)
        }
    }, [client]);

        return (
                carteClient?.nbrEmprunte < 5 ? (
                    <div className="p-4 gap-2 text-black flex flex-col items-center justify-center">
                        <p>Il rest {doc?.nombreExemplaire} exemplaire de <span className="font-semibold text-[#564592] ">{doc?.titre}</span> disponible pour le moment</p>
                        <p>Le processus {type=="emprunter" ? "d'emprunte" : "du location"} prendre quelque heures (1h-3h)</p>
                        {
                            type == "location" && (
                                <p>fraix du l'ocation est : {doc?.fraixExige} DH / Jour  </p>
                            )
                        }
                        <p className="">
                            Vouslez vous comfirmer la reservation ?
                        </p>
                       <div className="flex gap-2 items-center justify center">
                           <Button onClick={reserve}  type="" className="bg-[#564592] text-white">Oui</Button>
                           <Button   type="" className="hover:bg-red-400 text-white">Non</Button>
                       </div>
                    </div>
                ) : (
                    <p>
                        Vous avez atteint le nombre maximal d'emprunts
                    </p>
                )
        )

}

export default Reserve;