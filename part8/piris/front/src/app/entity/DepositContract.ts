import {Entity} from 'app/entity/Entity';



export interface DepositContract extends Entity{

    deposit: number,

    depositType: number,

    currency: number,

    clientId: number,

    startDate: Date,

    depositAmount: number,

    interestOnDeposit: number,

    capitalization: boolean,

    endDate: Date,

    termInMonth: number
}