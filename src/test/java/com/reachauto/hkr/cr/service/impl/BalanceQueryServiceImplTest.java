package com.reachauto.hkr.cr.service.impl;

import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import com.reachauto.hkr.cr.manager.BalanceManager;
import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordVO;
import com.reachauto.hkr.cr.pojo.vo.BalanceWalletVO;
import com.reachauto.hkr.cr.service.BalanceQueryService;
import com.reachauto.hkr.cr.tool.page.PageListResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Administrator on 2018/2/23.
 */
public class BalanceQueryServiceImplTest {

    @InjectMocks
    private BalanceQueryService balanceQueryService = new BalanceQueryServiceImpl();

    @Mock
    private BalanceManager balanceManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBalanceNoResult() throws Exception {
        BalanceQueryDTO balanceQueryDTO = new BalanceQueryDTO();
        balanceQueryDTO.setUserId("1");
        balanceQueryDTO.setBalanceType(1);

        doReturn(null).when(balanceManager).getBalance(balanceQueryDTO);

        BalanceWalletVO result = balanceQueryService.getBalance(balanceQueryDTO);
        assertEquals("1", result.getUserId());
        assertEquals(Integer.valueOf(1), result.getBalanceType());
        assertEquals(BigDecimal.ZERO, result.getAmount());
    }

    @Test
    public void getBalanceHasResult() throws Exception {
        BalanceQueryDTO balanceQueryDTO = new BalanceQueryDTO();
        balanceQueryDTO.setUserId("1");
        balanceQueryDTO.setBalanceType(1);

        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setUserId("1");
        balanceWalletDO.setBalanceType(1);
        balanceWalletDO.setAmount(BigDecimal.TEN);
        doReturn(balanceWalletDO).when(balanceManager).getBalance(balanceQueryDTO);

        BalanceWalletVO result = balanceQueryService.getBalance(balanceQueryDTO);
        assertEquals("1", result.getUserId());
        assertEquals(Integer.valueOf(1), result.getBalanceType());
        assertEquals(BigDecimal.TEN, result.getAmount());

    }

    @Test
    public void getBalanceRecord() throws Exception {
        BalanceQueryDTO balanceQueryDTO = new BalanceQueryDTO();
        balanceQueryDTO.setUserId("1");
        balanceQueryDTO.setBalanceType(1);
        balanceQueryDTO.setCurrentPage(0);
        balanceQueryDTO.setPageSize(10);

        List<BalanceRecordDO> list = new ArrayList<>();
        BalanceRecordDO balanceRecordDO = new BalanceRecordDO();
        list.add(balanceRecordDO);
        PageListResult<BalanceRecordDO> balanceRecordDOPageListResult = new PageListResult<>(balanceQueryDTO, 1, list);
        doReturn(balanceRecordDOPageListResult).when(balanceManager).getBalanceRecord(balanceQueryDTO);

        PageListResult<BalanceRecordVO> result = balanceQueryService.getBalanceRecord(balanceQueryDTO);
        assertEquals(1, result.getTotalCount());
        assertEquals(1, result.getList().size());

    }

    @Test
    public void getBalanceList() {
        List<BalanceWalletDO> balanceWalletDOList = new ArrayList<>();
        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setAmount(BigDecimal.valueOf(1));
        balanceWalletDO.setUserId("12");
        balanceWalletDO.setBalanceType(1);
        balanceWalletDOList.add(balanceWalletDO);
        List<String> userIdList = new ArrayList<>();
        Mockito.doReturn(balanceWalletDOList).when(balanceManager).getBalanceList(userIdList, 1);
        List<BalanceWalletVO> balanceWalletVOList = balanceQueryService.getBalanceList(userIdList, 1);
        Assert.assertTrue(balanceWalletVOList.size() > 0);
    }
}