const serverUrl = 'http://localhost:8066';

export const endpoints = {
    clients: `${serverUrl}/clients/`,
    clientUpdate: (id: number) => `${serverUrl}/clients/${id}`,
    cities: `${serverUrl}/cities`,
    citizenship: `${serverUrl}/citizenship`,
    marital_statuses: `${serverUrl}/marital_statuses`,
    disabilities: `${serverUrl}/disabilities`
};