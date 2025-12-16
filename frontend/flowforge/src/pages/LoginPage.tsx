import {useAuth} from "../context/AuthContext.tsx";
import {login} from "../api/auth.ts";
import React, {useEffect, useState} from "react";
import {Navigate, useNavigate} from "react-router-dom";
import logo from "../assets/logo.png";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<String | null>(null);
    const navigate = useNavigate();
    const {login: setAuth, token} = useAuth();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        if (!email || !password) {
            setError("Invalid email or password");
            return;
        }

        try {
            const data = await login(email, password);
            setAuth(data.token, data.user);
            navigate(`/dashboard/`);
        } catch (error) {
            setError("Something went wrong");
            console.log(error);
        }
    }

    useEffect(() => {
        if (error) {
            const timer = setTimeout(() => setError(null), 2500);
            return () => clearTimeout(timer);
        }
    }, [error]);

    if (token) {
        return <Navigate to={`/dashboard/`} />;
    }

    return (
        <div className="fixed h-full w-full flex justify-center items-center bg-gray-200">
            <form onSubmit={handleSubmit} className="h-[500px] w-[600px] flex justify-center items-center rounded-xl bg-white shadow-black drop-shadow-2xl">
                <div className="flex flex-col justify-center items-center w-[70%] h-full gap-3">

                    <div className="flex flex-col justify-center items-center gap-5">
                        <img src={logo} alt="logo" className="logo w-[50px] h-[50px] object-cover bg-cover block scale-300"/>
                        <h1 className="text-gray-700 text-2xl">Welcome Back!</h1>
                    </div>
                    <input
                        type="email"
                        placeholder="Email"
                        className="border w-full p-3 rounded-xl border-gray-500"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        id="password-input"
                        type="password"
                        placeholder="Password"
                        className="border w-full p-3 rounded-xl border-gray-500"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <div className="w-full select-none text-gray-600 flex justify-between">
                        <div>
                            <input
                                id="see-password-checkbox"
                                type="checkbox"
                                className="mr-2"
                                onChange={(e) => {
                                    const element = document.querySelector('#password-input');
                                    if (e.target.checked) {
                                        element?.setAttribute("type", "text");
                                    } else {
                                        element?.setAttribute("type", "password");
                                    }}}
                            />
                            <label htmlFor="see-password-checkbox">Show password</label>
                        </div>

                        <h1 className="">
                            New here?
                            <span> </span>
                            <a href="/register" className="text-blue-500">Join the Flow</a>
                        </h1>
                    </div>

                    <div className="w-full flex">
                        <div className="w-[50%] flex justify-center items-center text-red-500">
                            <h1 className="w-[90%]">{error}</h1>
                        </div>
                        <button type="submit"
                                className="border w-[50%] p-3 rounded-xl text-white bg-blueviolet-500 hover:cursor-pointer hover:bg-blueviolet-600 active:bg-blueviolet-700 duration-100">Log in</button>
                    </div>

                </div>
            </form>
        </div>
    )
}