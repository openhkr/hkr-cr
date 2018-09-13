package com.reachauto.hkr.cr.service.impl;

import com.reachauto.hkr.cr.cache.ConcurrentCache;
import com.reachauto.hkr.cr.constant.ErrorCodeConstant;
import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.manager.BalanceManager;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordStatusVO;
import com.reachauto.hkr.cr.service.BalanceService;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.exception.HkrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Deng Fangzhi
 * on 2018/1/29
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    private static final String CONCURRENT_MODIFY_KEY = "modify_";
    private static final String CONCURRENT_ROLLBACK_KEY = "rollback_";
    private static final Long CONCURRENT_TIMEOUT = 2L;
    private static final Integer DEL_FLAG = 1;
    private static final Integer ROLLBACK_FLAG = 1;
    private static final Integer NOT_OPERATED_STATUS = 1;
    private static final Integer ALREADY_OPERATED_STATUS = 2;
    private static final Integer ALREADY_ROLLED_BACK_STATUS = 4;

    @Autowired
    private BalanceManager balanceManager;

    @Autowired
    private ConcurrentCache concurrentCache;

    @Override
    public void modifyBalance(AccountModifyParameter parameter) {

        this.verifyModifyConcurrent(parameter.getUuid());
        balanceManager.modifyBalance(parameter);
    }

    @Override
    public void rollback(String uuid) {

        this.verifyRollbackConcurrent(uuid);
        BalanceRecordDO oldRecord = this.getOldRecord(uuid);
        balanceManager.rollback(oldRecord);
    }

    @Override
    public BalanceRecordStatusVO getRecordStatus(String uuid) {

        BalanceRecordStatusVO vo = new BalanceRecordStatusVO();
        BalanceRecordDO record = balanceManager.getRecordByUuid(uuid);
        if (Objects.isNull(record) || DEL_FLAG.equals(record.getDeleted())) {
            vo.setStatus(NOT_OPERATED_STATUS);
            return vo;
        }
        if (ROLLBACK_FLAG.equals(record.getRollbackFlag())) {
            vo.setStatus(ALREADY_ROLLED_BACK_STATUS);
            return vo;
        }
        vo.setStatus(ALREADY_OPERATED_STATUS);
        return vo;
    }

    private void verifyModifyConcurrent(String uuid) {

        if (concurrentCache.getConcurrent(CONCURRENT_MODIFY_KEY + uuid, 1, CONCURRENT_TIMEOUT) > 1) {
            throw new HkrServerException(ErrorCodeConstant.BUSINESS_PROCESSING, "business.processing");
        }
        if (Objects.nonNull(balanceManager.getRecordByUuid(uuid))) {
            throw new HkrBusinessException(ErrorCodeConstant.BUSINESS_ALREADY_DONE, "business.already.done");
        }
    }

    private BalanceRecordDO getOldRecord(String uuid) {

        BalanceRecordDO record = balanceManager.getRecordByUuid(uuid);
        if (Objects.isNull(record) || DEL_FLAG.equals(record.getDeleted())) {
            throw new HkrBusinessException(ErrorCodeConstant.UUID_NOT_EXISTS, "uuid.not.exists");
        }
        if (ROLLBACK_FLAG.equals(record.getRollbackFlag())) {
            throw new HkrBusinessException(ErrorCodeConstant.BUSINESS_ALREADY_DONE, "business.already.done");
        }
        return record;
    }

    private void verifyRollbackConcurrent(String uuid) {

        if (concurrentCache.getConcurrent(CONCURRENT_ROLLBACK_KEY + uuid, 1, CONCURRENT_TIMEOUT) > 1) {
            throw new HkrServerException(ErrorCodeConstant.BUSINESS_PROCESSING, "business.processing");
        }
    }
}
