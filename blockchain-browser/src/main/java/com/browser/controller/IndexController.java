package com.browser.controller;

import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.TblBcBlock;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.protocol.EUDataGridResult;
import com.browser.service.BlockService;
import com.browser.service.TransactionService;
import com.browser.tools.Constant;
import com.browser.tools.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RealData realData;

    /**
     * @Title: index @Description: 进入首页 @author David @return String  @throws
     */

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("contract")
    public String contract() {
        return "contract";
    }

    /**
     * @Title: block @Description:区块详情 @author David @param @return
     * String  @throws
     */
    @RequestMapping("block")
    public String block(@RequestParam("blockId") String blockId, Model model) {

        TblBcBlock block = blockService.selectByPrimaryKey(blockId);
        List<TblBcTransaction> trx = transactionService.selectByBlockId(blockId);

        //计算总交易手续费
        Long fee = 0L;
        if (trx != null && trx.size() > 0) {
            for (TblBcTransaction transaction : trx) {
                fee = fee + transaction.getFee();
            }
        }
        block.setFeeStr(new BigDecimal(fee).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString() + " " + Constant.SYMBOL);

        model.addAttribute("block", block);
        model.addAttribute("trx", trx);

        return "blockInfo";
    }

    /**
     * @Title: blockNum @Description:根据快号查询快信息 @author David @param @return
     * String  @throws
     */
    @RequestMapping("blockNum")
    public String blockNum(@RequestParam("blockNum") String blockNum, Model model) {
        TblBcBlock block = blockService.selectByBlockNum(Long.valueOf(blockNum));
        List<TblBcTransaction> trx = transactionService.selectByBlockId(block.getBlockId());

        //计算总交易手续费
        Long fee = 0L;
        if (trx != null && trx.size() > 0) {
            for (TblBcTransaction transaction : trx) {
                fee = fee + transaction.getFee();
            }
        }
        block.setFeeStr(new BigDecimal(fee).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString() + " " + Constant.SYMBOL);

        model.addAttribute("block", block);
        model.addAttribute("trx", trx);

        return "blockInfo";
    }

    /**
     * @Title: detail @Description:交易详情 @author David @param @return
     * String  @throws
     */
    @RequestMapping("detail")
    public String detail(@RequestParam("trxId") String trxId, Model model) {

        TblBcTransaction trx = transactionService.queryByTrxId(trxId);
        model.addAttribute("trx", trx);
        if (trx.getTrxType() == Constant.TRX_TYPE_REGISTER_CONTRACT
                || trx.getTrxType() == Constant.TRX_TYPE_UPGRADE_CONTRACT
                || trx.getTrxType() == Constant.TRX_TYPE_DESTROY_CONTRACT) {
            return "contractTrxUpdate";
        } else if (trx.getTrxType() == Constant.TRX_TYPE_CALL_CONTRACT) {
            return "contractTrxTtransfer";
        } else if (trx.getTrxType() == Constant.TRX_TYPE_DEPOSIT_CONTRACT) {
            return "contractTrxDeposit";
        } else {
            return "transactionsInfo";
        }
    }

    /**
     * @Title: getStatis @Description:获取统计信息 @author David @param @return
     * String  @throws
     */
    @ResponseBody
    @RequestMapping("getStatis")
    public String getStatis() {
        return realData.getStatisInfo();
    }

    /**
     * 获取最新区块信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("blocksInfo")
    public String blocksInfo() {
        return realData.getBlockInfo();
    }

    /**
     * @Title: getBlock @Description:获取交易信息 @author David @param @return
     * String  @throws
     * 查询最新交易信息
     */
    @ResponseBody
    @RequestMapping("getTrxance")
    public String getTrxance(HttpServletRequest request) {
        return realData.getTransactionInfo();
    }

    /**
     * @Title: search @Description:搜索功能 @author David @param @return
     * String  @throws
     */
    @ResponseBody
    @RequestMapping("searchBlock")
    public String search(HttpServletRequest request) {
        Map<String, Object> params = getParams(request);
        return blockService.search(params);
    }

    @RequestMapping("queryTransactionList")
    @ResponseBody
    public EUDataGridResult queryTransactionList(@RequestParam("address") String address,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer rows) {
        TblBcTransaction transaction = new TblBcTransaction();
        transaction.setFromAccount(address);
        transaction.setToAccount(address);
        return transactionService.getTransactionList(transaction, page, rows);
    }

    @ResponseBody
    @RequestMapping("queryContractTrx")
    public String queryContractTrx(@RequestParam("address") String address, @RequestParam("contractId") String contractId) {

        TblBcTransaction transaction = new TblBcTransaction();
        transaction.setContractId(contractId);
        transaction.setFromAccount(address);
        List<TblBcTransaction> trxList = transactionService.queryContractTrx(transaction);
        return JSONObject.toJSONString(trxList);
    }

    @RequestMapping("transactionList")
    public String transactionList(@RequestParam("address") String address, Model model) {

        model.addAttribute("address", address);
        String balance = blockService.getBalance(address);
        model.addAttribute("balance", balance);

        return "transactionList";
    }
}
