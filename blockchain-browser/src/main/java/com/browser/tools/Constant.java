package com.browser.tools;

/**
 * 常量类.
 */
public class Constant {
    //交易类型
//账户相关
//普通转账
    public static final int TRX_TYPE_TRANSFER = 0;
    //代理领工资
    public static final int TRX_TYPE_WITHDRAW_PAY = 1;
    //注册账户
    public static final int TRX_TYPE_REGISTER_ACCOUNT = 2;
    //注册代理
    public static final int TRX_TYPE_REGISTER_DELEGATE = 3;
    //升级代理
    public static final int TRX_TYPE_UPGRADE_ACCOUNT = 4;
    //更新账户
    public static final int TRX_TYPE_UPDATE_ACCOUNT = 5;

    //合约相关
//注册合约
    public static final int TRX_TYPE_REGISTER_CONTRACT = 10;
    //合约充值 deposit
    public static final int TRX_TYPE_DEPOSIT_CONTRACT = 11;
    //合约升级
    public static final int TRX_TYPE_UPGRADE_CONTRACT = 12;
    //合约销毁
    public static final int TRX_TYPE_DESTROY_CONTRACT = 13;
    //调用合约
    public static final int TRX_TYPE_CALL_CONTRACT = 14;


    //合约状态
    public static final int DESTROY_STATE = 0;
    public static final int TEMP_STATE = 1;
    public static final int FOREVER_STATE = 2;

    //精度保留长度
    public static final int PRECISION_LENGTH = 8;
    public static final long PRECISION = 100000000L;
    public static final String SYMBOL = "CDC";

    //批量处理个数
    public static final Integer BATCH_LENGTH = 10000;
}
