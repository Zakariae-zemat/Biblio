import React from "react";
import axios from "../../api/axios.js";
const columns = [
    {name: "ID", uid: "id", sortable: true},
    {name: "DOCUMENT", uid: "documentTitre", sortable: true},
    {name: "NOMBRE RESERVATIONS", uid: "nombreReservation", sortable: true},
    {name: "NOMBRE LOCATIONS", uid: "nombreLocation", sortable: true},
    {name: "NOMBRE PRETS", uid: "nombrePret", sortable: true},
    {name: "NOMBRE RETOUR", uid: "nombreRetour", sortable: true},
    {name: "NOMBRE PERDU", uid: "nombrePerdu", sortable: true},
    {name: "DATE STATISTIQUE", uid: "dateStatistique", sortable: true},
];


export {columns};
