package com.reachauto.hkr.cr.controller;

import com.reachauto.hkr.cr.pojo.vo.StatisticsTotalVO;
import com.reachauto.hkr.cr.service.BalanceStatisticsService;
import com.reachauto.hkr.cr.tool.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BalanceStatisticsControllerTest {

    @InjectMocks
    private BalanceStatisticsController controller = new BalanceStatisticsController();

    @Mock
    private BalanceStatisticsService balanceStatisticsService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void totalDeposit() {
        Mockito.doReturn(BigDecimal.TEN).when(balanceStatisticsService).totalDeposit();
        Response<StatisticsTotalVO> result = controller.totalDeposit();
        assertEquals(0, result.getCode());
        assertEquals(BigDecimal.TEN, result.getPayload().getTotal());
    }
}