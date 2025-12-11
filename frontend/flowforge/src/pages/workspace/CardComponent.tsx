import React, {type Dispatch, type SetStateAction, useEffect} from "react";
import type {Card} from "../../types/Card.ts";
import {taskService} from "../../services/taskService.ts";
import {cardService} from "../../services/cardService.ts";

interface Props {
    before: Card;
    after: Card;
    setCanSave: Dispatch<SetStateAction<boolean>>;
    saving: boolean;
    setAfter: Dispatch<SetStateAction<Card>>;
}


function formatStatus(status: string) {
    const parts = status.split("_");
    let result = "";

    parts.forEach(part => {
        result += part.charAt(0) + part.toLowerCase().slice(1) + " ";
    });

    return result;
}


function CardComponent({before, after, setCanSave, saving, setAfter}: Props) {
    const handleCardClick = (event: React.MouseEvent<HTMLDivElement>) => {
        const element = event.target as HTMLElement;
        const elementName = element.nodeName;

        if (elementName === "LABEL" || elementName === "INPUT" || elementName === "BUTTON") {
            return;
        }

        console.log(elementName + " clicked!");
    };

    const handleToggle = (event: React.ChangeEvent<HTMLInputElement>) => {
        const element = event.target as HTMLInputElement;
        const elementId = Number.parseInt(element.id);

        for (let i = 0; i < after.tasks.length; i++) {
            if (after.tasks[i].id === elementId) {
                console.log("ok");
                after.tasks[i].completed = element.checked;
            }

            if (after.tasks[i].subTasks === undefined || after.tasks[i].subTasks === null || after.tasks[i].subTasks.length === 0) return;
            for (let j = 0; j < after.tasks[i].subTasks.length; i++) {
                if (after.tasks[i].subTasks[j].id === elementId) {
                    after.tasks[i].subTasks[j].completed = element.checked;
                }
            }
        }

        console.log(before.tasks);
        console.log(after.tasks)

        // after.tasks.map((task) => {
        //     if (task.id === elementId) {
        //         task.completed = element.checked;
        //
        //         task.subTasks.forEach((subTask) => {
        //             subTask.completed = element.checked;
        //         });
        //
        //         if (element.parentElement === null || element.parentElement.parentElement === null) return;
        //         const subtaskElements = element.parentElement?.parentElement.children[1].querySelectorAll(`div[class="subtask-body"]`);
        //
        //         subtaskElements?.forEach((subtaskElement) => {
        //             (subtaskElement.querySelector("input") as HTMLInputElement).checked = element.checked;
        //         });
        //     }
        // });

        // after.tasks.map((task) => {
        //     if (task.id === elementId) {
        //         if (element.parentElement === null || element.parentElement.parentElement === null) return;
        //         const subtaskElements = element.parentElement?.parentElement.children[1].querySelectorAll(`div[class="subtask-body"]`);
        //         subtaskElements?.forEach((subtaskElement) => {
        //             (subtaskElement.querySelector("input") as HTMLInputElement).checked = element.checked;
        //         });
        //         task.subTasks.forEach((subtask) => {
        //             subtask.completed = element.checked;
        //         });
        //     }
        // });

        const isEqual = JSON.stringify(after) === JSON.stringify(before);
        setAfter(after);
        setCanSave(!isEqual);
    };

    const toggleTask = (id: number) => {
        taskService.toggle(id)
            .then(() => {console.log("Task toggled!");});
    }

    const handleStartClick = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        await cardService.start(after.id);
        window.location.reload();
    }

    const handleCompleteClick = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        await cardService.complete(after.id);
        window.location.reload();
    }

    const handleReopenClick = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        await cardService.reopen(after.id);
        window.location.reload();
    }

    useEffect(() => {
        if (saving) {
            console.log("saving...");
            for (let i = 0; i < after.tasks.length; i++) {
                for  (let j = 0; j < after.tasks[i].subTasks.length; j++) {
                    console.log(after.tasks[i].subTasks[j].completed, before.tasks[i].subTasks[j].completed);
                    if (after.tasks[i].subTasks[j].completed !== before.tasks[i].subTasks[j].completed) {
                        toggleTask(after.tasks[i].subTasks[j].id);
                    }
                }
                if (after.tasks[i].completed !== before.tasks[i].completed) {
                    toggleTask(after.tasks[i].id);
                }
            }
        }
    }, [saving])

    return (
        <div className="card w-full h-full" onClick={handleCardClick}>

            <div className="card-info p-3 h-[25%]">
                <div className="flex justify-between items-center">
                    <h1>{after.title}</h1>
                    <h1 className={"whitespace-nowrap pl-2 pr-2 rounded-2xl text-sm " + after.status}>{formatStatus(after.status)}</h1>
                </div>
                <h1>{after.description}</h1>
            </div>

            <div className="card-body h-[60%]">

                {after.tasks.map((task) => (
                    <div className="task p-3" key={task.id}>
                        <div>
                            <input id={task.id.toString()}
                                   type="checkbox"
                                   className="mr-2"
                                   defaultChecked={task.completed}
                                   onChange={handleToggle} />
                            <label htmlFor={task.id.toString()}
                                   className={(task.completed ? "line-through" : "")} >
                                {task.title}</label>
                        </div>

                        <div className="subtasks-container w-[90%] ml-[10%]">

                            {task.subTasks.map((subTask) => (
                                <div key={subTask.id}>
                                    <div className="subtask-body">
                                        <input id={subTask.id.toString()}
                                               type="checkbox"
                                               className="mr-2"
                                               defaultChecked={subTask.completed}
                                               onChange={handleToggle} />
                                        <label htmlFor={subTask.id.toString()}
                                               className={(subTask.completed ? "line-through" : "")}>
                                            {subTask.title}</label>
                                    </div>
                                </div>
                            ))}

                        </div>
                    </div>
                ))}

            </div>

            <div className="action h-[15%] p-3">
                <form className="h-full">
                    {
                        after.status === "NOT_STARTED" &&
                        <button type="submit"
                                className="hover:cursor-pointer hover:bg-blue-300 duration-150 w-full h-full bg-blue-200 rounded-[10px]"
                                onClick={handleStartClick}>
                            Start</button>
                    }
                    {
                        after.status === "IN_PROGRESS" &&
                        <button type="submit"
                                className="hover:cursor-pointer hover:bg-yellow-300 duration-150 w-full h-full bg-yellow-200 rounded-[10px]"
                                onClick={handleCompleteClick}>
                            Complete</button>
                    }
                    {
                        after.status === "COMPLETED" &&
                        <button type="submit"
                                className="hover:cursor-pointer hover:bg-green-300 duration-150 w-full h-full bg-green-200 rounded-[10px]"
                                onClick={handleReopenClick}>
                            Reopen</button>
                    }
                </form>
            </div>

        </div>
    );
}

export default CardComponent;