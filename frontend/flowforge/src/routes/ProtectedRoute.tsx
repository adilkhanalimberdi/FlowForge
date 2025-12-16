import React from "react";
import {useAuth} from "../context/AuthContext.tsx";
import {Navigate} from "react-router-dom";

export const ProtectedRoute = ({children}: { children: React.ReactNode }) => {
    const { token } = useAuth();
    if (!token) return <Navigate to="/login" replace/>;
    return children;
}