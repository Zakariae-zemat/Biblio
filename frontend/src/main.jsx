import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import {AuthProvider} from "./context/AuthProvider.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {NextUIProvider} from "@nextui-org/react";

const originalWarn = console.warn;
console.warn = (...args) => {

};
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <BrowserRouter>
          <NextUIProvider>
          <AuthProvider>
              <Routes>
                  <Route path="/*" element={<App />} />
              </Routes>
          </AuthProvider>
          </NextUIProvider>
      </BrowserRouter>
  </React.StrictMode>,
)
