package cn.lstf666.filmcomment.service.impl;

import cn.lstf666.filmcomment.service.OssService;
import cn.lstf666.filmcomment.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @program: guli_parent
 * @description: 实现类
 * @author: Restorff
 * @create: 2020-11-30 17:37
 **/
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        try{


        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName =ConstantPropertiesUtils.BUCKET_NAME;

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 获取文件名称
        String originalFilename = "";


        String datePath = new DateTime().toString("yyyy/MM/dd");
        originalFilename += datePath + "/" + UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
        // 获取文件流
            InputStream inputStream = file.getInputStream();
// 创建PutObjectRequest对象。

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

// 上传文件。
        ossClient.putObject(bucketName, originalFilename,inputStream);

// 关闭OSSClient。
        ossClient.shutdown();

//            https://edu-guli-lstf.oss-cn-chengdu.aliyuncs.com/G1.jpg
        // 手动拼接上传到阿里云的oss路径
            String url = "https://"+bucketName+"."+endpoint+"/"+originalFilename;
            return url;
        }catch (Exception e){
            e.printStackTrace();
        }
    return null;
    }
}
