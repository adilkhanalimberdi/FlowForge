import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {cardService} from "../../services/cardService.ts";
import type {Card} from "../../types/card/Card.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLeftLong} from "@fortawesome/free-solid-svg-icons";
import TaskComponent from "../../components/TaskComponent.tsx";
import type {Task} from "../../types/task/Task.ts";
import {taskService} from "../../services/taskService.ts";
import {toast} from "react-toastify";

function addSubTask(
    tasks: Task[],
    parentId: number,
    newTask: Task
): Task[] {
    return tasks.map(task => {
        if (task.id === parentId) {
            return {
                ...task,
                subTasks: [...task.subTasks, newTask]
            };
        }

        if (task.subTasks.length > 0) {
            return {
                ...task,
                subTasks: addSubTask(task.subTasks, parentId, newTask)
            };
        }

        return task;
    });
}

function removeTaskById(tasks: Task[], id: number): Task[] {
    return tasks
        .filter(task => task.id !== id)
        .map(task => ({
            ...task,
            subTasks: removeTaskById(task.subTasks ?? [], id)
        }));
}

function updateTaskById(tasks: Task[], updated: Task): Task[] {
    return tasks.map(task => {
        if (task.id === updated.id) {
            return {
                ...task,
                ...updated
            };
        }

        if (task.subTasks.length > 0) {
            return {
                ...task,
                subTasks: updateTaskById(task.subTasks, updated)
            };
        }

        return task;
    });
}


function CardPage() {
    const [card, setCard] = useState<Card | null>(null);
    const [title, setTitle] = useState<string>();
    const [description, setDescription] = useState<string>();
    const [tasks, setTasks] = useState<Task[]>([]);
    const { workspaceId, cardId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        if (!cardId) return;
        cardService.getById(Number.parseInt(cardId))
            .then(card => {
                if (!card) return;
                setCard(card);
                setTitle(card.title);
                setDescription(card.description);
                setTasks(card.tasks ?? []);
            })
            .catch(err => {
                if (err.status === 403) {
                    navigate("/login");
                }
                console.log(err);
            });
    }, [cardId]);

    const handleDeleteTask = async (id: number) => {
        try {
            await taskService.delete(id);

            setTasks(prev =>
                removeTaskById(prev, id)
            );
        } catch (err) {
            console.log(err);
        }
        // setTasks(prev => prev.filter(t => t.id !== id));
    }

    const handleUpdateTask = (updated: Task) => {
        setTasks(prev =>
            updateTaskById(prev, updated)
        );
    }

    if (!card || !tasks) {
        return null;
    }

    const handleSave = async () => {
        if (!title || !description) return;
        await cardService.update(card.id, {
            title,
            description,
            tasks
        })
            .then(() => {
                toast.success("Task saved successfully", {
                    position: "bottom-right",
                    autoClose: 2500,
                });
            })
            .catch(() => {
                toast.error("Something went wrong", {
                    position: "bottom-right",
                    autoClose: 300,
                });
            });
    }

    const handleDelete = async () => {
        await cardService.delete(card.id);
        navigate(`/dashboard/${workspaceId}`);
    }

    const handleCreatingTask = async () => {
        await taskService.createDefault(card.id, -1)
            .then(task => {
                setTasks(prev => [...prev, task])
            })
            .catch(err => {
                console.log(err);
            });
    }

    const handleCreatingSubTask = async (parentId: number) => {
         try {
             const newSubTask = await taskService.createDefault(card.id, parentId);

             setTasks(prev =>
                 addSubTask(prev, parentId, newSubTask)
             );
         } catch (err) {
             console.log(err);
         }
    };

    return (
        <div className="pl-30 pr-30 pt-5">
            <div className="flex justify-between">
                <button className="bg-blueviolet-500 hover:bg-blueviolet-600 active:bg-blueviolet-700 hover:cursor-pointer text-white pl-4 pr-4 pt-2 pb-2 rounded-[5px] mb-5"
                        onClick={() => {
                            navigate(`/dashboard/${workspaceId}`);
                        }}>
                    {/*<FontAwesomeIcon icon={faHouse} />*/}
                    <FontAwesomeIcon icon={faLeftLong} />
                </button>

                <div className="flex flex-row gap-2">
                    <button className="bg-blueviolet-500 hover:bg-blueviolet-600 active:bg-blueviolet-700 hover:cursor-pointer text-white pl-4 pr-4 pt-2 pb-2 rounded-[5px] mb-5"
                            onClick={handleDelete}>
                        Delete Card
                    </button>

                    <button className="bg-blueviolet-500 hover:bg-blueviolet-600 active:bg-blueviolet-700 hover:cursor-pointer text-white pl-4 pr-4 pt-2 pb-2 rounded-[5px] mb-5"
                            onClick={handleSave}>
                        Save Card
                    </button>
                </div>
            </div>
            <div>
                <form action="" className="w-[60%] flex flex-col gap-3 mb-5">
                    <div>
                        <label htmlFor="title" className="text-gray-700 text-lg">Title:</label>
                        <input
                            id="title"
                            type="text" value={title}
                            className="w-full border border-gray-500 p-3 rounded-[5px]"
                            onChange={(e) => setTitle(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="description" className="text-gray-700 text-lg">Description:</label>
                        <textarea
                            id="description"
                            className="w-full border border-gray-500 rounded-[5px] p-3"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </div>
                </form>

                <div className="">
                    <h1 className="text-gray-700 text-lg">Tasks:</h1>

                    <div className="task-container w-[25%]">
                        {tasks.map((task) => (
                            <div key={task.id}>
                                <TaskComponent task={task} padding={0} onDelete={handleDeleteTask} onChange={handleUpdateTask} />

                                <div className="subtask-container" data-parentid={task.id}>
                                    {task.subTasks.map((subTask) => (
                                        <TaskComponent task={subTask} padding={5} key={subTask.id} onDelete={handleDeleteTask} onChange={handleUpdateTask} />
                                    ))}
                                    <div className="h-10 pr-2 pl-5">
                                        <h1
                                            className="text-blueviolet-500 hover:cursor-pointer hover:text-blueviolet-600 hover:underline active:text-blueviolet-700 select-none w-max"
                                            onClick={ async (e) => {
                                                console.log(1);
                                                const element = e.target as HTMLDivElement;
                                                const subtaskContainer = element.parentElement?.parentElement as HTMLDivElement;
                                                const parentId = subtaskContainer.getAttribute("data-parentid");
                                                if (!parentId) return;
                                                await handleCreatingSubTask(Number.parseInt(parentId));
                                            }}
                                        >
                                            Create Subtask
                                        </h1>
                                    </div>
                                </div>
                            </div>
                        ))}

                        <div className="h-10 pr-2">
                            <h1
                                className="text-blueviolet-500 hover:cursor-pointer hover:text-blueviolet-600 hover:underline active:text-blueviolet-700 select-none w-max"
                                onClick={handleCreatingTask}
                            >Create Task</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CardPage;