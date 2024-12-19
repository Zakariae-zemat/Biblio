// App.js
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Acceuil from './components/Acceuil';
import MostPopulare from './pages/MostPopulare.jsx';
import Unauthorized from "./components/Unauthorized.jsx";
import Missing from "./components/Missing.jsx";
import RequireAuth from "./components/RequireAuth.jsx";
import ProfileDisplay from "./pages/ProfileDisplay.jsx";
import BookDetails from "./pages/BookDetails.jsx";
import Admin from "./pages/Admin.jsx";
import Documents from "./pages/Documents.jsx";
import Transactions from "./pages/Transactions.jsx";
import Statistiques from "./pages/Statistiques.jsx";

const App = () => {
    //const { username, role } = useContext(AuthContext);
    //console.log(username, role)
    const ROLES = {
        'User': 'CLIENT',
        'Editor': 'BIBLIOTHECAIRE',
        'Admin': 'ADMIN',
        'Guest': 'GUEST'
    }

    return (
                <div className="w-full md:px-6 sm:px-8 flex relative justify-center h-[100vh] ">
                    <div className="container d-flex align-items-center flex-column">
                        <Routes>
                            <Route path="unauthorized" element={<Unauthorized />} />
                            <Route element={<RequireAuth allowedRoles={ROLES.Guest} path="/" />}>
                                <Route exact path='/' element={<Acceuil/>}/>
                            </Route>
                            <Route element={<RequireAuth allowedRoles={ROLES.User} path="mostPopulaire/profile"/>}>
                                <Route exact path='mostPopulaire/profile' element={<ProfileDisplay/>}/>
                            </Route>
                            <Route element={<RequireAuth allowedRoles={ROLES.User} path="/mostPopulaire"/>}>
                                <Route exact path='/mostPopulaire' element={<MostPopulare/>}/>
                                <Route path={"/bookDetails/:idBook"} element={<BookDetails/>} />
                            </Route>
                            <Route exact path='/profile' element={<ProfileDisplay/>}/>
                            <Route element={<RequireAuth allowedRoles={ROLES.Admin} path="/admin"/>}>
                                <Route path="/admin" element={<Admin/>}/>
                            </Route>
                            <Route path="*" element={<Missing />} />
                            <Route element={<RequireAuth allowedRoles={ROLES.Editor} path="/mostPopulaire"/>}>
                                <Route path="/documents" element={<Documents/>} />
                                <Route path="/transactions" element={<Transactions />} />
                                <Route path="/statistiques" element={<Statistiques />} />
                                <Route exact path='/profile' element={<ProfileDisplay/>}/>
                            </Route>
                        </Routes>
                    </div>
                </div>
    );
};

export default App;
