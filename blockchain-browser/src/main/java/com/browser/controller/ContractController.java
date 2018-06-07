package com.browser.controller;

import com.browser.dao.entity.TblContractInfo;
import com.browser.protocol.EUDataGridResult;
import com.browser.service.impl.ContractService;
import com.browser.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

/** 合约信息处理入口
 * Created by Administrator on 2017/12/8 0008.
 */
@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @RequestMapping("/queryContractList")
    @ResponseBody
    public EUDataGridResult queryContractList(@RequestParam(required = false) String keyword,
                                              @RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(required = false, defaultValue = "20") Integer rows) {
        TblContractInfo contract = new TblContractInfo();
        if(!StringUtils.isEmpty(keyword)){
            contract.setKeyword(keyword);
        }
        //contract.setStatus(Constant.FOREVER_STATE);
        return contractService.getContractList(contract,page,rows);
    }

    @RequestMapping("/contractDetail")
    public String block(@RequestParam("contractId") String contractId,Model model) {
        TblContractInfo contractInfo = contractService.getContractDetail(contractId);
        model.addAttribute("contractInfo", contractInfo);
        return "contractDetail";
    }

    @RequestMapping("/queryContractTrxList")
    @ResponseBody
    public EUDataGridResult queryContractTrxList(@RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(required = false, defaultValue = "6") Integer rows) {
        TblContractInfo contract = new TblContractInfo();
        if(!StringUtils.isEmpty(keyword)){
            contract.setKeyword(keyword);
        }
        contract.setStatus(Constant.FOREVER_STATE);
        return contractService.getContractList(contract,page,rows);
    }
}
