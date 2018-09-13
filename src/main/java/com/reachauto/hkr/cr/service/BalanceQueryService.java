package com.reachauto.hkr.cr.service;

import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordVO;
import com.reachauto.hkr.cr.pojo.vo.BalanceWalletVO;
import com.reachauto.hkr.cr.tool.page.PageListResult;

import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */
public interface BalanceQueryService {

    BalanceWalletVO getBalance(BalanceQueryDTO balanceQueryDTO);

    List<BalanceWalletVO> getBalanceList(List<String> userIdList, Integer balanceType);

    PageListResult<BalanceRecordVO> getBalanceRecord(BalanceQueryDTO balanceQueryDTO);
}
