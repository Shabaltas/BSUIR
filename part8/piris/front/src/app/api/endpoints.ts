const serverUrl = 'http://localhost:8066';

export const endpoints = {
    clients: {
        base: `${serverUrl}/clients/`,
        clientUpdate: (id: number) => `${serverUrl}/clients/${id}`,
        cities: `${serverUrl}/cities`,
        citizenship: `${serverUrl}/citizenship`,
        marital_statuses: `${serverUrl}/marital_statuses`,
        disabilities: `${serverUrl}/disabilities`
    },
    deposits: {
        base: `${serverUrl}/deposits`,
        currencies: `${serverUrl}/deposits/currencies`,
        types: `${serverUrl}/deposits/types`,
        accounts: `${serverUrl}/deposits/accounts/`,
        viewAccount: (id: number) => `${serverUrl}/deposits/accounts/${id}`,
        closeDay: `${serverUrl}/deposits/close_day`
    }
};