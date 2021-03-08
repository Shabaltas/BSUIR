export const getWhiteSpaceRule = (name: string) => {
    return {
        whitespace: true,
        message: `Fill ${name}!`,
    }
}

export const getRequireRule = (name: string) => {
    return {
        required: true,
        message: `Please enter your ${name}!`
    }
}
export const getDefaultRules = (name: string) => {
    return [[getWhiteSpaceRule(name), getRequireRule(name)]]
}