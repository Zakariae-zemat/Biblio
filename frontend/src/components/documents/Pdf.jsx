import { jsPDF } from "jspdf";
import html2canvas from 'html2canvas';
import axios from "axios";

const getImageData = async (imageUrl) => {
    const response = await axios.get(imageUrl, { responseType: 'arraybuffer' });
    const base64 = btoa(
        new Uint8Array(response.data)
            .reduce((data, byte) => data + String.fromCharCode(byte), '')
    );
    return `data:image/jpeg;base64,${base64}`;
}

const Pdf = async (componentId) => {
    const input = document.getElementById(componentId);
    const canvas = await html2canvas(input);
    const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF();
    pdf.addImage(imgData, 'JPEG', 0, 0);
    if(componentId === "carte") {
        const imageUrl = document.getElementById("profile").getElementsByTagName("img")[0].src;
        const imageData = await getImageData(imageUrl);
        pdf.addImage(imageData, 'JPEG', 85, 18 , 20, 20);
    }
    pdf.save("download.pdf");
}

export default Pdf