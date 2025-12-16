import type {Task} from "../types/task/Task.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faXmark} from "@fortawesome/free-solid-svg-icons";

interface Props {
    task: Task;
    padding: number;
    onDelete: (id: number) => void;
    onChange: (updatedTask: Task) => void;
}

function TaskComponent({task, padding, onDelete, onChange}: Props) {
    return (
        <div className="flex justify-between">
            <div className={`pl-${padding} pb-3 flex items-center`}>
                <input
                    id={task.id.toString()}
                    type="checkbox"
                    checked={task.completed}
                    className="w-6 h-6 accent-blueviolet-500"
                    onChange={(e) =>
                        onChange({ ...task, completed: e.target.checked })
                    }
                />
                <label htmlFor={task.id.toString()}>
                    <input
                        type="text"
                        value={task.title}
                        className="border border-gray-500 w-[300px] rounded-[4px] pl-1 pr-1 ml-2"
                        onChange={(e) =>
                            onChange({ ...task, title: e.target.value })
                        }
                    />
                </label>
            </div>

            <button
                className="h-max border rounded-[4px] w-[28px] border-gray-500 text-gray-500 hover:bg-red-500 hover:text-white hover:border-none duration-200"
                onClick={() => onDelete(task.id)}
            >
                <FontAwesomeIcon icon={faXmark} />
            </button>
        </div>
    )
}

export default TaskComponent;