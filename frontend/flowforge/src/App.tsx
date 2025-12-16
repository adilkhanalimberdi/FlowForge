import Header from "./components/Header";
import {useEffect, useState} from "react";
import type {User} from "./types/User.ts";
import {userService} from "./services/userService.ts";


function App() {
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        userService.getById(1).then(setUser);
    }, []);

    if (!user) return null;

    return (
        <>
            <Header user={user} />
        </>
    );
}

export default App;