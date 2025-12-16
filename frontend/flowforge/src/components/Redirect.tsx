import {Navigate} from 'react-router-dom';
import {useAuth} from "../context/AuthContext.tsx";
import {useEffect, useState} from "react";
import {userService} from "../services/userService.ts";

function Redirect() {
    const { token, logout } = useAuth();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const checkAuth = async () => {
            if (!token) {
                setLoading(false);
                return;
            }

            try {
                const me = await userService.getCurrent();
                localStorage.setItem("user", JSON.stringify(me));
            } catch (err) {
                logout();
            } finally {
                setLoading(false);
            }
        };

        checkAuth();
    }, [token]);


    if (loading) return <div>Loading...</div>;
    return token ? <Navigate to="/dashboard/" replace /> : <Navigate to="/login" replace />;
}

export default Redirect;