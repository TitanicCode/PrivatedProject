package com.shunrun.p2pproject.borrowdata.service;


import com.shunrun.p2pproject.borrowdata.pojo.BorrowData;

public interface BorrowDataInterface {
    /**
     * 添加借款数据的方法
     * @param borrowData
     */
    void addBorrowData(BorrowData borrowData);
}
