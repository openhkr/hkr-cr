package com.reachauto.hkr.cr.controller;

import com.reachauto.hkr.cr.pojo.vo.StatisticsTotalVO;
import com.reachauto.hkr.cr.service.BalanceStatisticsService;
import com.reachauto.hkr.cr.tool.response.Response;
import com.reachauto.hkr.cr.tool.response.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API - BalanceStatisticsController", tags = "balance_statistics")
@RestController
@RequestMapping(value = "/api/v2/balances/statistics")
public class BalanceStatisticsController {

    @Autowired
    private BalanceStatisticsService balanceStatisticsService;

    @ApiOperation(value = "统计保证金余额总和", notes = "统计保证金余额总和")
    @RequestMapping(value = "deposit/total", method = RequestMethod.GET, headers = "api-version=1")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = 4010, message = "请求参数异常")
    })
    public Response<StatisticsTotalVO> totalDeposit() {
        return ResponseHelper.createSuccessResponse(new StatisticsTotalVO(balanceStatisticsService.totalDeposit()));
    }
}
