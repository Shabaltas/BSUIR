export class Routes {

    public static clients = '/clients';
    public static createClient = '/clients/create';

    public static editUser(id: number) {
        return '/clients/edit/' + id
    }

}