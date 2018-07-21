package api.dto;

import java.util.List;

/**
 * Created by Administrator on 2018/6/27.
 */
public class PaginationRTDto {
    private Long total;
    private Object rows;

    public PaginationRTDto() {
    }

    public PaginationRTDto(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PaginationRTDto{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
