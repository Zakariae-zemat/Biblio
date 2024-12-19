import React from 'react';
import NavbarLayout from "../components/NavbarLayout.jsx";
import {Tab, Tabs} from "@nextui-org/react";
import Pretations from "../components/bibliothecaire/Pretations.jsx";
import Reservations from "../components/bibliothecaire/Reservations.jsx";
import Alocations from "../components/bibliothecaire/Alocations.jsx";

const Transactions = (props) =>{
    return(
        <NavbarLayout>
            <div className="bg-white overflow-x-scroll rounded-md p-3">
                <Tabs color="secondary" variant="underlined"  className="text-black w-[50rem] " size="lg" >
                    <Tab key="Reservation" title="Reservation">
                        <Reservations/>
                    </Tab>
                    <Tab key="Prétation" title="Prétation">
                       <Pretations/>
                    </Tab>
                    <Tab key="Location" title="Location">
                        <Alocations/>
                    </Tab>
                </Tabs>
            </div>
        </NavbarLayout>
    )
}

export default Transactions;