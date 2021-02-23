import * as axios from "axios";

const apiInstance = axios.create({
    baseURL: "http://localhost:8066/"
});

const api = {
    getClients() {
        return apiInstance.get(`clients`)
            .then(res => res.data);
    },
    createClient(client) {
        return apiInstance.post('', client)
            .then(res => res.data);
    },
    getClient(clientId) {
        return apiInstance.get(`${clientId}`)
            .then(res => res.data);
    },
    deleteClient(clientId) {
        return apiInstance.delete(`${clientId}`)
            .then(res => res.data);
    },
    updateClient(clientId, client) {
        return apiInstance.put(`${clientId}`, client)
            .then(res => res.data);
    },
    getMaritalStatuses() {
        return apiInstance.get(`marital_statuses`)
            .then(res => res.data);
    },
    getCities() {
        return apiInstance.get(`cities`)
            .then(res => res.data);
    },
    getCitizenship() {
        return apiInstance.get(`citizenship`)
            .then(res => res.data);
    },
    getDisabilities() {
        return apiInstance.get(`disabilities`)
            .then(res => res.data);
    }
};

export default api;
