import React from 'react';
import {DatePicker, Form} from 'antd';
import {getRequireRule} from 'app/ui/form/rules';
import moment from 'moment';

interface DefaultStringFormItemProps {
    name: string | (string | number)[],
    shortName?: string,
}

export const DateFormItem = ({name, shortName}: DefaultStringFormItemProps) => {
    const nameString = Array.isArray(name) ? (shortName ?? name[2] as string) : (shortName ?? name as string);
    const label = nameString[0].toUpperCase() + nameString.slice(1, nameString.length);
    return (
        <Form.Item rules={[getRequireRule(nameString)]} name={name} label={label}>
            <DatePicker />
        </Form.Item>
    )
}