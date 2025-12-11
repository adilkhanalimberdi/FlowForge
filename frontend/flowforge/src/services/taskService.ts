import type {Task} from "../types/task/Task.ts";
import {api} from "./api.ts";
import type {TaskCreation} from "../types/task/TaskCreation.ts";
import type {TaskUpdate} from "../types/task/TaskUpdate.ts";


export const taskService = {
    getAll: async () => {
        const response = await api.get<Task[]>("/tasks");
        return response.data;
    },

    getById: async (id: number) => {
        const response = await api.get<Task | null>("/tasks/" + id);
        return response.data;
    },

    create: async (task: TaskCreation) => {
        const response = await api.post("/tasks", task);
        return response.data;
    },

    update: async (id: number, task: TaskUpdate) => {
        const response = await api.post(`/tasks/${id}`, task);
        return response.data;
    },

    toggle: async (id: number) => {
        const response = await api.post(`/tasks/toggle/${id}`);
        return response.data;
    }
}