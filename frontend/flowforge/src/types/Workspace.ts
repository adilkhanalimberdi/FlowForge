import type {User} from './User';
import type {Card} from "./Card.ts";

export interface Workspace {
    id: number,
    title: string,
    user: User,
    cards: Card[]
}