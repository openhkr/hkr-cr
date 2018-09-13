package com.reachauto.hkr.cr.controller;

import com.reachauto.hkr.cr.constant.Constants;
import com.reachauto.hkr.cr.constant.ErrorCodeConstant;
import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.enu.BalanceTypeEnum;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordVO;
import com.reachauto.hkr.cr.pojo.vo.BalanceWalletVO;
import com.reachauto.hkr.cr.service.BalanceQueryService;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.page.PageListResult;
import com.reachauto.hkr.cr.tool.page.PageSortCondition;
import com.reachauto.hkr.cr.tool.response.Response;
import com.reachauto.hkr.cr.tool.response.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */
@Slf4j
@Api(value = "API - BalanceQueryController", tags = "balance_query")
@RestController
@RequestMapping(value = "/api/v2/balances")
public class BalanceQueryController {

    @Autowired
    private BalanceQueryService balanceQueryService;

    @ApiOperation(value = "根据用户id查询用户余额", notes = "根据用户id查询用户余额，可查普通余额和保证金余额")
    @RequestMapping(value = "user_id={userId}/balance_type={balanceType}/total", method = RequestMethod.GET, headers = "api-version=1")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = 4010, message = "请求参数异常")
    })
    public Response<BalanceWalletVO> getBalance(@PathVariable("userId") String userId,
                                                @PathVariable("balanceType") String balanceType) {

        BalanceQueryDTO balanceQueryDTO = BalanceQueryDTO.verifyAndAssembleParams(userId, balanceType, null);
        return ResponseHelper.createSuccessResponse(balanceQueryService.getBalance(balanceQueryDTO));
    }

    @ApiOperation(value = "批量：根据用户id查询用户余额", notes = "批量：根据用户id查询用户余额，可查普通余额和保证金余额, userIdsStr传userId字符串，逗号分隔，最多200个")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = ErrorCodeConstant.OUT_OF_QUERY_MAX_200, message = "批量查询用户数大于200条")
    })
    @RequestMapping(value = "user_ids_str={userIdsStr}/balance_type={balanceType}/total", method = RequestMethod.GET, headers = "api-version=1")
    public Response<List<BalanceWalletVO>> getBalanceList(@PathVariable("userIdsStr") String userIdsStr,
                                                          @PathVariable("balanceType") Integer balanceType) {
        if (StringUtils.isBlank(userIdsStr) || BalanceTypeEnum.getType(balanceType) == null) {
            return ResponseHelper.createNotFoundResponse();
        }
        String[] userIds = userIdsStr.split(",");
        if (userIds == null || userIds.length == 0) {
            return ResponseHelper.createNotFoundResponse();
        } else if (userIds.length > Constants.MAX_RECORDS_PER_PAGE) {
            throw new HkrBusinessException(ErrorCodeConstant.OUT_OF_QUERY_MAX_200, "out.of.query.max.200");
        }
        return ResponseHelper.createSuccessResponse(balanceQueryService.getBalanceList(Arrays.asList(userIds), balanceType));
    }

    @ApiOperation(value = "根据用户id查询用户余额记录", notes = "根据用户id查询用户余额记录，可查普通余额和保证金余额")
    @RequestMapping(value = "user_id={userId}/balance_type={balanceType}/record", method = RequestMethod.GET, headers = "api-version=1")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = 4010, message = "请求参数异常"),
            @ApiResponse(code = ErrorCodeConstant.PAGE_PARAMS_ERROR, message = "分页参数错误，每页最多200条")
    })
    public Response<PageListResult<BalanceRecordVO>> getBalanceRecord(@PathVariable("userId") String userId,
                                                                      @PathVariable("balanceType") String balanceType,
                                                                      PageSortCondition pageSortCondition) {
        BalanceQueryDTO balanceQueryDTO = BalanceQueryDTO.verifyAndAssembleParams(userId, balanceType, pageSortCondition);
        return ResponseHelper.createSuccessResponse(balanceQueryService.getBalanceRecord(balanceQueryDTO));
    }

}
