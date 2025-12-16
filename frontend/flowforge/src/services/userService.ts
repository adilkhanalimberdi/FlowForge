import type { User } from '../types/User';
import api from "../api/api.ts";

export const userService = {
    getAll: async (): Promise<User[]> => {
        const response = await api.get<User[]>(`/api/users/`);
        return response.data;
    },

    getById: async (id: number): Promise<User | null> => {
        const response = await api.get<User | null>(`/api/users/${id}`);
        return response.data;
    },

    getCurrent: async (): Promise<User | null> => {
        const response = await api.get<User | null>(`/api/users/me`);
        return response.data;
    }
}