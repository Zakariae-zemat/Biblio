import React, {useEffect, useState} from 'react';
import useAuth from "../hook/useAuth.js";
import axios from "../api/axios.js";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import NavbarLayout from "../components/NavbarLayout.jsx";
import {useAtom} from "jotai";
import Reserve from "../components/Reserve.jsx";
import {isActiveRegister, isLoadingData, isReserveActive} from "../context/GlobalProvider.jsx";
import {
    Button,
    useDisclosure, PopoverContent, Popover, PopoverTrigger, Tooltip
} from "@nextui-org/react";
import { compile } from "@onedoc/react-print";
import Pdf from "../components/documents/Pdf.jsx";
import ClientCarte from "../components/ClientCarte.jsx";
import toast from "react-hot-toast";
import { IoArrowBack } from "react-icons/io5";
export function formatDate(inputDate) {
    // Parse the input date
    const date = new Date(inputDate);
    // Extract the day, month, and year
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed in JavaScript
    const year = date.getFullYear();
    // Return the formatted date
    return `${day}/${month}/${year}`;
}


const BookDetails = () => {
    const [isLoadingDataa,setIsLoadingDataa] = useAtom(isLoadingData);
    const navigate = useNavigate();
    const {onClose,isOpen} = useDisclosure();
    const [client,setClient] = useState({})
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

    const {auth} = useAuth()
    const [isActiveReservation,setIsActiveReservation] = useAtom(isReserveActive)
    const {idBook} = useParams()
    const [doc,setDoc] = useState({})

    const creeCard = async () => {
        try {
            const response = await axios.post('/carteClient?email='+auth.email, null ,{
                headers: {
                    'Authorization': 'Bearer ' + auth.token,
                }
            });
            console.log(response.data);
            toast.success("Carte crée avec succès... Vous pouvez le récuperer dans votre profile!")
        }catch (e) {
            console.log(e)
        }
    }


    const contentCreateCard = (type)  => (
        <PopoverContent>
            <div className="px-3 text-black w-64 flex flex-col gap-6 text-center py-4">
                <p>
                    Vous avez besoin de cree une  carte pour {type} ce document
                </p>
                <div className="flex gap-2 items-center justify-center">
                    <Button onPress={onClose} className="border hover:bg-[#4A4E69] text-white bg-[#564592]  rounded-md px-2 py-2 " onClick={()=>{
                        creeCard()
                        getClient().then(r => r).catch(e => e);
                    }} >
                        Crée mon carte
                    </Button>
                </div>
            </div>
        </PopoverContent>
    );

    const getBook = async ()=>{
        setIsLoadingDataa(true)
        try{
            const response = await axios.get('/documents/' + idBook,{
                headers:{
                    'Authorization': 'Bearer ' + auth.token,
                }
            });
            setIsLoadingDataa(false)
            setDoc(response.data)
        }catch (e) {
            console.log(e)
        }
    }

    useEffect( () => {
        getBook()
        console.log(doc)
        getClient()
    }, []);

    return(
        <NavbarLayout>
            <div className="py-6 w-full">
                <div className="flex gap-2 items-center py-3 cursor-pointer hover:font-bold " onClick={
                    ()=>{
                        navigate('/mostPopulaire')
                    }
                }>
                    <IoArrowBack/>
                    <p>Retour</p>
                </div>
                <h1 className="text-4xl font-semibold">Document Détails</h1>
                <div
                    className="border-white text-black mt-6 bg-white  rounded-3xl shadow-2xl md:py-8 md:px-8 px-4 py-4  w-[100] md:max-w-[100%] lg:max-w-[75%] xl:max-w-[70%] ">
                    <div
                        className="flex md:flex-row flex-col md:items-start items-center md:justify-start justify-center  gap-3">
                        <div className="xl:w-[80%] lg:w-[50%] w-full flex  items-center  justify-center ">
                            <img src={doc.imgUrl} className="w-[80%] h-[80%] " width={250} height={200}
                                 alt={doc.titre}/>
                        </div>
                        <div className="flex flex-col w-full  md:items-start lg:gap-4 gap-1 sm:gap-3 ">
                            <div className="flex gap-2">
                                <p className=" text-2xl  md:text-4xl text-[#22223B]  tracking-wider font-semibold">{doc.titre}</p>
                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Auteur : </p>
                                <p className=" text-[15px]  font-medium">{doc.auteur}</p>
                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Date Publication :</p>
                                <p className=" text-[15px]  font-medium">{formatDate(doc.datePublication)}</p>
                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Genres : <span
                                ></span>
                                </p>
                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Type : <span
                                    className=" text-[15px] font-medium">{doc.type}</span></p>

                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Classification Dewey : <span
                                    className=" text-[15px] text-black font-medium">Roman, Policier, Policier,
                                    Fiction</span>
                                </p>
                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Langue : <span
                                    className=" text-[15px]  font-medium">Anglais Britannique</span></p>

                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Nombre De Pages : <span
                                    className=" text-[15px]  font-medium">230</span></p>
                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Nombre Exemplaire disponible
                                    : <span className=" text-[15px]  font-medium">23</span></p>

                            </div>
                            <div className="md:flex items-center gap-2">
                                <p className=" text-[16px] text-[#4A4E69] font-semibold">Note : <span
                                    className=" text-[15px]  font-medium">4.5</span></p>
                            </div>
                        </div>
                    </div>
                    <div className=" md:ml-8 flex items-start md:justify-start pt-6  gap-4">
                        <Popover >
                            <PopoverTrigger>
                                {
                                    client.nbrEmprunte < 5 ?
                                        (<Button  onClick={() => {
                                            setIsActiveReservation(!isActiveReservation)
                                        }} className="border focus:outline-none bg-[#564592] flex items-center justify-center button-36  py-3 hover:bg-[#4A4E69]  w-[40%] hover:shadow-blue-800 text-white  rounded-md  font-semibold text-[13px] px-10 ">
                                            Emprinter
                                        </Button>) :
                                        (
                                            <Tooltip color="secondary" content="vous avez depasser la limite du 5 documents" size="sm" >
                                                <div className="w-[40%]  cursor-not-allowed ">
                                                    <Button isDisabled  className="border w-full focus:outline-none bg-[#564592] flex items-center justify-center button-36  py-3 hover:bg-[#4A4E69]  hover:shadow-blue-800 text-white  rounded-md  font-semibold text-[13px] px-10 ">
                                                        Emprunter
                                                    </Button>
                                                </div>
                                            </Tooltip>
                                        )
                                }
                            </PopoverTrigger>
                            {!client.estRegistered ?
                                contentCreateCard("emprunter") :
                                (
                                    <PopoverContent>
                                        <Reserve type="emprunter" idBook={idBook}/>
                                    </PopoverContent>
                                )
                            }
                        </Popover>
                        <Popover>
                            <PopoverTrigger>
                                {
                                    client.nbrEmprunte < 5 ?
                                        ( <Button
                                            onClick={() => {
                                                setIsActiveReservation(!isActiveReservation)
                                            }}
                                            className="border-none focus:outline-none flex items-center justify-center border-black py-3 button-36  hover:border-none w-[40%] px-10 hover:text-white hover:bg-[#564592] text-black  rounded-md font-semibold text-[13px]  ">
                                            Louer
                                        </Button>) :
                                        (
                                            <Tooltip color="primary" content="vous avez depasser la limite du 5 documents" size="sm" >
                                                <div className="w-[40%] cursor-not-allowed  ">
                                                    <Button
                                                        isDisabled={true}
                                                        className="border-none w-full focus:outline-none flex items-center justify-center border-black py-3 button-36  hover:border-none  px-10 hover:text-white hover:bg-[#564592] text-black  rounded-md font-semibold text-[13px]  ">
                                                        Louer
                                                    </Button>
                                                </div>
                                            </Tooltip>
                                        )
                                }
                            </PopoverTrigger>
                            {!client.estRegistered ?
                                contentCreateCard("louer") :
                                (
                                    <PopoverContent>
                                        <Reserve type="location" idBook={idBook}/>
                                    </PopoverContent>
                                )
                            }
                        </Popover>
                    </div>
                </div>
            </div>
        </NavbarLayout>
    )
}
export default BookDetails;