import type {Workspace} from "./Workspace.ts";

export interface User {
    id: number,
    username: string,
    email: string,
    profilePictureUrl: string,
    workspaces: Workspace[]
}