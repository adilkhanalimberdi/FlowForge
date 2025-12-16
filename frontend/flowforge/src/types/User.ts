import type {Workspace} from "./Workspace.ts";

export interface User {
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    profilePictureUrl: string,
    workspaces: Workspace[]
}