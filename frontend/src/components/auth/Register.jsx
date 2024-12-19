import React, {useContext, useState} from 'react';
import { Formik, Field, Form, ErrorMessage } from 'formik';
import * as yup from 'yup';
import axios from '../../api/axios.js';
import {isActiveLogin, isActiveRegister} from "../../context/GlobalProvider.jsx";
import {useAtom} from "jotai";
import AuthContext from "../../context/AuthProvider.jsx";
import {useNavigate} from "react-router-dom";

const Register = (props) => {axios
    const [isActiveLoged, setIsActiveLogin] = useAtom(isActiveLogin);
    const [isActiveRegisted, setIsActiveRegister] = useAtom(isActiveRegister);
    const [er,setError] = useState("")
    const { setAuth } = useContext(AuthContext);
    const [isLoading, setIsLoading] = useState(false)
    const navigation = useNavigate()
    // Create a schema for validation
    const schema = yup.object().shape({
        nom: yup.string().required('Required'),
        prenom: yup.string().required('Required'),
        addresse : yup.string().required('Required'),
        naissance: yup.date().required('Required'),
        email: yup.string().email('Invalid email').required('Required'),
        password: yup.string().min(8, 'Must be at least 8 characters').required('Required'),
        confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords must match').required('Required')
    });
    const handleSubmit = async (values) => {
        setIsLoading(true)
        try {
            // Send a POST request to the /register endpoint
            const response = await axios.post('/auth/register', values);
            setIsLoading(false)
            setIsActiveRegister(false)
            setIsActiveLogin(true)
        } catch (e) {
            if (e.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                setError(e.response.data.error);
            } else if (e.request) {
                // The request was made but no response was received
                // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                // http.ClientRequest in node.js
                console.log(e.request);
            } else {
                // Something happened in setting up the request that triggered an Error
                console.log('Error', e.message);
            }
            console.log(e.config);
        }

    };

    return (
        <div className="bg-white  font-medium flex flex-col justify-center gap-4  text-black bg-opacity-90  rounded-xl py-5 px-6">
            <h1 className="text-xl text-center ">S'inscrire</h1>
            {er && <p className="text-left text-red-600 text-sm font-normal">{er}</p>}
            <Formik
                initialValues={{ nom: '', prenom: '',addresse: '', naissance: '', email: '', password: '', confirmPassword: '' }}
                validationSchema={schema}
                onSubmit={handleSubmit}
            >
                <Form className="flex flex-col gap-2 w-96">
                    <ErrorMessage name="nom" component="div" className="text-red-600 text-[12px] font-normal text-left " />
                    <Field name="nom" type="text" placeholder="Nom" className="border p-2 focus:outline-none rounded text-gray-700 " />
                    <ErrorMessage name="prenom" component="div" className="text-red-600  text-[12px] font-normal text-left" />
                    <Field name="prenom" type="text" placeholder="Prenom" className="border focus:outline-none p-2 rounded text-gray-700 " />
                    <ErrorMessage name="adresse" component="div" className="text-red-600  text-[12px] font-normal text-left" />
                    <Field name="addresse" type="text" placeholder="Ville" className="border focus:outline-none p-2 rounded text-gray-700 " />
                    <ErrorMessage name="naissance" component="div" className="text-red-600  text-[12px] font-normal text-left" />
                    <Field name="naissance" type="date" placeholder="Naissance" className="border focus:outline-none p-2 text-gray-700  rounded" />
                    <ErrorMessage name="email" component="div" className="text-red-600  text-[12px] font-normal text-left" />
                    <Field name="email" type="email" placeholder="Email" className="border focus:outline-none p-2 rounded text-gray-700 " />
                    <ErrorMessage name="password" component="div" className="text-red-600  text-[12px] font-normal text-left" />
                    <Field name="password" type="password" placeholder="Password" className="border focus:outline-none p-2 rounded text-gray-700 " />
                    <ErrorMessage name="confirmPassword" component="div" className="text-red-600 text-[12px] font-normal text-left" />
                    <Field name="confirmPassword" type="password" placeholder="Confirm Password" className="border focus:outline-none p-2 rounded text-gray-700 " />
                    {
                        !isLoading ? (
                            <button type="submit" className="bg-[#564592] text-white py-2 px-6 rounded-md">Register</button>
                        ) : (
                            <button disabled type="button"
                                    className="bg-[#564592] text-white py-2 px-6 rounded-md">
                                <svg aria-hidden="true" role="status"
                                     className="inline w-4 h-4 me-3 text-white animate-spin"
                                     viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path
                                        d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                        fill="#E5E7EB"/>
                                    <path
                                        d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                        fill="currentColor"/>
                                </svg>
                                Loading...
                            </button>
                        )
                    }
                </Form>
            </Formik>
            <div>
                <p className="text-sm font-normal">Vous avez déjà un compte ? <span
                    className="text-[#564592] cursor-pointer" onClick={() => {
                    setIsActiveLogin(true)
                    setIsActiveRegister(false)
                }
                }>Se Connecter</span></p>
            </div>
        </div>
    )
}

export default Register;