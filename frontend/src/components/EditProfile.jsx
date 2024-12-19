import React, {useEffect, useRef, useState} from 'react';
import { Avatar, Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader, useDisclosure } from "@nextui-org/react";
import * as yup from 'yup';
import toast from "react-hot-toast";
import {MdSettings} from "react-icons/md";
import {CameraIcon} from "./utils/CameraIcon.jsx";
import useAuth from "../hook/useAuth.js";
import axios from "../api/axios.js";

const EditProfile = ({user}) => {
    const handleChangee = (event) => {
        setValue(event.target.value);
    };
    const {auth} = useAuth();
    const imageUrl = useRef();
    const nomRef = useRef();
    const cinRef = useRef();
    const prenomRef = useRef();
    const adresseRef = useRef();
    const naissanceRef = useRef();
    const telephoneRef = useRef();
    const [imageSrc, setImageSrc] = useState(user.imageUrl);
    const [errors, setErrors] = useState({});
    const schema = yup.object().shape({
        imageUrl: yup.mixed(),
        nom: yup.string(),
        prenom: yup.string(),
        adresse: yup.string(),
        telephone: yup.string(),
        naissance: yup.date("date invalid"),
        cin: yup.string(),
        email: yup.string().email('Email invalide'),
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
            nom: nomRef.current.value ? nomRef.current.value : user.nom ,
            prenom: prenomRef.current.value ? prenomRef.current.value : user.prenom,
            adresse: adresseRef.current.value ? adresseRef.current.value : user.addresse,
            naissance: naissanceRef.current.value ? naissanceRef.current.value : user.naissance,
            telephone : telephoneRef.current.value ? telephoneRef.current.value : user.telephone,
            cin: cinRef.current.value ? cinRef.current.value : user.cin,
            imageUrl: imageUrll ? imageUrll : user.imageUrl,
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
                axios.put('/users?email='+auth.email, data, {
                    headers: {
                        "Authorization": "Bearer " + auth.token,
                    }
                })
                    .then(res => {
                        console.log(res);
                        toast.success("Profile Modifier avec succès")
                    })
                    .catch(err => {
                        console.log(err);
                        toast.error("Erreur lors de la modification du Modification du profile")
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
                className="flex cursor-pointer hover:bg-gray-300 bg-white rounded-none justify-start  text-[14px]  p-2 font-medium  items-center px-6 gap-2  ">
                <MdSettings className="text-[18px] "/>
                <p>Manage Votre Acount</p>
            </Button>
            <div
               >

            </div>
            <Modal className="py-6" isOpen={isOpen}  onOpenChange={onOpenChange}>
                <ModalContent classNaitems-leftme="text-black  ">
                    {(onClose) => (
                        <>
                            <ModalHeader className="flex flex-col text-black font-semibold text-lg pb-6 gap-1">Modifier
                                Profile</ModalHeader>
                            <ModalBody>
                                <form className="flex flex-col gap-2 justify-center w-96" onSubmit={handleSubmit}>
                                    <div className="flex gap-6 text-gray-400 text-sm items-center ">
                                        <p>Photo profile : </p>
                                        <div
                                            className="border relative cursor-pointer rounded-[100%] flex items-center justify-center  w-24 h-24 ">
                                            <input ref={imageUrl} name="image" id="fileUpload" type="file"
                                                   onChange={(event) => {
                                                       handleChange(event)
                                                   }}
                                                   className="  absolute  rounded-full  w-full h-full px-6 p-2 cursor-pointer "/>
                                            <label className="relative cursor-pointer h-full w-full"
                                                   htmlFor="fileUpload">
                                                {
                                                    imageSrc ? <img src={imageSrc} alt="image"
                                                                    className="rounded-full w-full h-full"/> :
                                                        <Avatar className="w-full  h-full absolute" showFallback
                                                                src='https://images.unsplash.com/broken' fallback={
                                                            <CameraIcon
                                                                className="animate-pulse w-6 h-6 text-default-500"
                                                                fill="currentColor" size={20}/>
                                                        }/>

                                                }
                                            </label>

                                        </div>
                                    </div>
                                    {errors.nom && <p className="text-sm font-normal text-red-500">{errors.nom}</p>}
                                    <input onChange={() => setErrors({})}
                                           ref={nomRef} name="nom" type="text" placeholder={user.nom ? user.nom : "Nom"}
                                           className="border p-2 focus:outline-none rounded text-gray-700 "/>
                                    {errors.prenom &&
                                        <p className="text-sm font-normal text-red-500">{errors.prenom}</p>}
                                    <input onChange={() => setErrors({})}
                                           ref={prenomRef} name="prenom" type="text"
                                           placeholder={user.prenom ? user.prenom : "Prenom"}
                                           className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                    <p className="text-sm font-normal text-red-500">{errors.cin}</p>
                                    <input onChange={() => setErrors({})}
                                           ref={cinRef} name="cin" type="text"
                                           placeholder={user.cin ? user.cin : "Cin"}
                                           className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                    {errors.adresse &&
                                        <p className="text-sm font-normal text-red-500">{errors.adresse}</p>}
                                    <input onChange={() => setErrors({})}
                                           ref={adresseRef} name="adresse" type="text"
                                           placeholder={user.addresse ? user.addresse : "Ville"}
                                           className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                    {errors.naissance &&
                                        <p className="text-sm font-normal text-red-500">{errors.naissance}</p>}
                                    <input onChange={() => setErrors({})}
                                           ref={naissanceRef} name="naissance" type="date"
                                           placeholder={user?.naissance ? user?.naissance : "Naissance"}
                                           className="border focus:outline-none p-2 text-gray-700  rounded"/>
                                    {errors.telephone &&
                                        <p className="text-sm font-normal text-red-500">{errors.telephone}</p>}
                                    <input onChange={() => setErrors({})}
                                           ref={telephoneRef}
                                           value={user?.telephone ? user?.telephone : null}
                                           name="telephone" type="text"
                                           placeholder={user?.telephone ? user.telephone : "Telephone"}
                                           className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                    <input onChange={() => setErrors({})}
                                           disabled={true}
                                           value={user.email}
                                           name="email" type="email" placeholder={user.email}
                                           className="border focus:outline-none p-2 rounded text-gray-700 "/>
                                    <button onClick={() => {
                                        setTimeout(() => {
                                            onClose()
                                        }, 3000);
                                    }} className="bg-[#564592] text-white py-2 px-6 rounded-md" type="submit">Ajouter
                                    </button>
                                </form>
                            </ModalBody>
                        </>
                    )}
                </ModalContent>
            </Modal>
        </>
    );
}

export default EditProfile;