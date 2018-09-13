package com.reachauto.hkr.cr.persistence;

import com.reachauto.hkr.cr.tool.page.PageSortCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */
public interface PagingAndSortingRepository<T> extends CrudRepository<T> {

    List<T> findBy(@Param(PageSortCondition.NAME) PageSortCondition condition);

    int countBy(@Param(PageSortCondition.NAME) PageSortCondition condition);

}
