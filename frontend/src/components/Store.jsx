import React, {useEffect, useState} from 'react';
import {Avatar} from "@nextui-org/react";
import {Divider} from "@nextui-org/divider";
import {IoMdNotifications} from "react-icons/io";
import {MdLocalGroceryStore} from "react-icons/md";
import {useAtom} from "jotai";
import {isStoreActive} from "../context/GlobalProvider.jsx";
import axios from "../api/axios.js";
import useAuth from "../hook/useAuth.js";

const Store = (props) => {
    const [transactions, setTransactions] = useState([]);
    const [isStoreActivee, setIsStoreActive] = useAtom(isStoreActive);
    const {auth} = useAuth();
    const getTransactions = async () => {
        try{
            const response = await axios.get('/transactions/user?email='+auth.email, {
                headers: {
                    "Authorization": "Bearer " + auth.token
                }
            })
            setTransactions(response.data)
        }catch (error){
            console.log(error)
        }
    }
    useEffect(() => {
        getTransactions()
    }, [isStoreActivee]);
    return (
        <div className="relative flex items-center">
            <div>
                <MdLocalGroceryStore className="text-[1.6rem] cursor-pointer " onClick={() => {
                    setIsStoreActive(!isStoreActivee)
                }}/>
            </div>
            {isStoreActivee ?
                <>
                    <div className="triangle absolute  top-10 -right-1"></div>
                    <div
                        className="min-w-72 z-40 text-black bg-white rounded-md md:-right-[4px] -right-[12px]  top-12  absolute py-4 flex flex-col ">

                        <div>
                            {
                                transactions?.length > 0 ?
                                    transactions.map(
                                        (item, value) => {
                                            return (
                                                <div>
                                                    <div
                                                        className="flex py-3 rounded  px-6 gap-3 cursor-pointer hover:bg-gray-300">
                                                        <div className="flex flex-col   ">
                                                            <p className="font-semibold text-sm">{item.type}</p>
                                                            <p className="text-[10px] font-semibold ">{item?.titreDocument}</p>
                                                            <p className="text-[10px]">{item.message}</p>
                                                        </div>
                                                    </div>
                                                    <Divider className="my-4"/>
                                                </div>

                                            )
                                        }
                                    ) :
                                    <div className="flex items-center justify-center text-sm font-normal"><p> Vous avez
                                        pas
                                        de transaction
                                    </p>
                                    </div>
                            }
                        </div>
                    </div>
                </>
                : null
            }
        </div>
    );
}

export default Store;