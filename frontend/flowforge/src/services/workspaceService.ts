import type {Workspace} from "../types/Workspace.ts";
import api from "../api/api.ts";

export const workspaceService = {
    getAll: async (): Promise<Workspace[]> => {
        const response = await api.get<Workspace[]>("/api/workspaces");
        return response.data;
    },

    getById: async (id: number): Promise<Workspace | null> => {
        const response = await api.get<Workspace | null>("/api/workspaces/" + id);
        return response.data;
    },

    update: async (id: number, title: string): Promise<Workspace | null> => {
        const response = await api.put<Workspace | null>(`/api/workspaces/updateTitle/${id}?title=${title}`);
        return response.data;
    },

    createDefault: async (userId: number): Promise<Workspace | null> => {
        const response = await api.post<Workspace | null>(`/api/workspaces/createDefault/${userId}`);
        return response.data;
    },

    delete: async (id: number) => {
        const response = await api.delete<Workspace | null>(`/api/workspaces/${id}`);
        return response.data;
    }
}