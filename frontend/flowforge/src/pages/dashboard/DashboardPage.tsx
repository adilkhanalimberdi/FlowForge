import WorkspacePage from "../workspace/WorkspacePage.tsx";
import type {Workspace} from "../../types/Workspace.ts";
import React, {useEffect, useState} from "react";
import {workspaceService} from "../../services/workspaceService.ts";
import Message from "../../components/Message.tsx";

interface Props {
    workspace: Workspace;
}


function DashboardPage({workspace}: Props) {
    const [canSave, setCanSave] = useState<boolean>(false);
    const [saving, setSaving] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");
    const [showMessage, setShowMessage] = useState<boolean>(false);

    const handleSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        // const element = event.target as HTMLButtonElement;
        event.preventDefault();

        if (!canSave) {
            return;
        }

        setSaving(true);
        setCanSave(false);

        setMessage("Saved Successfully!");
        setShowMessage(true);
    }

    useEffect(() => {
        const button = document.querySelector(".save-button") as HTMLButtonElement;

        if (canSave) {
            button.classList.remove("disab");
            setSaving(false);
        } else {
            button.classList.add("disab");
        }
    }, [canSave])

    return (
        <div className="p-10">
            <div className="">

                <div>
                    <button className="bg-blue-500 hover:bg-blue-600 hover:cursor-pointer text-white pl-4 pr-4 pt-2 pb-2 rounded-[5px] mb-5">Back</button>
                </div>

                <div className="mb-5">
                    <div className="pb-3">
                        <label htmlFor="workspace-title" className="text-2xl text-gray-600">Workspace Title: </label>
                    </div>
                    <div className="flex">
                        <input id="workspace-title" type="text" className="border pl-3 pr-3 w-[500px] text-xl pt-2 pb-2 rounded-[5px] mr-5" defaultValue={workspace.title}/>
                        <button className="pl-4 pr-4 pt-2 pb-2 rounded-[5px] bg-blue-500 hover:bg-blue-600 text-white text-md hover:cursor-pointer"
                                onClick={ async () => {
                                    const newWorkspace: Workspace = {
                                        id: workspace.id,
                                        title: (document.querySelector("#workspace-title") as HTMLInputElement).value,
                                        user: workspace.user,
                                        cards: workspace.cards
                                    };

                                    await workspaceService.update(workspace.id, newWorkspace);

                                    setMessage("Title updated!");
                                    setShowMessage(true);
                                }}>
                            Ok</button>
                    </div>
                </div>

                <div className=" flex justify-between">
                    <h1 className="text-2xl text-gray-600 pb-5">Your Cards: </h1>

                    <form action="" className="items-center">
                        <button type="submit"
                                className={"save-button hover:cursor-pointer hover:bg-blue-600 rounded-[5px] align-text-top pl-5 pr-5 pt-2 pb-2 block bg-blue-500 text-white disab"}
                                onClick={handleSave}>
                            Save</button>
                    </form>
                </div>

                <div className="flex items-center">

                    <div className="cards-container flex flex-wrap gap-5 items-center justify-center lg:justify-start">

                        {workspace.cards.map((card) => (
                            <WorkspacePage card={card} key={card.id} setCanSave={setCanSave} saving={saving} />
                        ))}

                    </div>

                </div>

            </div>

            {
                showMessage &&
                <Message message={message} onClose={() => {
                    setShowMessage(false);
                    setMessage("");
                }} />
            }
        </div>
    )
}

export default DashboardPage;