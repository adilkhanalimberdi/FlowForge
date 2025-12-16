import type {Task} from "../task/Task.ts";

export interface CardUpdate {
    title: string,
    description: string,
    tasks: Task[]
}