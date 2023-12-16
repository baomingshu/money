package com.bjpowernode.money.utils;

import java.io.Serializable;

/**
 * 分页模型：
 * 共20条3页　当前为第2页　 首页 上一页 下一页 尾页:
 *  不变：共多少条   共多少页   当前页   首页   一页显示多少条 ==》Map =面向对象的编程思想=》PageModel
 *  变：上一页 下一页
 *
 * 分析 抽象 封装 继承 多态
 *
 * (cunPage-1)*pageSize  ,pageSize
 * 第一页 ：0 - 9
 * 第二页 ：10 - 19
 * 第三页 ：20 - 29
 *
 */
public class PageModel implements Serializable {
    private Long totalCount;//总条数
    private Long totalPage;//总页数
    private Long cunPage;//当前页
    private Integer firstPage=1;// 首页
    private Integer pageSize=10;//一页展示几条

    //private List list; 存放业务数据    松耦合性

    public PageModel() {
    }

    public PageModel(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalPage() {
        return totalCount%pageSize>0?totalCount/pageSize+1:totalCount/pageSize;
    }



    public Long getCunPage() {
        return cunPage;
    }

    public void setCunPage(Long cunPage) {
        this.cunPage = cunPage;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
