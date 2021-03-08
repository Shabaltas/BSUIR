import React, {useContext, useEffect, useState} from 'react';
import {observer} from 'mobx-react-lite';
import {RouteComponentProps, useHistory} from 'react-router';
import {RootStoreContext} from "../../RootStoreContext";
import {Form, Switch} from "antd";
import moment from "moment";
import {StringFormItem} from "../form/StringFormItem";
import {SelectFormItem} from "../form/SelectFormItem";
import {DateFormItem} from "../form/DateFormItem";
import {Account} from "../../entity/Account";


const ACCOUNT_NUMBER = 'accountNumber';
const CLIENT_ID = 'clientId';
const CLIENT_NAME = 'clientName';
const CLIENT_SURNAME = 'clientSurname';
const CLIENT_PATRONYMIC = 'clientPatronymic';
const CURRENCY = 'currency';
const DEPOSIT_NAME = 'depositName';
const START_DATE = 'startDate';
const END_DATE = 'endDate';
const CREDIT = 'credit';
const INTEREST_ON_DEPOSIT = 'interestOnDeposit';
const TERM_IN_MONTH = 'termInMonth';
const DEBET = 'debet';
const SALDO = 'saldo';
const DEPOSIT_TYPE = 'depositType';
const CONTRACT_NUMBER = 'contractNumber';


interface AccountProps {
    accountId?: string
}
export const AccountForm = observer((props: RouteComponentProps<AccountProps>) => {

    const {accountsStore} = useContext(RootStoreContext);
    const [form] = Form.useForm();

    useEffect(() => {
        accountsStore.fetchItemById(Number(props.match.params.accountId)).then(resp => {
            let values: any = {...accountsStore.fetchedItem};
            values[START_DATE] = moment(values[START_DATE]);
            values[END_DATE] = moment(values[END_DATE]);
            form.setFieldsValue(values);
        });
    }, [accountsStore]);

    return (
        <>
            <Form
                labelCol={{span: 4}}
                wrapperCol={{span: 10}}
                layout="horizontal"
                size={'large'}
                form={form}
            >
                <StringFormItem name={ACCOUNT_NUMBER} props={{disabled: true}}/>
                <StringFormItem name={CONTRACT_NUMBER} props={{disabled: true}}/>
                <StringFormItem name={DEPOSIT_NAME} props={{disabled: true}}/>
                <StringFormItem name={CLIENT_ID} props={{disabled: true}}/>
                <StringFormItem name={CLIENT_NAME} props={{disabled: true}}/>
                <StringFormItem name={CLIENT_SURNAME} props={{disabled: true}}/>
                <StringFormItem name={CLIENT_PATRONYMIC} props={{disabled: true}}/>
                <StringFormItem name={CURRENCY} props={{disabled: true}}/>
                <StringFormItem name={DEPOSIT_TYPE} props={{disabled: true}}/>
                <StringFormItem name={TERM_IN_MONTH} props={{disabled: true}}/>
                <StringFormItem name={INTEREST_ON_DEPOSIT} props={{disabled: true}}/>
                <StringFormItem name={CREDIT} props={{disabled: true}}/>
                <StringFormItem name={DEBET} props={{disabled: true}}/>
                <StringFormItem name={SALDO} props={{disabled: true}}/>

                <DateFormItem name={START_DATE} shortName={'Start date'} props={{disabled: true}}/>

                <DateFormItem name={END_DATE} shortName={'End date'} props={{disabled: true}}/>

            </Form>
        </>)
});