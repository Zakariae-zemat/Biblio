import axios from 'axios';

export default axios.create({
    baseURL: 'https://library-1-xpel.onrender.com'
});

export const axiosPrivate = axios.create({
    baseURL: "https://library-1-xpel.onrender.com",
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
});