import {Entity} from 'app/entity/Entity';



export interface User extends Entity{

    surname: string,

    name: string,

    second_name: string,

    date_of_birth: Date,

    passport_series: string,

    passport_number: string,

    issued_by: string,

    date_of_issue: Date,

    identification_number: string,

    place_of_birth: string,

    city_of_residence: number,

    address_of_residence: string,

    home_phone: string,

    mobile_phone: string,

    email: string,

    place_of_work: string,

    position: string,

    disability: number,

    citizenship: number,

    marital_status: number,

    retiree: boolean,

    monthly_income: number,

    liable_for_military: boolean
}