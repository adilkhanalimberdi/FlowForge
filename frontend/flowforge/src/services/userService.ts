import type { User } from '../types/User';
import {api} from "./api.ts";

export const userService = {
    getAll: async (): Promise<User[]> => {
        const response = await api.get<User[]>("/users");
        return response.data;
    },

    getById: async (id: number): Promise<User | null> => {
        const response = await api.get<User | null>("/users/" + id);
        return response.data;
    }
}