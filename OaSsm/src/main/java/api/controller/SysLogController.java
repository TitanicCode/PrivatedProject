package api.controller;

import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.service.sys.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/2.
 */
@RestController
@RequestMapping("/api")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /***********************查询**********************/
    //分页模糊查询SysConfigCustom
    //TODO@ResponseBody
    @RequestMapping("/sys/selectAllSysLogCustom")
    public PaginationRTDto selectAllSysConfigCustom(@RequestParam Map<String,Object> SysLogBootstrapRequestData){
        System.out.println(SysLogBootstrapRequestData);
        DataMapPackage dataMapPackage=new DataMapPackage(SysLogBootstrapRequestData);
        return sysLogService.selectAllSysLogCustom(dataMapPackage);
    }

}
