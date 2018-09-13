package com.reachauto.hkr.cr.controller;

import com.reachauto.hkr.cr.pojo.vo.BalanceWalletVO;
import com.reachauto.hkr.cr.service.BalanceQueryService;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.exception.HkrParameterException;
import com.reachauto.hkr.cr.tool.page.PageListResult;
import com.reachauto.hkr.cr.tool.page.PageSortCondition;
import com.reachauto.hkr.cr.tool.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Administrator on 2018/2/23.
 */
public class BalanceQueryControllerTest {

    @InjectMocks
    private BalanceQueryController controller = new BalanceQueryController();

    @Mock
    private BalanceQueryService balanceQueryService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenStringBalanceTypeWhenGetBalanceThenEx() throws Exception {
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getBalance("1", "a");
    }

    @Test
    public void givenWrongBalanceTypeWhenGetBalanceThenEx() throws Exception {
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getBalance("1", "3");
    }

    @Test
    public void givenEmptyUserIdWhenGetBalanceThenEx() throws Exception {
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getBalance("", "2");
    }

    @Test
    public void givenRightParamWhenGetBalanceThenPass() throws Exception {

        BalanceWalletVO balanceWalletVO = new BalanceWalletVO();
        balanceWalletVO.setUserId("1");
        balanceWalletVO.setAmount(BigDecimal.TEN);
        balanceWalletVO.setBalanceType(2);
        doReturn(balanceWalletVO).when(balanceQueryService).getBalance(any());
        Response<BalanceWalletVO> result = controller.getBalance("1", "2");
        assertEquals(0, result.getCode());
        assertEquals("1", result.getPayload().getUserId());
        assertEquals(BigDecimal.TEN, result.getPayload().getAmount());
        assertEquals(Integer.valueOf(2), result.getPayload().getBalanceType());
    }


    @Test
    public void givenStringBalanceTypeWhenGetBalanceRecordThenEx() throws Exception {
        PageSortCondition pageSortCondition = new PageSortCondition(0, 10);
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getBalanceRecord("1", "a", pageSortCondition);
    }

    @Test
    public void givenWrongBalanceTypeWhenGetBalanceRecordThenEx() throws Exception {
        PageSortCondition pageSortCondition = new PageSortCondition(0, 10);
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getBalanceRecord("1", "3", pageSortCondition);
    }

    @Test
    public void givenEmptyUserIdWhenGetBalanceRecordThenEx() throws Exception {
        PageSortCondition pageSortCondition = new PageSortCondition(0, 10);
        thrown.expect(HkrParameterException.class);
        thrown.expectMessage("请求参数异常");
        controller.getBalanceRecord("", "2", pageSortCondition);
    }

    @Test
    public void givenWrongPage1WhenGetBalanceRecordThenEx() throws Exception {
        PageSortCondition pageSortCondition = new PageSortCondition(0, 201);
        thrown.expect(HkrBusinessException.class);
        thrown.expectMessage("page.params.error");
        controller.getBalanceRecord("1", "2", pageSortCondition);
    }

    @Test
    public void givenWrongPage2WhenGetBalanceRecordThenEx() throws Exception {
        PageSortCondition pageSortCondition = new PageSortCondition();
        thrown.expect(HkrBusinessException.class);
        thrown.expectMessage("page.params.error");
        controller.getBalanceRecord("1", "2", pageSortCondition);
    }

    @Test
    public void givenRightParamsWhenGetBalanceRecordThenEx() throws Exception {
        PageSortCondition pageSortCondition = new PageSortCondition(0, 100);

        doReturn(PageListResult.emptyResult()).when(balanceQueryService).getBalanceRecord(any());
        controller.getBalanceRecord("1", "2", pageSortCondition);
    }

    @Test
    public void getBalanceList() {
        List<String> userIdList = new ArrayList<>();
        userIdList.add("123");
        userIdList.add("456");
        List<BalanceWalletVO> balanceWalletVOList = new ArrayList<>();
        balanceWalletVOList.add(new BalanceWalletVO());
        doReturn(balanceWalletVOList).when(balanceQueryService).getBalanceList(userIdList, 1);
        Response<List<BalanceWalletVO>> response = controller.getBalanceList("123,456", 1);
        Assert.assertTrue(response.getPayload().size() > 0);
    }
}