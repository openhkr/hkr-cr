package com.reachauto.hkr.cr.controller;

import com.reachauto.hkr.cr.constant.ErrorCodeConstant;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordStatusVO;
import com.reachauto.hkr.cr.service.BalanceService;
import com.reachauto.hkr.cr.tool.exception.HkrParameterException;
import com.reachauto.hkr.cr.tool.response.Response;
import com.reachauto.hkr.cr.tool.response.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Deng Fangzhi
 * on 2018/1/29
 */
@Api(value = "API - BalanceController", tags = "balance")
@RestController
@RequestMapping(value = "/api/v2/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @ApiOperation(value = "修改余额", notes = "修改余额")
    @RequestMapping(value = "user_id={userId}", method = RequestMethod.PUT, headers = "api-version=1")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = 4010, message = "请求参数异常"),
            @ApiResponse(code = ErrorCodeConstant.BUSINESS_PROCESSING, message = "业务正在执行"),
            @ApiResponse(code = ErrorCodeConstant.BUSINESS_ALREADY_DONE, message = "业务已执行"),
            @ApiResponse(code = ErrorCodeConstant.WRONG_USER_INFO, message = "用户不存在"),
            @ApiResponse(code = ErrorCodeConstant.BALANCE_OUT_OF_RANGE, message = "余额超出限定范围"),
            @ApiResponse(code = ErrorCodeConstant.BALANCE_ACCOUNT_NOT_ENOUGH, message = "用户余额不足")
    })
    public Response modifyBalance(@PathVariable String userId, @RequestBody @Valid AccountModifyParameter accountModifyParameter) {

        if (StringUtils.isBlank(userId)) {
            throw new HkrParameterException();
        }
        accountModifyParameter.setUserId(userId);
        balanceService.modifyBalance(accountModifyParameter);
        return ResponseHelper.createSuccessResponse();
    }

    @ApiOperation(value = "业务回滚", notes = "业务回滚")
    @RequestMapping(value = "rollback/{uuid}", method = RequestMethod.PUT, headers = "api-version=1")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = 4010, message = "请求参数异常"),
            @ApiResponse(code = ErrorCodeConstant.BUSINESS_PROCESSING, message = "业务正在执行"),
            @ApiResponse(code = ErrorCodeConstant.BUSINESS_ALREADY_DONE, message = "业务已执行"),
            @ApiResponse(code = ErrorCodeConstant.UUID_NOT_EXISTS, message = "该流水不存在"),
            @ApiResponse(code = ErrorCodeConstant.WRONG_USER_INFO, message = "用户不存在"),
            @ApiResponse(code = ErrorCodeConstant.BALANCE_OUT_OF_RANGE, message = "余额超出限定范围"),
            @ApiResponse(code = ErrorCodeConstant.BALANCE_ACCOUNT_NOT_ENOUGH, message = "用户余额不足")
    })
    public Response rollback(@PathVariable("uuid") String uuid) {

        if (StringUtils.isBlank(uuid)) {
            throw new HkrParameterException();
        }
        balanceService.rollback(uuid);
        return ResponseHelper.createSuccessResponse();
    }

    @ApiOperation(value = "获取余额业务执行状态", notes = "获取余额业务执行状态")
    @RequestMapping(value = "{uuid}/record_status", method = RequestMethod.GET, headers = "api-version=1")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successful — 成功"),
            @ApiResponse(code = 4010, message = "请求参数异常")
    })
    public Response<BalanceRecordStatusVO> getRecordStatus(@PathVariable("uuid") String uuid) {

        if (StringUtils.isBlank(uuid)) {
            throw new HkrParameterException();
        }
        return ResponseHelper.createSuccessResponse(balanceService.getRecordStatus(uuid));
    }
}
