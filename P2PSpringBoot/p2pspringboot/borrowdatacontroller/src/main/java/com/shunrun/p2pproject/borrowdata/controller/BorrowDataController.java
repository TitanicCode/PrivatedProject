package com.shunrun.p2pproject.borrowdata.controller;




import com.alibaba.dubbo.config.annotation.Reference;
import com.common.fastdfs.util.FastdfsClient;
import com.shunrun.p2pproject.borrowdata.pojo.BorrowData;
import com.shunrun.p2pproject.borrowdata.service.BorrowDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jackiechan on 18-7-18/下午4:04
 */
@RestController
@RequestMapping("/borrow")
public class BorrowDataController {
    //这里的FastdfsClient是引入的第三方插件，所以源码中没有spring注解，所以想要自动注入需要在xml文件中配置<bean>或者在springboot配置类中配置该对象
    @Autowired
    private FastdfsClient fastdfsClient;
    @Reference(version = "1.0" ,application = "${dubbo.application.id}")
    private BorrowDataInterface borrowDataInterface;
    @PostMapping("/addborrowdata")
    public BorrowData addBorrowData(BorrowData borrowData, MultipartFile file1, MultipartFile file2) {
        //上传文件需要注意使用MultipartFile类型接收
        //在pojo中引入文件属性时，不要把所有的文件放到一个list集合中，因为一旦你顺序搞错就尴尬了，所以一个文件定义一个属性，分别接收
        //之前我们都会新建webapp/uoload或者webapp/webinf用于在服务器上存储文件，但是这样有个弊端，就是一旦重新部署项目，你上传的文件就全消了
        //所以在此可以使用fast上传工具

        //获取文件的后缀名
        //如何获取byte[]
        try {
            byte[] bytes = file1.getBytes();
            String name = file1.getName();
            String file1name = file1.getOriginalFilename();
            System.out.println(  );
            String address = fastdfsClient.upload_file(bytes, file1name.substring(file1name.lastIndexOf(".")+1), null);
            borrowData.setFczPic(address);
            //第二个图片
            bytes = file2.getBytes();
            file1name = file2.getOriginalFilename();
            address = fastdfsClient.upload_file(bytes, file1name.substring(file1name.lastIndexOf(".")+1), null);
            borrowData.setOtherData(address);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //插件化开发 移动端 --->QQ QQ空间
        return borrowData;
    }


}
