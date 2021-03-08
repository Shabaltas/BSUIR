import React from 'react';
import {DatePicker, Form} from 'antd';
import {getRequireRule, getWhiteSpaceRule} from 'app/ui/form/rules';
import moment from 'moment';

interface DefaultStringFormItemProps {
    name: string | (string | number)[],
    shortName?: string,
    props?: any,
    isNotRequire?: boolean
}

export const DateFormItem = ({name, shortName, props, isNotRequire}: DefaultStringFormItemProps) => {
    const nameString = Array.isArray(name) ? (shortName ?? name[2] as string) : (shortName ?? name as string);
    const label = nameString[0].toUpperCase() + nameString.slice(1, nameString.length);
    const rules = isNotRequire ? [] : [getRequireRule(nameString)];
    return (
        <Form.Item rules={rules} name={name} label={label} {...props}>
            <DatePicker {...props}/>
        </Form.Item>
    )
}