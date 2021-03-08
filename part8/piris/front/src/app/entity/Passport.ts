import {Entity} from 'app/entity/Entity';

export interface Passport extends Entity{
    series: string,

    number: string,

    issuedBy: string,

    dateOfIssue: Date,

    identificationNumber: string,
}