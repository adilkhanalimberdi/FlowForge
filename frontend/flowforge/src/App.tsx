import './index.css';

import Header from "./components/Header";
import {useEffect, useState} from "react";
import type {User} from "./types/User.ts";
import {userService} from "./services/userService.ts";
import DashboardPage from "./pages/dashboard/DashboardPage.tsx";
import {workspaceService} from "./services/workspaceService.ts";
import type {Workspace} from "./types/Workspace.ts";


function App() {
    const [user, setUser] = useState<User | null>(null);
    const [workspace, setWorkspace] = useState<Workspace | null>(null);

    useEffect(() => {
        userService.getById(1).then(setUser);

        workspaceService.getById(1).then(setWorkspace);
    }, []);

    if (!user || !workspace) {
        return (
            <>
                <h2 className="text-red-500 text-4xl">Error</h2>
            </>
        )
    }

    return (
        <>
            <Header user={user} />
            <DashboardPage workspace={workspace} />
        </>
    );
}

export default App;