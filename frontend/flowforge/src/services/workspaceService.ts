import type {Workspace} from "../types/Workspace.ts";
import {api} from "./api.ts";

export const workspaceService = {
    getAll: async (): Promise<Workspace[]> => {
        const response = await api.get<Workspace[]>("/workspaces");
        return response.data;
    },

    getById: async (id: number): Promise<Workspace | null> => {
        const response = await api.get<Workspace | null>("/workspaces/" + id);
        return response.data;
    },

    update: async (id: number, workspace: Workspace): Promise<Workspace | null> => {
        const response = await api.post<Workspace | null>(`/workspaces/${id}`, workspace);
        return response.data;
    }
}