import type {User} from './User';
import type {Card} from "./card/Card.ts";

export interface Workspace {
    id: number,
    title: string,
    user: User,
    cards: Card[]
}