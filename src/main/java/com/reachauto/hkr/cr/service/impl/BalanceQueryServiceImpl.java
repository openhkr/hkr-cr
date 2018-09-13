package com.reachauto.hkr.cr.service.impl;

import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import com.reachauto.hkr.cr.manager.BalanceManager;
import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordVO;
import com.reachauto.hkr.cr.pojo.vo.BalanceWalletVO;
import com.reachauto.hkr.cr.service.BalanceQueryService;
import com.reachauto.hkr.cr.tool.page.PageListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */
@Service
public class BalanceQueryServiceImpl implements BalanceQueryService {

    @Autowired
    private BalanceManager balanceManager;

    @Override
    public BalanceWalletVO getBalance(BalanceQueryDTO balanceQueryDTO) {
        BalanceWalletDO balanceWalletDO = balanceManager.getBalance(balanceQueryDTO);
        if (balanceWalletDO == null) {
            BalanceWalletVO balanceWalletVO = new BalanceWalletVO();
            balanceWalletVO.setUserId(balanceQueryDTO.getUserId());
            balanceWalletVO.setBalanceType(balanceQueryDTO.getBalanceType());
            balanceWalletVO.setAmount(BigDecimal.ZERO);
            return balanceWalletVO;
        }
        return BalanceWalletVO.buildFromBalanceWalletDO(balanceWalletDO);
    }

    @Override
    public List<BalanceWalletVO> getBalanceList(List<String> userIdList, Integer balanceType) {
        List<BalanceWalletVO> balanceWalletVOList = new ArrayList<>();
        List<BalanceWalletDO> balanceWalletDOList = balanceManager.getBalanceList(userIdList, balanceType);
        balanceWalletDOList.forEach(balanceWalletDO -> {
            BalanceWalletVO balanceWalletVO = new BalanceWalletVO();
            balanceWalletVO.setUserId(balanceWalletDO.getUserId());
            balanceWalletVO.setBalanceType(balanceWalletDO.getBalanceType());
            balanceWalletVO.setAmount(balanceWalletDO.getAmount());
            balanceWalletVOList.add(balanceWalletVO);
        });
        return balanceWalletVOList;
    }

    @Override
    public PageListResult<BalanceRecordVO> getBalanceRecord(BalanceQueryDTO balanceQueryDTO) {
        PageListResult<BalanceRecordDO> balanceRecordDOPageListResult = balanceManager.getBalanceRecord(balanceQueryDTO);
        List<BalanceRecordVO> balanceRecordVOList = BalanceRecordVO.buildFromBalanceRecordDOList(balanceRecordDOPageListResult.getList());
        return new PageListResult<>(balanceQueryDTO, balanceRecordDOPageListResult.getTotalCount(), balanceRecordVOList);
    }
}
