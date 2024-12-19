import React, {useEffect, useRef, useState} from 'react';
import { Avatar, Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader, useDisclosure } from "@nextui-org/react";
import { PlusIcon } from "../utils/PlusIcon.jsx";
import { CameraIcon } from "../utils/CameraIcon.jsx";
import * as yup from 'yup';
import axios from "../../api/axios.js";
import useAuth from "../../hook/useAuth.js";
import toast from "react-hot-toast";

const AddBibliothecaire = ({isChanged,setIsChanged}) => {
    const {auth} = useAuth();
    const imageUrl = useRef();
    const nomRef = useRef();
    const prenomRef = useRef();
    const adresseRef = useRef();
    const naissanceRef = useRef();
    const emailRef = useRef();
    const passwordRef = useRef();
    const [imageSrc, setImageSrc] = useState("");
    const [errors, setErrors] = useState({});
    const schema = yup.object().shape({
        imageUrl: yup.mixed().required('Requis'),
        nom: yup.string().required('Requis'),
        prenom: yup.string().required('Requis'),
        adresse: yup.string().required('Requis'),
        naissance: yup.date("date invalid").required('Requis'),
        email: yup.string().email('Email invalide').required('Requis'),
        password: yup.string().min(8, 'Doit contenir au moins 8 caractères').required('Requis'),
    });

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
            nom: nomRef.current.value,
            prenom: prenomRef.current.value,
            adresse: adresseRef.current.value,
            naissance: naissanceRef.current.value,
            email: emailRef.current.value,
            password: passwordRef.current.value,
            imageUrl: imageUrll
        }
        schema.validate(data, { abortEarly: false })
            .then(valid => {
                    console.log(data)
            })
            .catch(err => {
                const errorMessages = err.inner.reduce((acc, current) => {
                    acc[current.path] = current.message;
                    return acc;
                }, {});
                setErrors(errorMessages);
            });
        console.log(data)
        console.log(Object.keys(errors).length)
        if(Object.keys(errors).length === 0) {
            try{
                console.log(auth.token)
                axios.post('/bibliothecaire', data, {
                    headers: {
                        "Authorization": "Bearer " + auth.token,
                    }
                })
                    .then(res => {
                        console.log(res);
                        toast.success("Bibliothecaire ajouté avec succès")
                        setIsChanged(!isChanged)
                    })
                    .catch(err => {
                        console.log(err);
                        toast.error("Erreur lors de l'ajout du bibliothecaire : email déjà utilisé")
                    });
            }catch (e) {
                console.log(e)
            }
        }
    }

    return (
        <>
            <Button
                onPress={onOpen}
                onClick={() => {
                    onOpen()
                }}
                className="  bg-[#564592] text-background"
                endContent={<PlusIcon />}
                size="sm"
            >
                Ajouter Nouveau
            </Button>
            <Modal className="py-6"  isOpen={isOpen} scrollBehavior="inside" onOpenChange={onOpenChange}>
                <ModalContent className="text-black  " >
                    {(onClose) => (
                        <>
                            <ModalHeader className="flex flex-col font-semibold text-lg pb-6 gap-1">Nouveau Bibliothecaire</ModalHeader>
                            <ModalBody>
                                <form className="flex flex-col gap-2 justify-center w-96" onSubmit={handleSubmit}>
                                    <div className="flex gap-6 text-gray-400 text-sm items-center ">
                                        <p>Photo profile : </p>
                                        <div
                                            className="border relative cursor-pointer rounded-[100%] flex items-center justify-center  w-24 h-24 ">
                                            <input ref={imageUrl}  name="image" id="fileUpload" type="file"
                                                   onChange={(event)=>{
                                                         handleChange(event)
                                                   }}
                                                   className="  absolute  rounded-full  w-full h-full px-6 p-2 cursor-pointer " />
                                            <label className="relative cursor-pointer h-full w-full"
                                                   htmlFor="fileUpload">
                                                {
                                                    imageSrc ? <img src={imageSrc} alt="image"
                                                                    className="rounded-full w-full h-full" /> :
                                                        <Avatar className="w-full  h-full absolute" showFallback
                                                                src='https://images.unsplash.com/broken' fallback={
                                                            <CameraIcon
                                                                className="animate-pulse w-6 h-6 text-default-500"
                                                                fill="currentColor" size={20} />
                                                        } />

                                                }
                                            </label>

                                        </div>
                                    </div>
                                    {errors.nom && <p className="text-sm font-normal text-red-500">{errors.nom}</p>}
                                    <input onChange={() => setErrors({})}
                                               ref={nomRef} name="nom" type="text" placeholder="Nom"
                                           className="border p-2 focus:outline-none rounded text-gray-700 " />
                                    {errors.prenom && <p className="text-sm font-normal text-red-500">{errors.prenom}</p>}
                                    <input  onChange={() => setErrors({})}
                                               ref={prenomRef} name="prenom" type="text" placeholder="Prenom"
                                           className="border focus:outline-none p-2 rounded text-gray-700 " />
                                    {errors.adresse && <p className="text-sm font-normal text-red-500">{errors.adresse}</p>}
                                    <input   onChange={() => setErrors({})}
                                               ref={adresseRef} name="adresse" type="text" placeholder="Ville"
                                           className="border focus:outline-none p-2 rounded text-gray-700 " />
                                    {errors.naissance && <p className="text-sm font-normal text-red-500">{errors.naissance}</p>}
                                    <input  onChange={() => setErrors({})}
                                               ref={naissanceRef} name="naissance" type="date" placeholder="Naissance"
                                           className="border focus:outline-none p-2 text-gray-700  rounded" />
                                    {errors.email && <p className=" text-sm font-normal text-red-500">{errors.email}</p>}
                                    <input     onChange={() => setErrors({})}
                                               ref={emailRef} name="email" type="email" placeholder="Email"
                                           className="border focus:outline-none p-2 rounded text-gray-700 " />
                                    {errors.password && <p className="text-sm font-normal text-red-500">{errors.password}</p>}
                                    <input onChange={() => setErrors({})}
                                               ref={passwordRef} name="password" type="password" placeholder="Password"
                                           className="border focus:outline-none p-2 rounded text-gray-700 " />
                                    <button onClick={()=>{
                                        setTimeout(() => {
                                            onClose()
                                        }, 3000);
                                    }} className="bg-[#564592] text-white py-2 px-6 rounded-md" type="submit">Ajouter</button>
                                </form>
                            </ModalBody>
                        </>
                    )}
                </ModalContent>
            </Modal>
        </>
    );
}

export default AddBibliothecaire;