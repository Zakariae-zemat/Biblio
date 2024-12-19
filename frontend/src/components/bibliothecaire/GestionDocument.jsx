import React, {useEffect, useRef, useState} from 'react';
import {
    Avatar,
    Button,
    Modal,
    ModalBody,
    ModalContent,
    ModalFooter,
    ModalHeader,
    Select, SelectItem, Tab, Tabs,
    useDisclosure
} from "@nextui-org/react";
import { PlusIcon } from "../utils/PlusIcon.jsx";
import { CameraIcon } from "../utils/CameraIcon.jsx";
import * as yup from 'yup';
import axios from "../../api/axios.js";
import useAuth from "../../hook/useAuth.js";
import toast from "react-hot-toast";


const classificationDeweyItems = [
    { value: '000', label: 'Généralités' },
    { value: '100', label: 'Philosophie' },
    { value: '200', label: 'Religion' },
    { value: '300', label: 'Sciences sociales' },
    { value: '400', label: 'Langues' },
    { value: '500', label: 'Sciences' },
    { value: '600', label: 'Techniques' },
    { value: '700', label: 'Arts' },
    { value: '800', label: 'Littérature' },
    { value: '900', label: 'Géographie et Histoire' },
];

const formatItems = [
    { value: 'format1', label: 'mp4' },
    { value: 'format2', label: 'mp3' },
    // Add more formats as needed
];
const types = [
    { value: 'Livre', label: 'Livre' },
    { value: 'DisqueCompacte', label: 'Disque Compacte' },
    { value: 'Journale', label: 'Journale' },
    { value: 'CasseteVideo', label: 'Cassete Video' },
    { value: 'Periodique', label: 'Periodique' },
];

