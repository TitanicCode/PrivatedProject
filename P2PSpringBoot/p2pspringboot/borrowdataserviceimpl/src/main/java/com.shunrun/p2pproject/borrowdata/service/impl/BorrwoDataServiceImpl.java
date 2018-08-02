package com.shunrun.p2pproject.borrowdata.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.shunrun.p2pproject.borrowdata.pojo.BorrowData;
import com.shunrun.p2pproject.borrowdata.service.BorrowDataInterface;

/**
 * Created by jackiechan on 18-7-18/下午3:09
 */
@Service(application = "${dubbo.application.id}",version = "1.0",protocol = "${dubbo.protocol.id}",registry = "${dubbo.registry.id}")
public class BorrwoDataServiceImpl implements BorrowDataInterface {
    @Override
    public void addBorrowData(BorrowData borrowData) {

    }
}
