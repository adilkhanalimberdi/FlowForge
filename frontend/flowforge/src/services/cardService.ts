import type {Card} from "../types/card/Card.ts";
import api from "../api/api.ts";
import type {CardUpdate} from "../types/card/CardUpdate.ts";

export const cardService = {
    getAll: async () => {
        const response = await api.get<Card[]>("/api/cards");
        return response.data;
    },

    getById: async (id: number) => {
        const response = await api.get<Card | null>("/api/cards/" + id);
        return response.data;
    },

    start: async (id: number) => {
        const response = await api.post<Card[]>(`/api/cards/start/${id}`);
        return response.data;
    },

    update: async (id: number, data: CardUpdate) => {
        const response = await api.post<Card>(`/api/cards/update/${id}`, data);
        return response.data;
    },

    complete: async (id: number) => {
        const response = await api.post<Card[]>(`/api/cards/complete/${id}`);
        return response.data;
    },

    reopen: async (id: number) => {
        const response = await api.post<Card[]>(`/api/cards/reopen/${id}`);
        return response.data;
    },

    delete: async (id: number) => {
        const response = await api.delete<Card>(`/api/cards/${id}`);
        return response.data;
    }
}