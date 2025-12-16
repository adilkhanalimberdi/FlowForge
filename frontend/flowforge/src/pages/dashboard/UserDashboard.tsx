import {useEffect, useState} from "react";
import {userService} from "../../services/userService.ts";
import type {User} from "../../types/User.ts";
// import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
// import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {useAuth} from "../../context/AuthContext.tsx";
import {useNavigate} from "react-router-dom";
import {workspaceService} from "../../services/workspaceService.ts";


function UserDashboardPage() {
    const [user, setUser] = useState<User | null>(null);
    const { logout } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        let isMounted = true;

        userService.getCurrent()
            .then(user => {
                if (isMounted) setUser(user);
            })
            .catch(err => {
                if (isMounted && err.status === 403) {
                    setTimeout(() => {
                        logout();
                        navigate("/login");
                    }, 0);
                }
            });

        return () => { isMounted = false; };
    }, []);

    if (!user) return null;

    return (
        <div className="p-5 pl-30 pr-30">
            <button onClick={logout} className="hover:cursor-pointer">Logout</button>

            <div className="w-full pt-10">

                <div className="text-xl text-gray-600 pb-5">Your Workspaces:</div>

                <div className="flex flex-col">
                    {user.workspaces.map((workspace) => (
                        <div className="flex flex-row items-center gap-2" key={workspace.id}>
                            <div className="workspace p-3 w-[60%] h-[50px] min-w-[300px] rounded-xl hover:cursor-pointer hover:bg-blueviolet-100 duration-200 active:bg-blueviolet-200 select-none"
                                key={workspace.id}
                                onClick={() => {
                                    navigate(`/dashboard/${workspace.id}`);
                                }}
                                data-id={workspace.id}>
                                <div className="h-full flex flex-row justify-start items-center">
                                    <div className="align-middle text-gray-700 text-md h-max">{workspace.title}</div>
                                </div>
                            </div>
                            {/*<div className="bg-red-300 hover:bg-red-400 hover:cursor-pointer active:bg-red-500 duration-200 h-[50px] flex justify-center items-center p-3 rounded-xl text-md min-w-[60px] text-red-900">*/}
                            {/*    <FontAwesomeIcon icon={faTrashCan} />*/}
                            {/*</div>*/}
                        </div>
                    ))}

                    {user.workspaces.length === 0 && (
                        <div className="flex flex-col items-center justify-center mt-20 text-center">
                            <div className="text-6xl mb-4">📭</div>

                            <h2 className="text-xl font-semibold text-gray-700">
                                You don’t have any workspace yet
                            </h2>

                            <p className="text-gray-500 mt-2 max-w-md">
                                Looks empty here. Start by creating your first workspace and organize your work.
                            </p>

                            <button
                                className="mt-6 px-5 py-2 rounded-md bg-blueviolet-500 text-white
               hover:bg-blueviolet-600 active:bg-blueviolet-700 transition"
                                onClick={ async (e) => {
                                    e.preventDefault();
                                    await workspaceService.createDefault(user.id);
                                    window.location.reload();
                                }}
                            >
                                Create your first workspace
                            </button>
                        </div>
                    )}
                </div>

            </div>
        </div>
    );
}

export default UserDashboardPage;