const AddBibliothecaire = ({isChanged,setIsChanged}) => {
    const [isChecked, setIsChecked] = useState(false);

    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };
    const estFortementDemande = useRef("")
    const [discripteurs,setDiscripteurs] = useState([])
    const discripteur = useRef()
    const [dis,setDis] = useState('')
    const [selecedDiscripteur,setSelectedDiscripteur] = useState([])
    const [selectedType, setSelectedType] = useState('');
    const {auth} = useAuth();
    const [format, setFormat] = useState('');
    const [classificationDewey, setClassificationDewey] = useState('');
    const editionRef = useRef();
    const imageUrl = useRef();
    const titreRef = useRef();
    const auteurRef = useRef();
    const descriptionRef = useRef();
    const fraixExigeRef = useRef();
    const nombreExemplaireRef = useRef();
    const datePublicationRef = useRef();
    const langueRef = useRef();
    const dureeRef = useRef();
    const [imageSrc, setImageSrc] = useState("");
    const [errors, setErrors] = useState({});
    const schema = yup.object().shape({
        imageUrl: yup.mixed().required('Requis'),
        titre: yup.string().required('Requis'),
        prenom: yup.string().required('Requis'),
        adresse: yup.string().required('Requis'),
        naissance: yup.date("date invalid").required('Requis'),
        email: yup.string().email('Email invalide').required('Requis'),
        password: yup.string().min(8, 'Doit contenir au moins 8 caractères').required('Requis'),
    });
    const getKeyWords = async ()=>{
        try{
            const response = await axios.get('/discripteurs',{
                headers:{
                    "Authorization": "Bearer " + auth.token,
                }
            })
            setDiscripteurs(response.data)
            console.log(response.data)
        }catch (e) {

            console.log(e)
        }
    }
    useEffect(() => {
        getKeyWords().then(r => r).catch(e => e)
    },[])
    function handleOnSubmit(file) {
        return new Promise((resolve, reject) => {
            const formData = new FormData();
            formData.append("file", file);
            formData.append("upload_preset", "ml_default");

            fetch(
                "https://api.cloudinary.com/v1_1/dbdozvm8s/image/upload",
                {
                    method: "POST",
                    body: formData,
                }
            )
                .then(async (r) => {
                    const data = await r.json();
                    setImageSrc(data.secure_url);
                    console.log(data.secure_url)
                    resolve(data.secure_url);
                })
                .catch(reject);
        });
    }
    function handleChange(e) {
        setImageSrc(URL.createObjectURL(e.target.files[0]));
    }

    const { isOpen, onOpen, onOpenChange } = useDisclosure();
    const handleSubmit = async (event) => {
        event.preventDefault();
        const imageUrll = await handleOnSubmit(imageUrl.current.files[0]);
        console.log(imageUrl.current.files[0])
        const data = {
            titre: titreRef.current.value,
            auteur: auteurRef.current.value,
            description: descriptionRef.current.value,
            fraixExige: fraixExigeRef.current.value,
            nombreExemplaire: nombreExemplaireRef.current.value,
            datePublication: datePublicationRef.current.value,
            langue: langueRef.current.value,
            estFortementDemande: isChecked,
            discripteurs: dis,
            type: selectedType,
            imageUrl: imageUrll,
            classificationDewey: classificationDewey,
            format: format,
            duree:dureeRef.current ? dureeRef.current.value : "",
            edition: editionRef.current ? editionRef.current.value : "",
        };
        console.log(data)

        //backend
        try{
            const response = await axios.post('/documents',data,{
                headers:{
                    "Authorization": "Bearer " + auth.token,
                }
            })
            console.log(response.data)
            setIsChanged(!isChanged)
            toast.success('Document ajouté avec succès')
        }catch (e) {
            console.log(e)
            toast.error("Erreur lors de l'ajout du document")
        }
    }

    const addDiscripteur = async (event) => {
        event.preventDefault();
        const data = {
            keyword: discripteur.current.value,
        };
        console.log(data)
        try{
            const response = await axios.post('/discripteurs',data,{
                headers:{
                    "Authorization": "Bearer " + auth.token,
                }
            })
            console.log(response.data)
            getKeyWords()
            toast.success('Discripteur ajouté avec succès')
        }catch (e) {
            console.log(e)
            toast.error("Erreur lors de l'ajout du discripteur")
        }
    }

    useEffect(() => {
        let myString = Array.from(selecedDiscripteur).join(',');
        setDis(myString)
         }, [selecedDiscripteur]);
    return (
        <>
            <Button
                onPress={onOpen}
                onClick={() => {
                    onOpen()
                }}
                className=" p-4 bg-[#564592] text-background"
                endContent={<PlusIcon />}
            >
                Ajouter Nouveau
            </Button>
            <Modal placement="center" className="py-6  mt-8" scrollBehavior="outside"  isOpen={isOpen}  onOpenChange={onOpenChange}>
                <ModalContent className="text-black  " >
                    {(onClose) => (
                        <div className="">
                            <Tabs  className="ml-6" variant="bordered" color="secondary" aria-label="Options">
                                <Tab Key="document"  title="document">
                                    <ModalHeader className="flex flex-col font-semibold text-lg pb-6 gap-1">Nouveau
                                        Document</ModalHeader>
                                    <ModalBody>
                                        <form className="flex flex-col gap-2 justify-center w-96"
                                              onSubmit={handleSubmit}>
                                            <div className="flex gap-6 text-gray-400 text-sm items-center ">
                                                <p>image document : </p>
                                                <div
                                                    className="border relative cursor-pointer  flex items-center justify-center  w-24 h-24 ">
                                                    <input ref={imageUrl} name="image" id="fileUpload" type="file"
                                                           onChange={(event) => {
                                                               handleChange(event)
                                                           }}
                                                           className="  absolute  rounded-full  w-full h-full px-6 p-2 cursor-pointer "/>
                                                    <label className="relative cursor-pointer h-full w-full"
                                                           htmlFor="fileUpload">
                                                        {
                                                            imageSrc ? <img src={imageSrc} alt="image"
                                                                            className=" w-full h-full"/> :
                                                                <Avatar className="w-full  h-full absolute"
                                                                        showFallback
                                                                        src='https://images.unsplash.com/broken'
                                                                        fallback={
                                                                            <CameraIcon
                                                                                className="animate-pulse w-6 h-6 text-default-500"
                                                                                fill="currentColor" size={20}/>
                                                                        }/>

                                                        }
                                                    </label>

                                                </div>
                                            </div>
                                            <input onChange={() => setErrors({})}
                                                   ref={titreRef} name="titre" type="text" placeholder="Titre"
                                                   className="border p-2 focus:outline-none rounded text-gray-700 "/>
                                            {errors.titre &&
                                                <p className="text-sm font-normal text-red-500">{errors.titre}</p>}

                                            <input onChange={() => setErrors({})}
                                                   ref={auteurRef} name="auteur" type="text" placeholder="Auteur"
                                                   className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                            {errors.auteur &&
                                                <p className="text-sm font-normal text-red-500">{errors.auteur}</p>}

                                            <input onChange={() => setErrors({})}
                                                   ref={descriptionRef} name="description" type="text"
                                                   placeholder="Description"
                                                   className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                            {errors.description &&
                                                <p className="text-sm font-normal text-red-500">{errors.description}</p>}

                                            <input onChange={() => setErrors({})}
                                                   ref={fraixExigeRef} name="fraixExige" type="number"
                                                   placeholder="Fraix Exige"
                                                   className="border focus:outline-none p-2 text-gray-700  rounded"/>
                                            {errors.fraixExige &&
                                                <p className=" text-sm font-normal text-red-500">{errors.fraixExige}</p>}

                                            <input onChange={() => setErrors({})}
                                                   ref={nombreExemplaireRef} name="nombreExemplaire" type="number"
                                                   placeholder="Nombre Exemplaire"
                                                   className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                            {errors.nombreExemplaire &&
                                                <p className="text-sm font-normal text-red-500">{errors.nombreExemplaire}</p>}

                                            <input onChange={() => setErrors({})}
                                                   ref={datePublicationRef} name="datePublication" type="date"
                                                   placeholder="Date Publication"
                                                   className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                            {errors.datePublication &&
                                                <p className="text-sm font-normal text-red-500">{errors.datePublication}</p>}
                                            <input onChange={() => setErrors({})}
                                                   ref={langueRef} name="langue" type="text" placeholder="Langue"
                                                   className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                            {errors.langue &&
                                                <p className="text-sm font-normal text-red-500">{errors.langue}</p>}
                                            <Select
                                                onSelectionChange={(value) => {
                                                    console.log(value.currentKey);
                                                    setSelectedType(value.currentKey);
                                                }}
                                                items={types}
                                                placeholder="Choisir le type"
                                                className="max-w-xs text-black/90 "
                                            >
                                                {(type) => <SelectItem className="text-black"
                                                                       key={type.value}>{type.label}</SelectItem>}
                                            </Select>
                                            {selectedType === 'Livre' && (
                                                <div className="flex flex-col gap-4">
                                                    <input
                                                        className="border focus:outline-none p-2 rounded text-gray-700 "
                                                        type="number" placeholder="Nombre de pages"/>
                                                    <Select
                                                        onSelectionChange={(value) => {
                                                            console.log(value.currentKey);
                                                            setClassificationDewey(value.currentKey)
                                                        }}
                                                        items={classificationDeweyItems}
                                                        placeholder="Classification Dewey"
                                                        className="max-w-xs text-black/90 "
                                                    >
                                                        {(type) => <SelectItem className="text-black"
                                                                               key={type.value}>{type.label}</SelectItem>}
                                                    </Select>
                                                    <div className="flex items-center gap-3">
                                                        <label htmlFor="EstFortementDemander"
                                                               className="text-gray-700 text-sm">Est fortement
                                                            demandé ?</label>
                                                        <input id="EstFortementDemander" type="checkbox"
                                                               checked={isChecked}
                                                               onChange={handleCheckboxChange}
                                                               className="border focus:outline-none p-2 rounded text-gray-700"
                                                               placeholder="Est fortement demandé ? "/>
                                                    </div>
                                                </div>
                                            )}
                                            {['Periodique', 'Journale'].includes(selectedType) && (
                                                <input ref={editionRef} type="number"
                                                       className="border focus:outline-none p-2 rounded text-gray-700 "
                                                       placeholder="Edition"/>
                                            )}
                                            {['DisqueCompacte', 'CasseteVideo'].includes(selectedType) && (
                                                <div className="flex items-center gap-3">
                                                    <Select
                                                        onSelectionChange={(value) => {
                                                            console.log(value.currentKey);
                                                            setFormat(value.currentKey)
                                                        }}
                                                        items={formatItems}
                                                        placeholder="Format"
                                                        className="max-w-xs text-black/90 "
                                                    >
                                                        {(type) => <SelectItem className="text-black"
                                                                               key={type.value}>{type.label}</SelectItem>}
                                                    </Select>
                                                    <input type="number" ref={dureeRef}
                                                           className="border focus:outline-none p-2 rounded text-gray-700 "
                                                           placeholder="Durée (en min)"/>
                                                </div>
                                            )}
                                            <Select
                                                selectionMode="multiple"
                                                onSelectionChange={(value) => {
                                                    console.log(value);
                                                    setSelectedDiscripteur(value)
                                                }}
                                                items={discripteurs}
                                                placeholder="Discripteurs"
                                                className="max-w-xs text-black/90 ">
                                                {(type) => <SelectItem className="text-black"
                                                                       key={type.id}>{type.keyword}</SelectItem>}
                                            </Select>
                                            <button onClick={() => {
                                                setTimeout(() => {
                                                    onClose()
                                                }, 3000);
                                            }} className="bg-[#564592] text-white py-2 px-6 rounded-md"
                                                    type="submit">Ajouter
                                            </button>
                                        </form>
                                    </ModalBody>
                                </Tab>
                                <Tab Key="discripteur" title="discripteuur">
                                    <ModalHeader className="flex flex-col font-semibold text-lg pb-6 gap-1">Nouveau
                                        Discripteur</ModalHeader>
                                    <ModalBody>
                                        <form className="flex flex-col gap-2 justify-center w-96"
                                              onSubmit={addDiscripteur}>
                                            <input onChange={() => setErrors({})}
                                                   ref={discripteur} name="discripteur" type="text" placeholder="Nom du Discripteur"
                                                   className="border p-2 focus:outline-none rounded text-gray-700 "/>
                                            {errors.titre &&
                                                <p className="text-sm font-normal text-red-500">{errors.titre}</p>}
                                            <button onClick={() => {
                                                setTimeout(() => {
                                                    onClose()
                                                }, 1000);
                                            }} className="bg-[#564592] text-white py-2 px-6 rounded-md"
                                                    type="submit">Ajouter
                                            </button>
                                        </form>
                                    </ModalBody>
                                </Tab>
                            </Tabs>
                        </div>
                    )}
                </ModalContent>
            </Modal>
        </>
    );
}

export default AddBibliothecaire;