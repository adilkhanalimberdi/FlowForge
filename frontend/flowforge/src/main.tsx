import './index.css';
// import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import UserDashboardPage from "./pages/dashboard/UserDashboard.tsx";
import {ProtectedRoute} from "./routes/ProtectedRoute.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import {AuthProvider} from "./context/AuthContext.tsx";
import Redirect from "./components/Redirect.tsx";
import RegisterPage from "./pages/RegisterPage.tsx";
import DashboardPage from "./pages/dashboard/DashboardPage.tsx";
import {PrivateLayout} from "./components/PrivateLayout.tsx";
import CardPage from "./pages/card/CardPage.tsx";

createRoot(document.getElementById('root')!).render(
  // <StrictMode>
    <AuthProvider>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Redirect />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route element={<PrivateLayout />}>
                    <Route path="/dashboard/" element={
                        <ProtectedRoute>
                            <UserDashboardPage />
                        </ProtectedRoute>
                    } />
                    <Route path="/dashboard/:workspaceId" element={<DashboardPage />} />
                    <Route path="/dashboard/:workspaceId/card/:cardId" element={<CardPage />} />
                </Route>
            </Routes>
        </BrowserRouter>
    </AuthProvider>
  // </StrictMode>,
)
