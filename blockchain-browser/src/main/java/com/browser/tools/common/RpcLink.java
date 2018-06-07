package com.browser.tools.common;

public class RpcLink {
	
	// 获取快接口
	public static final String BLOCK_NUM = "blockchain_get_block";
	
	// 获取打包代理快
	public static final String BLOCK_SIGNEE = "blockchain_get_block_signee";
	
	// 获取块交易信息
	public static final String BLOCK_TRX = "blockchain_get_transaction";

	public static final String BLOCK_COUNT = "blockchain_get_block_count";
	
	// 交易总额
	public static final String TRX_AMOUNT = "trxAmount";
	
	// 交易总数
	public static final String TRX_NUMBER = "trxNumber";
	
	// 总交易手续费
	public static final String TRX_FEE = "trxfee";
	
	// 每小时交易量
	public static final String TRX_HOUR = "trxHour";
	
	// 交易峰值每小时
	public static final String TRX_AVGHOUR = "trxAvgHour";

	public static final String BLOCK_BALANCE = "blockchain_get_balance";

	// 获取合约交易信息
	public static final String CONTRACT_TRX = "blockchain_get_pretty_contract_transaction";

	//获取合约信息
	public static final String CONTRACT_INFO = "contract_get_info";

	public static final String CONTRACT_BALANCE = "contract_get_balance";

	public static final String BLOCK_ASSET = "blockchain_get_asset";

	public static final String MULTISIG_ACCOUNT = "wallet_import_multisig_account_by_detail";

	public static final String ADDRESS_BALANCE = "blockchain_list_address_balances";
}
