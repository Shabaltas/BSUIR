export class Routes {

    public static clients = '/clients';
    public static createClient = '/clients/create';
    public static deposits = '/deposits';
    public static depositAccounts = '/deposits/accounts';

    public static editUser(id: number) {
        return '/clients/edit/' + id
    }

    public static createDeposit(clientId: number) {
        return '/deposits/new/' + clientId
    }

    public static viewAccount(accId: number) {
        return `${this.depositAccounts}/${accId}`
    }

}