import {api} from "./api.ts";
import type {Card} from "../types/Card.ts";

export const cardService = {
    getAll: async () => {
        const response = await api.get<Card[]>("/cards");
        return response.data;
    },

    getById: async (id: number) => {
        const response = await api.get<Card | null>("/cards/" + id);
        return response.data;
    },

    start: async (id: number) => {
        const response = await api.post<Card[]>(`/cards/start/${id}`);
        return response.data;
    },

    complete: async (id: number) => {
        const response = await api.post<Card[]>(`/cards/complete/${id}`);
        return response.data;
    },

    reopen: async (id: number) => {
        const response = await api.post<Card[]>(`/cards/reopen/${id}`);
        return response.data;
    }
}