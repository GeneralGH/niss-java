package com.nanyang.academy.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页工具类
 *
 * @author pt
 * @date 2022-06-07
 */
@ApiModel(value = "分页")
public class PageUtils<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "总记录数")
    @Getter
    @Setter
    private int totalCount;
    @ApiModelProperty(value = "每页记录数")
    @Getter
    @Setter
    private int pageSize;
    @ApiModelProperty(value = "总页数")
    @Getter
    @Setter
    private int totalPage;
    @ApiModelProperty(value = "当前页数")
    @Getter
    @Setter
    private int currPage;

    @ApiModelProperty(value = "列表数据")
    @Getter
    @Setter
    private T list;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageUtils(T list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public PageUtils(IPage<?> page) {
        this.list = (T) page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }
}
