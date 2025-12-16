import React, {useEffect, useState} from "react";
import {register} from "../api/auth.ts";
import {Navigate, useNavigate} from "react-router-dom";
import logo from "../assets/logo.png";
import {useAuth} from "../context/AuthContext.tsx";

export default function RegisterPage() {
    const [firstName, setFirstName] = useState<string>('');
    const [lastName, setLastName] = useState<string>('');
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<String>("");
    const navigate = useNavigate();
    const {token} = useAuth();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        if (!email || !password) {
            setError("Invalid email or password");
            return;
        }

        if (password.length < 6) {
            setError("Password must be at least 6 characters");
            return;
        }

        if (firstName.trim().length > 6) {}

        try {
            const data = await register(firstName, lastName, email, password);
            console.log(data);
            navigate("/login");
        } catch (error) {
            setError("Something went wrong");
            console.log(error);
        }
    }

    useEffect(() => {
        if (error) {
            const timer = setTimeout(() => setError(""), 2500);
            return () => clearTimeout(timer);
        }
    }, [error])

    if (token) {
        return <Navigate to={`/dashboard/`} />;
    }

    return (
        <div className="fixed h-full w-full flex justify-center items-center bg-gray-200">
            <form onSubmit={handleSubmit} className="h-[600px] w-[600px] flex justify-center items-center bg-white rounded-xl shadow-black drop-shadow-2xl">
                <div className="flex flex-col justify-center items-center w-[70%] h-full gap-3">

                    <div className="flex flex-col justify-center items-center gap-5">
                        <img src={logo} alt="logo" className="logo w-[50px] h-[50px] object-cover bg-cover scale-300"/>
                        <h1 className="text-gray-700 text-2xl">Let’s get you started!</h1>
                    </div>
                    <input
                        type="text"
                        placeholder="First Name"
                        className="border w-full p-3 rounded-xl border-gray-500"
                        value={firstName}
                        required={true}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Last Name"
                        className="border w-full p-3 rounded-xl border-gray-500"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        className="border w-full p-3 rounded-xl border-gray-500"
                        value={email}
                        required={true}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        id="password-input"
                        type="password"
                        placeholder="Password"
                        className="border w-full p-3 rounded-xl border-gray-500"
                        value={password}
                        required={true}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <div className="w-full select-none text-gray-600">
                        <div className="flex justify-between">
                            <div>
                                <input
                                    id="see-password-checkbox"
                                    type="checkbox"
                                    className="mr-2"
                                    onChange={(e) => {
                                        const element = document.querySelector("#password-input");
                                        if (e.target.checked) {
                                            element?.setAttribute("type", "text");
                                        } else {
                                            element?.setAttribute("type", "password");
                                        }}}
                                />
                                <label htmlFor="see-password-checkbox">Show password</label>
                            </div>

                            <h1>
                                Already registered?
                                <span> </span>
                                <a href="/login" className="text-blue-500">Back to Login</a>
                            </h1>
                        </div>
                        <div className="w-full h-[20px] text-red-600">
                            <h1>{error}</h1>
                        </div>
                    </div>
                    <div className="w-full flex">
                        <div className="w-[50%]"></div>
                        <button type="submit"
                            className="w-[50%] p-3 rounded-xl text-white bg-blueviolet-500 hover:cursor-pointer hover:bg-blueviolet-600 active:bg-blueviolet-700 duration-100">Sign up</button>
                    </div>

                </div>
            </form>
        </div>
    )
}