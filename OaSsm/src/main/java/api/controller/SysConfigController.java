package api.controller;


import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.dto.R;
import api.service.sys.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/2.
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {
//    @Autowired
//    private SysConfigService sysConfigService;
//    @GetMapping("/list")
//    public PaginationRTDto queryPage(@RequestParam Map<String,Object> params){
//        DataMapPackage query = new DataMapPackage(params);
//        return sysConfigService.queryPageList(query);
//    }
//    @PostMapping("/save")
//    public R addConfig(@RequestBody SysConfig sysConfig){
//        sysConfigService.addConfig(sysConfig);
//        return R.ok("添加成功");
//    }
//    @GetMapping("info/{id}")
//    public R queryObject(@PathVariable("id") Long id){
//        SysConfig sysConfig = sysConfigService.selectByPrimaryKey(id);
//        return R.ok().put("sysConfig",sysConfig);
//    }
//    @PostMapping("/update")
//    public R update(@RequestBody SysConfig sysConfig){
//        sysConfigService.update(sysConfig);
//        return R.ok("修改成功");
//    }
//    @PostMapping("/del")
//    public R deleteBatch(@RequestBody List<Long> ids){
//        for (Long id : ids) {
//            System.out.println(id);
//        }
//        sysConfigService.deleteBatch(ids);
//        return R.ok("删除成功");
//    }
}
