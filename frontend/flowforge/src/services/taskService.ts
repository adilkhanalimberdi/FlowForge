import type {Task} from "../types/task/Task.ts";
import type {TaskCreation} from "../types/task/TaskCreation.ts";
import type {TaskUpdate} from "../types/task/TaskUpdate.ts";
import api from "../api/api.ts";


export const taskService = {
    getAll: async () => {
        const response = await api.get<Task[]>("/api/tasks");
        return response.data;
    },

    getById: async (id: number) => {
        const response = await api.get<Task | null>(`/api/tasks/${id}`);
        return response.data;
    },

    create: async (task: TaskCreation) => {
        const response = await api.post("/api/tasks", task);
        return response.data;
    },

    createDefault: async (id: number, parentId: number) => {
        const response = await api.post(`/api/tasks/createDefault/${id}?parentId=${parentId}`);
        return response.data;
    },

    update: async (id: number, task: TaskUpdate) => {
        const response = await api.post(`/api/tasks/${id}`, task);
        return response.data;
    },

    toggle: async (id: number) => {
        console.log(id);
        const response = await api.post(`/api/tasks/toggle/${id}`);
        return response.data;
    },

    delete: async (id: number) => {
        const response = await api.delete(`/api/tasks/${id}`);
        return response.data;
    }
}