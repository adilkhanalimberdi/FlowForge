import type {Card} from "../Card.ts";

export interface Task {
    id: number,
    title: string,
    completed: boolean,
    lastUpdate: Date,
    card: Card,
    parent: Task | null,
    subTasks: Task[]
}