import type {Card} from "../card/Card.ts";

export interface Task {
    id: number,
    title: string,
    completed: boolean,
    lastUpdate: Date,
    card: Card,
    parent: Task | null,
    subTasks: Task[]
}