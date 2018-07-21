package api.dto;

import api.pojo.sys.SysMenu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */
public class SysMenuCustomDto extends SysMenu implements Serializable{
    private static final long serialVersionUID = 1L;
    private String parentName;
    private Boolean open;
    private List<SysMenuCustomDto> list;

    public SysMenuCustomDto() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<SysMenuCustomDto> getList() {
        return list;
    }

    public void setList(List<SysMenuCustomDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SysMenuCustomDto{" +
                "parentName='" + parentName + '\'' +
                ", open=" + open +
                ", list=" + list +
                '}';
    }
}
