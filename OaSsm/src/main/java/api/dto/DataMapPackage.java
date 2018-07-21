package api.dto;

import api.utils.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/27.
 */
public class DataMapPackage extends LinkedHashMap<String,Object>{
    private static final long serialVersionUID = 1L;

    public  DataMapPackage(Map<String,Object> params){
        this.putAll(params);

        //分页参数
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Integer offset = Integer.parseInt(params.get("offset").toString());
        this.put("limit",limit);
        this.put("offset",offset);

        //防止SQL注入,凡是用户手动输入的参数都需要进行以下过滤
        String search = params.get("search")==null?"":params.get("search").toString();
        String order = params.get("order")==null?"":params.get("order").toString();
        String sort = params.get("sort")==null?"":params.get("sort").toString();
        this.put("search", SQLFilter.sqlInject(search));
        this.put("order", SQLFilter.sqlInject(order));
        this.put("sort", SQLFilter.sqlInject(sort));
    }

}
