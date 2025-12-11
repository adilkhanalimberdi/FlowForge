import type {Workspace} from "./Workspace.ts";
import type {Task} from "./task/Task.ts";

export interface Card {
    id: number,
    title: string,
    description: string,
    status: string,
    workspace: Workspace,
    tasks: Task[]
}