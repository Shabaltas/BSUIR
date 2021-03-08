package com.bsuir.ashabaltas.piris.model.deposit;

public enum AccountsCode {
    DEP(3414),
    PRC(3471),
    CASH(1010),
    BDF(7327);

    private int code;
    AccountsCode(int code){
        this.code = code;
    }
    public int getCode(){ return code;}
}
