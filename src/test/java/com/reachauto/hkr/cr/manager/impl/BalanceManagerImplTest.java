package com.reachauto.hkr.cr.manager.impl;

import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import com.reachauto.hkr.cr.manager.BalanceManager;
import com.reachauto.hkr.cr.persistence.BalanceRecordRepository;
import com.reachauto.hkr.cr.persistence.BalanceWalletRepository;
import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.enu.BalanceTypeEnum;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.exception.HkrServerException;
import com.reachauto.hkr.cr.tool.page.PageListResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * @author Deng Fangzhi
 * on 2018/1/31
 */
public class BalanceManagerImplTest {

    @InjectMocks
    private BalanceManager balanceManager = new BalanceManagerImpl();

    @Mock
    private BalanceRecordRepository balanceRecordRepository;

    @Mock
    private BalanceWalletRepository balanceWalletRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void givenNotEnoughBalanceWhenModifyDepositThenEx() throws Exception {

        AccountModifyParameter parameter = new AccountModifyParameter();
        parameter.setUserId("1");
        parameter.setBalanceType(BalanceTypeEnum.DEPOSIT_BALANCE.getCode().toString());
        parameter.setUuid("uuid");
        parameter.setAmount(BigDecimal.TEN.negate());
        parameter.setRemarks("remarks");

        doReturn(1).when(balanceWalletRepository).modifyOrInsertBalance(any());
        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setAmount(new BigDecimal(-1));
        doReturn(balanceWalletDO).when(balanceWalletRepository).getBalanceByUserIdAndBalanceType(any(), any());
        thrown.expect(HkrServerException.class);
        thrown.expectMessage("balance.account.not.enough");
        balanceManager.modifyBalance(parameter);
    }

    @Test
    public void givenAlreadyRollbackRecordWhenRollbackDepositThenEx() throws Exception {

        BalanceRecordDO oldRecord = new BalanceRecordDO();
        oldRecord.setBalanceType(BalanceTypeEnum.DEPOSIT_BALANCE.getCode());
        oldRecord.setId(1L);

        doReturn(0).when(balanceRecordRepository).setHasRollback(any());
        thrown.expect(HkrBusinessException.class);
        thrown.expectMessage("business.already.done");
        balanceManager.rollback(oldRecord);
    }

    @Test
    public void givenDepositParamsWhenGetBalance() throws Exception {
        BalanceQueryDTO balanceQueryDTO = new BalanceQueryDTO();
        balanceQueryDTO.setUserId("1");
        balanceQueryDTO.setBalanceType(BalanceTypeEnum.DEPOSIT_BALANCE.getCode());

        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setUserId("1");
        balanceWalletDO.setAmount(BigDecimal.TEN);
        doReturn(balanceWalletDO).when(balanceWalletRepository).getBalanceByUserIdAndBalanceType("1", 2);

        BalanceWalletDO result = balanceManager.getBalance(balanceQueryDTO);
        Assert.assertEquals(BigDecimal.TEN, result.getAmount());
    }

    @Test
    public void givenDepositParamsWhenGetBalanceRecordThenNoResult() throws Exception {
        BalanceQueryDTO balanceQueryDTO = new BalanceQueryDTO();
        balanceQueryDTO.setUserId("1");
        balanceQueryDTO.setBalanceType(BalanceTypeEnum.DEPOSIT_BALANCE.getCode());
        balanceQueryDTO.setPageSize(10);
        balanceQueryDTO.setCurrentPage(0);

        doReturn(0).when(balanceRecordRepository).countBy(balanceQueryDTO);

        PageListResult<BalanceRecordDO> result = balanceManager.getBalanceRecord(balanceQueryDTO);

        Assert.assertEquals(0, result.getTotalCount());
    }

    @Test
    public void givenDepositParamsWhenGetBalanceRecordThenHasResult() throws Exception {
        BalanceQueryDTO balanceQueryDTO = new BalanceQueryDTO();
        balanceQueryDTO.setUserId("1");
        balanceQueryDTO.setBalanceType(BalanceTypeEnum.DEPOSIT_BALANCE.getCode());
        balanceQueryDTO.setPageSize(10);
        balanceQueryDTO.setCurrentPage(0);

        doReturn(1).when(balanceRecordRepository).countBy(balanceQueryDTO);

        List<BalanceRecordDO> list = new ArrayList<>();
        BalanceRecordDO balanceRecordDO = new BalanceRecordDO();
        balanceRecordDO.setUserId("1");
        list.add(balanceRecordDO);
        doReturn(list).when(balanceRecordRepository).findBy(balanceQueryDTO);

        PageListResult<BalanceRecordDO> result = balanceManager.getBalanceRecord(balanceQueryDTO);

        Assert.assertEquals(1, result.getTotalCount());
        Assert.assertEquals(1, result.getList().size());
    }

    @Test
    public void givenDepositParamsWhenGetBalanceListThenHasResult() {
        List<BalanceWalletDO> balanceWalletDOList = new ArrayList<>();
        List<String> userIdList = new ArrayList<>();
        Mockito.doReturn(balanceWalletDOList).when(balanceWalletRepository).getBalanceListByUserIdListAndBalanceType(userIdList, 2);
        List<BalanceWalletDO> result = balanceManager.getBalanceList(userIdList, 2);
        Assert.assertNotNull(result);
    }
}