import {Entity} from 'app/entity/Entity';

export interface Account extends Entity{

    accountNumber: string,

    clientId: number,

    depositName: string,

    credit: number,

    debet: number,
    clientSurname: string,
    clientName: string,
    clientPatronymic: string,

    currency: string,
    depositType: string,
    termInMonth: number,
    interestOnDeposit: number,

    contractNumber: string,
    startDate: Date,
    endDate: Date,

    saldo: number
}