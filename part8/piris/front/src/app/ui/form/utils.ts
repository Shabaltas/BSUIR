/**
 * Use it for object like :
 * {
 *     name:test,
 *     passport:{
 *         name: test
 *     }
 * }
 * or
 * {
 *     name: test
 * }
 * @param object
 */
export const getDefaultValuesFromObject = (object: any) => {
    if (!object || isPrimitive(object)) {
        return object;
    }

    const resultObject: any = {};
    Object.keys(object).forEach((key => {
        if (isPrimitive(object[key])) {
            resultObject[key] = object[key];
        } else {
            resultObject[key] = Object.keys(object[key]).map((innerObjectKey) => {
                return {[innerObjectKey]: object[key][innerObjectKey]}
            })
        }
    }));

    return resultObject;
}

export const getObjectFromValues = (object: any) => {
    if (!object || isPrimitive(object)) {
        return object;
    }
    const resultObject: any = {};
    Object.keys(object).forEach((key => {
        if (isPrimitive(object[key])) {
            resultObject[key] = object[key];
        } else {
            if (Array.isArray(object[key])) {
                let buf = {}
                object[key].forEach((obj: any) => {
                    buf = {...buf, ...obj}
                })
                resultObject[key] = buf;
            } else {
                resultObject[key] = object[key].toDate();
            }

        }
    }));

    return resultObject;
}
export const isPrimitive = (obj: any): boolean => {
    return (obj !== Object(obj));
}