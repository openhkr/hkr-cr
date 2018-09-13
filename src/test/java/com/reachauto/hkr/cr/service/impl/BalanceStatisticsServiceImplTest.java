package com.reachauto.hkr.cr.service.impl;

import com.reachauto.hkr.cr.persistence.BalanceWalletRepository;
import com.reachauto.hkr.cr.service.BalanceStatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BalanceStatisticsServiceImplTest {

    @InjectMocks
    private BalanceStatisticsService service = new BalanceStatisticsServiceImpl();

    @Mock
    private BalanceWalletRepository balanceWalletRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void totalDepositWhenNull() {
        Mockito.doReturn(null).when(balanceWalletRepository).totalDepositBalance(2);
        BigDecimal result = service.totalDeposit();
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void totalDepositWhenNotNull() {
        Mockito.doReturn(BigDecimal.TEN).when(balanceWalletRepository).totalDepositBalance(2);
        BigDecimal result = service.totalDeposit();
        assertEquals(BigDecimal.TEN, result);
    }
}