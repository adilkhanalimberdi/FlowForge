export interface TaskUpdate {
    id: number,
    title: string,
    completed: boolean,
    cardId: number,
    parentId: number | null,
}