package com.reachauto.hkr.cr.service.impl;

import com.reachauto.hkr.cr.persistence.BalanceWalletRepository;
import com.reachauto.hkr.cr.pojo.enu.BalanceTypeEnum;
import com.reachauto.hkr.cr.service.BalanceStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceStatisticsServiceImpl implements BalanceStatisticsService {

    @Autowired
    private BalanceWalletRepository balanceWalletRepository;

    @Override
    public BigDecimal totalDeposit() {
        BigDecimal total = balanceWalletRepository.totalDepositBalance(BalanceTypeEnum.DEPOSIT_BALANCE.getCode());
        return total == null ? BigDecimal.ZERO : total;
    }
}
