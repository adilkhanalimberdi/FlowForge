import WorkspacePage from "../workspace/WorkspacePage.tsx";
import type {Workspace} from "../../types/Workspace.ts";
import React, {useEffect, useState} from "react";
import {workspaceService} from "../../services/workspaceService.ts";
import type {Card} from "../../types/card/Card.ts";
import {useNavigate, useParams} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLeftLong, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import Calendar from "../../components/Calendar.tsx";
import {cardService} from "../../services/cardService.ts";


function sortByStatus(cards: Card[]) {
    const newCards: Card[] = [];
    for (const card of cards) {
        if (card.status === "NOT_STARTED") newCards.push(card);
    }
    for (const card of cards) {
        if (card.status === "IN_PROGRESS") newCards.push(card);
    }
    for (const card of cards) {
        if (card.status === "COMPLETED") newCards.push(card);
    }

    return newCards;
}


function DashboardPage() {
    const {workspaceId} = useParams();
    const [workspace, setWorkspace] = useState<Workspace | null>(null);
    const [canSave, setCanSave] = useState<boolean>(false);
    const [saving, setSaving] = useState<boolean>(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (!workspaceId) return;
        workspaceService.getById(Number.parseInt(workspaceId))
            .then(setWorkspace)
            .catch(err => {
                if (err.status === 403) {
                    navigate("/login");
                    return;
                }
            });
    }, [workspaceId]);

    useEffect(() => {
        const button = document.querySelector("#save-button") as HTMLButtonElement;
        if (!button) return;

        if (canSave) {
            button.classList.remove("disab");
            setSaving(false);
        } else {
            button.classList.add("disab");
        }
    }, [canSave])

    const handleSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        // const element = event.target as HTMLButtonElement;
        event.preventDefault();
        if (!canSave) return;

        setSaving(true);
        setCanSave(false);

        toast.success("Successfully saved!", {
            position: "bottom-right",
            autoClose: 3000,
        });
    }

    const handleCreateCard = async () => {
        if (!workspace) return;

        try {
            const newCard = await cardService.createDefault(workspace.id);

            setWorkspace(prev => {
                if (!prev) return prev;

                // @ts-ignore
                const updatedCards: Card[] = [...prev.cards, newCard];

                return {
                    ...prev,
                    cards: updatedCards
                } as Workspace;
            });

            setCanSave(true);

            toast.success("Card created successfully!", {
                position: "bottom-right",
                autoClose: 3000,
            });

        } catch (err: any) {
            console.error(err);
            toast.error("Failed to create card: " + (err.message || err), {
                position: "bottom-right",
                autoClose: 3000,
            });
        }
    };

    const handleDelete = async () => {
        if (!workspace) return;
        await workspaceService.delete(workspace.id);
        navigate("/dashboard");
    }


    if (!workspace) return null;

    return (
        <div className="pl-30 pr-30 pt-5">
            <div className="">
                <div className="flex justify-between items-center">
                    <div className="flex flex-row gap-2">
                        <button className="bg-blueviolet-500 hover:bg-blueviolet-600 hover:cursor-pointer active:bg-blueviolet-700 text-white pl-4 pr-4 pt-2 pb-2 rounded-[5px] mb-5"
                                onClick={() => {
                                    navigate("/dashboard");
                                }}>
                            {/*<FontAwesomeIcon icon={faHouse} />*/}
                            <FontAwesomeIcon icon={faLeftLong} />
                        </button>
                        <button className="bg-blueviolet-500 hover:bg-blueviolet-600 hover:cursor-pointer active:bg-blueviolet-700 text-white pl-4 pr-4 pt-2 pb-2 rounded-[5px] mb-5"
                                onClick={handleDelete}>
                            <FontAwesomeIcon icon={faTrashCan} />
                        </button>
                    </div>

                    <form action="" className="items-center">
                        <button id="save-button"
                                type="submit"
                                className={"hover:cursor-pointer hover:bg-blueviolet-600 rounded-[5px] align-text-top pl-5 pr-5 pt-2 pb-2 block bg-blueviolet-500 text-white disab"}
                                onClick={handleSave}>
                            Save</button>
                    </form>
                </div>

                <div className="mb-2">
                    <label htmlFor="workspace-title" className="text-lg text-gray-700">Workspace Title: </label>
                    <div className="flex" id="workspace-title">
                        <input
                            type="text"
                            className="workspace-title border p-3 w-[500px] rounded-[5px] mr-5 border-gray-500"
                            defaultValue={workspace.title}
                        />
                        <button className="pl-4 pr-4 pt-2 pb-2 rounded-[5px] bg-blueviolet-500 hover:bg-blueviolet-600 active:bg-blueviolet-700 text-white text-md hover:cursor-pointer mr-4"
                                onClick={ async () => {
                                    const title = (document.querySelector(".workspace-title") as HTMLInputElement).value;
                                    await workspaceService.update(workspace.id, title)
                                        .then(() => {
                                            toast.success("Workspace updated successfully!", {
                                                position: "bottom-right",
                                                autoClose: 3000
                                            });
                                        })
                                        .catch((e) => {
                                            toast.error(e.message, {
                                                position: "bottom-right",
                                                autoClose: 3000
                                            });
                                        });
                                }}>
                            {/*<FontAwesomeIcon icon={faCheck} />*/}
                            {/*<FontAwesomeIcon icon={faFloppyDisk} />*/}
                            Ok
                        </button>
                        <button
                            className="pl-4 pr-4 pt-2 pb-2 rounded-[5px] bg-blueviolet-500 hover:bg-blueviolet-600 active:bg-blueviolet-700 text-white text-md hover:cursor-pointer"
                            onClick={handleCreateCard}>
                            Create Card
                        </button>
                    </div>
                </div>

                <div className="flex justify-between">
                    <div className="flex justify-between items-end">
                        <h1 className="text-lg text-gray-700">Your Cards: </h1>
                    </div>
                </div>

                <div className="flex items-center">

                    <div className="cards-container flex flex-wrap gap-5 items-center justify-center lg:justify-start">

                        {[...sortByStatus(workspace.cards)].map((card) => (
                            <WorkspacePage
                                card={card}
                                key={card.id}
                                setCanSave={setCanSave}
                                saving={saving}
                                setSaving={setSaving}
                            />
                        ))}

                        {sortByStatus(workspace.cards).length === 0 && (
                            <div>
                                <h1 className="text-gray-600 text-xl p-3">You don't have any card.</h1>
                            </div>
                        )}

                    </div>

                </div>

                <div className="flex justify-center items-center pt-10 pb-50">
                    <div className="w-[800px]">
                        <Calendar />
                    </div>
                </div>

            </div>
        </div>
    )
}

export default DashboardPage;