import {useAuth} from "../context/AuthContext.tsx";
import {Navigate, Outlet} from "react-router-dom";
import Header from "./Header.tsx";

export const PrivateLayout = () => {
    const {token, user} = useAuth();

    if (!token) {
        return <Navigate to="/login" />;
    }

    return (
        <div>
            <Header user={user} />
            <main>
                <Outlet />
            </main>
        </div>
    );
}