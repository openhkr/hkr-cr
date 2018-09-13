package com.reachauto.hkr.cr.service.impl;

import com.reachauto.hkr.cr.cache.ConcurrentCache;
import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.manager.BalanceManager;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.service.BalanceService;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.exception.HkrServerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;

/**
 * @author Deng Fangzhi
 * on 2018/1/31
 */
public class BalanceServiceImplTest {

    @InjectMocks
    private BalanceService balanceService = new BalanceServiceImpl();

    @Mock
    private BalanceManager balanceManager;

    @Mock
    private ConcurrentCache concurrentCache;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private String uuid;

    private AccountModifyParameter accountModifyParameter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        accountModifyParameter = new AccountModifyParameter();
        accountModifyParameter.setAmount(new BigDecimal(1));
        accountModifyParameter.setUserId("1");
        accountModifyParameter.setUuid("111");
        uuid = "111";
    }

    @Test
    public void givenProcessingWhenModifyThenEx() throws Exception {

        doReturn(2L).when(concurrentCache).getConcurrent("modify_111", 1, 2L);
        thrown.expect(HkrServerException.class);
        thrown.expectMessage("business.processing");
        balanceService.modifyBalance(accountModifyParameter);
    }

    @Test
    public void givenModifiedWhenModifyThenEx() throws Exception {

        doReturn(0L).when(concurrentCache).getConcurrent("modify_111", 1, 2L);
        doReturn(new BalanceRecordDO()).when(balanceManager).getRecordByUuid("111");
        thrown.expect(HkrServerException.class);
        thrown.expectMessage("business.already.done");
        balanceService.modifyBalance(accountModifyParameter);
    }

    @Test
    public void givenNoCurrentWhenModifyThenPass() throws Exception {

        doReturn(0L).when(concurrentCache).getConcurrent("modify_111", 1, 2L);
        doReturn(null).when(balanceManager).getRecordByUuid("111");
        balanceService.modifyBalance(accountModifyParameter);
    }

    @Test
    public void givenProcessingWhenRollbackThenEx() throws Exception {

        doReturn(2L).when(concurrentCache).getConcurrent("rollback_111", 1, 2L);
        thrown.expect(HkrServerException.class);
        thrown.expectMessage("business.processing");
        balanceService.rollback("111");
    }

    @Test
    public void givenDeletedRecordWhenRollbackThenEx() throws Exception {

        BalanceRecordDO record = new BalanceRecordDO();
        record.setDeleted(1);
        doReturn(record).when(balanceManager).getRecordByUuid(uuid);
        thrown.expect(HkrBusinessException.class);
        thrown.expectMessage("uuid.not.exists");
        balanceService.rollback(uuid);
    }

    @Test
    public void givenAlreadyRollbackWhenRollbackThenEx() throws Exception {

        BalanceRecordDO record = new BalanceRecordDO();
        record.setDeleted(0);
        record.setRollbackFlag(1);
        doReturn(record).when(balanceManager).getRecordByUuid(uuid);
        thrown.expect(HkrBusinessException.class);
        thrown.expectMessage("business.already.done");
        balanceService.rollback(uuid);
    }

    @Test
    public void givenRightParamWhenRollbackThenPass() throws Exception {

        BalanceRecordDO record = new BalanceRecordDO();
        record.setUserId("1");
        record.setChange(new BigDecimal(1));
        record.setDeleted(0);
        record.setRollbackFlag(0);
        doReturn(record).when(balanceManager).getRecordByUuid(uuid);
        balanceService.rollback(uuid);
    }

    @Test
    public void givenNotExistedRecordWhenGetStatusThenReturn1() throws Exception {

        BalanceRecordDO record = new BalanceRecordDO();
        record.setDeleted(1);
        doReturn(record).when(balanceManager).getRecordByUuid(uuid);
        Assert.assertEquals("记录未执行", 1, balanceService.getRecordStatus(uuid).getStatus().intValue());
    }

    @Test
    public void givenRolledBackRecordWhenGetStatusThenReturn4() throws Exception {

        BalanceRecordDO record = new BalanceRecordDO();
        record.setDeleted(0);
        record.setRollbackFlag(1);
        doReturn(record).when(balanceManager).getRecordByUuid(uuid);
        Assert.assertEquals("记录已回滚", 4, balanceService.getRecordStatus(uuid).getStatus().intValue());
    }

    @Test
    public void givenOperatedRecordWhenGetStatusThenReturn2() throws Exception {

        BalanceRecordDO record = new BalanceRecordDO();
        record.setDeleted(0);
        record.setRollbackFlag(0);
        doReturn(record).when(balanceManager).getRecordByUuid(uuid);
        Assert.assertEquals("记录已执行", 2, balanceService.getRecordStatus(uuid).getStatus().intValue());
    }
}