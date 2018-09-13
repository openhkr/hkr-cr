package com.reachauto.hkr.cr.manager.impl;

import com.reachauto.hkr.cr.constant.ErrorCodeConstant;
import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import com.reachauto.hkr.cr.manager.BalanceManager;
import com.reachauto.hkr.cr.persistence.BalanceRecordRepository;
import com.reachauto.hkr.cr.persistence.BalanceWalletRepository;
import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.exception.HkrServerException;
import com.reachauto.hkr.cr.tool.page.PageListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author Deng Fangzhi
 * on 2018/1/30
 */
@Service
public class BalanceManagerImpl implements BalanceManager {

    @Autowired
    private BalanceRecordRepository balanceRecordRepository;

    @Autowired
    private BalanceWalletRepository balanceWalletRepository;

    @Transactional
    @Override
    public void modifyBalance(AccountModifyParameter parameter) {
        this.updateBalanceAndWriteRecordDeposit(BalanceRecordDO.buildFromAccountModifyParameter(parameter),
                BalanceWalletDO.buildFromAccountModifyParameter(parameter));

    }

    @Transactional
    @Override
    public void rollback(BalanceRecordDO oldRecord) {
        if (balanceRecordRepository.setHasRollback(oldRecord.getId()) == 0) {
            throw new HkrBusinessException(ErrorCodeConstant.BUSINESS_ALREADY_DONE, "business.already.done");
        }
        this.updateBalanceAndWriteRecordDeposit(oldRecord.getRollbackRecordDO(),
                BalanceWalletDO.buildFromBalanceRecordDOForRollback(oldRecord));


    }

    @Override
    public BalanceRecordDO getRecordByUuid(String uuid) {
        return balanceRecordRepository.getRecordByUuid(uuid);
    }

    @Override
    public BalanceWalletDO getBalance(BalanceQueryDTO balanceQueryDTO) {
        return balanceWalletRepository.getBalanceByUserIdAndBalanceType(balanceQueryDTO.getUserId(), balanceQueryDTO.getBalanceType());
    }

    @Override
    public List<BalanceWalletDO> getBalanceList(List<String> userIdList, Integer balanceType) {
        return balanceWalletRepository.getBalanceListByUserIdListAndBalanceType(userIdList, balanceType);
    }

    @Override
    public PageListResult<BalanceRecordDO> getBalanceRecord(BalanceQueryDTO balanceQueryDTO) {
        balanceQueryDTO.addDescOrder("id");
        int totalCount;
        List<BalanceRecordDO> balanceRecordDOList;
        totalCount = this.balanceRecordRepository.countBy(balanceQueryDTO);
        if (totalCount < 1) {
            return PageListResult.emptyResult();
        }
        balanceRecordDOList = this.balanceRecordRepository.findBy(balanceQueryDTO);

        return new PageListResult<>(balanceQueryDTO, totalCount, balanceRecordDOList);
    }

    private void updateBalanceAndWriteRecordDeposit(BalanceRecordDO balanceRecordDO, BalanceWalletDO balanceWalletDO) {
        try {
            balanceWalletRepository.modifyOrInsertBalance(balanceWalletDO);
        } catch (DataIntegrityViolationException e) {
            throw new HkrServerException(ErrorCodeConstant.BALANCE_OUT_OF_RANGE, "balance.out.of.range");
        }

        BalanceWalletDO balanceWalletDOResult = balanceWalletRepository.getBalanceByUserIdAndBalanceType(balanceWalletDO.getUserId(), balanceWalletDO.getBalanceType());
        if (balanceRecordDO.getChange().compareTo(BigDecimal.ZERO) < 0) {
            if (Objects.isNull(balanceWalletDOResult) || balanceWalletDOResult.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new HkrBusinessException(ErrorCodeConstant.BALANCE_ACCOUNT_NOT_ENOUGH, "balance.account.not.enough");
            }
        }
        balanceRecordDO.setBalanceAmount(balanceWalletDOResult.getAmount());
        balanceRecordRepository.createRecord(balanceRecordDO);
    }

}
