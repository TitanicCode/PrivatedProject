package com.shunrun.p2pproject.borrowdata.pojo;



import java.io.Serializable;

/**
 * Created by jackiechan on 18-7-18/下午2:52
 */

public class BorrowData implements Serializable {

    private String borrowDataId;
    private String borrowDataName;
    private String fczPic;
    private String otherData;

    public String getFczPic() {
        return fczPic;
    }

    public void setFczPic(String fczPic) {
        this.fczPic = fczPic;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public String getBorrowDataId() {
        return borrowDataId;
    }

    public void setBorrowDataId(String borrowDataId) {
        this.borrowDataId = borrowDataId;
    }

    public String getBorrowDataName() {
        return borrowDataName;
    }

    public void setBorrowDataName(String borrowDataName) {
        this.borrowDataName = borrowDataName;
    }
}
