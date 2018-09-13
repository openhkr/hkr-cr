package com.reachauto.hkr.cr.controller;

import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordStatusVO;
import com.reachauto.hkr.cr.service.BalanceService;
import com.reachauto.hkr.cr.tool.exception.HkrParameterException;
import com.reachauto.hkr.cr.tool.response.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Administrator on 2018/2/23.
 */
public class BalanceControllerTest {

    @InjectMocks
    private BalanceController controller = new BalanceController();

    @Mock
    private BalanceService balanceService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenEmptyUserIdWhenModifyBalanceThenEx() throws Exception {
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.modifyBalance("", null);
    }

    @Test
    public void givenRightParamsWhenModifyBalanceThenPass() throws Exception {
        controller.modifyBalance("1", new AccountModifyParameter());
    }

    @Test
    public void givenEmptyUuidWhenRollbackThenEx() throws Exception {
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.rollback("");
    }

    @Test
    public void givenRightParamsWhenRollbackThenPass() throws Exception {
        controller.rollback("1");
    }

    @Test
    public void givenEmptyUuidWhenGetRecordStatusThenEx() throws Exception {
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getRecordStatus("");
    }

    @Test
    public void givenRightParamsWhenGetRecordStatusThenPass() throws Exception {
        doReturn(new BalanceRecordStatusVO()).when(balanceService).getRecordStatus(any());
        Response<BalanceRecordStatusVO> result = controller.getRecordStatus("uuid");
        assertEquals(0, result.getCode());
    }

}