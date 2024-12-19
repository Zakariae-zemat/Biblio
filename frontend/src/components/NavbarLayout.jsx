import React from 'react';
import Navbar from "./Navbar.jsx";
import {useAtom} from "jotai";
import {
    isActiveLogin,
    isActiveProfile,
    isActiveRegister,
    isNotification,
    isStoreActive,
    isLoadingData
} from "../context/GlobalProvider.jsx";
import {Toaster} from "react-hot-toast";

const NavbarLayout = ({children}) => {
    const [isActiveLoged, setIsActiveLogin] = useAtom(isActiveLogin);
    const [isActiveRegisted, setIsActiveRegister] = useAtom(isActiveRegister);
    const [isNotif, setIsNotif] = useAtom(isNotification);
    const [isActiveProfil, setIsActiveProfile] = useAtom(isActiveProfile);
    const [isACtiveStore, setIsActiveStore] = useAtom(isStoreActive);
    const [isLoadingDataa,setIsLoadingDataa] = useAtom(isLoadingData);

    return (
         isLoadingDataa ? (
                <div className="h-full md:p-0 px-8 flex items-center justify-center  relative">
                    <span className="loading loading-dots loading-lg"></span>
                </div>
            ) : (
                <div className={`h-full md:p-0 px-8  relative  ${isActiveLoged || isActiveRegisted ? 'blur' : ''}`}
                     onClick={
                         () => {
                             if (isActiveLoged) {
                                 setIsActiveLogin(false)
                             }
                             if (isActiveRegisted) {
                                 setIsActiveRegister(false)
                             }
                             if (isNotif) {
                                 setIsNotif(false)
                             }
                             if (isACtiveStore) {
                                 setIsActiveStore(false)
                             }

                         }

                     }>
                    <div>
                        <Navbar/>
                    </div>
                    <Toaster/>
                    {children}
                </div>
            )
)
};

export default NavbarLayout;