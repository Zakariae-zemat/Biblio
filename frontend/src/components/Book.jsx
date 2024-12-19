import React from 'react';
import {useNavigate} from "react-router-dom";
import useAuth from "../hook/useAuth.js";
import {Button, Tooltip} from "@nextui-org/react";
import axios from "../api/axios.js";
import toast from "react-hot-toast";


const Book = ({imageUrl,titre,Author,date,type,id,nbrExemplaire,isChanged,setIsChanged}) => {
    const perdus = async () =>{
        try {
            await axios.put('/documents/perdu?id='+id, {}, {
                headers: {
                    "Authorization": "Bearer " + auth.token,
                }
            });
            setIsChanged(!isChanged)
            toast.success("Exemplaire perdu")
        }catch (e) {
            console.log(e)
        }
    }
    const navigate=useNavigate()
    const {auth} = useAuth();
    return(
        <div
            className="bg-white text-black max-w-72 hover: opacity-90 hover:opacity-95 gap-1  rounded-md p-4 flex flex-col  ">
            <div className="flex gap-3 items-center">
                <img src={imageUrl} className="w-26 h-44" alt={titre}/>
                <div className="py-2">
                    <p className=" text-[16px] font-semibold">{titre}</p>
                    <p className=" text-[14px] font-semibold">Author : </p>
                    <p className=" text-[12px]  font-medium">{Author}</p>
                    <p className=" text-[14px] font-semibold">Publication</p>
                    <p className=" text-[12px]  font-medium">{date}</p>
                    <p className=" text-[14px] font-semibold">Type</p>
                    <p className=" text-[12px]  font-medium">{type}</p>

                </div>
            </div>
            {
                auth.role == "BIBLIOTHECAIRE" && (
                    <div className="flex gap-3 items-center">
                        <p className=" text-[14px] font-semibold">Nombre Exemplaires : </p>
                        <p className=" text-[12px]  font-medium">{nbrExemplaire}</p>
                    </div>
                )
            }
            {
                auth.role == "CLIENT" ?
                    <button
                        className="border bg-[#564592] hover:bg-[#4A4E69] text-white  rounded-md py-2 font-semibold text-[10px] px-8 "
                        onClick={() => {
                            navigate('/bookDetails/' + id, {state: id})
                        }}>Voir Details</button> :
                    nbrExemplaire > 0 ? (<Button
                        onClick={perdus}
                        className="border bg-red-500 text-white  rounded-md py-2 font-semibold text-[10px] px-8 "
                    >
                        Exemplaire Perdu
                    </Button>) : (
                     <Tooltip color="danger" content="Pas d'exemplaires" size="sm" >
                        <div className="w-full cursor-not-allowed ">
                            <Button
                                isDisabled={true}
                                className="border w-full bg-red-500 text-white  rounded-md py-2 font-semibold text-[10px] px-8 "
                            >
                                Exemplaire Perdu
                            </Button>
                        </div>
                    </Tooltip>
                    )

            }
        </div>
    )
}
export default Book;