package com.cdm.idlefish.utils;


import android.util.Log;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;


import java.io.IOException;
import java.util.UUID;


/**
 * ******************************************************************
 *
 * @文件名称:CDNUtil
 * @作者 : caodm
 * @文件描述: 七牛平台上传头像接口
 * @创建时间: 2016/7/26日
 * ******************************************************************
 */
public class CDNUtil {

    public static final String TAG = "CDNUtil";
    public static final int TIME = 6000;

    private CDNUtil() {
    }

    private static CDNUtil instance;
    private Auth auth;

    public static CDNUtil getInstance() {
        if (instance == null) {
            instance = new CDNUtil();
        }
        return instance;
    }


    // 创建上传对象
    private UploadManager uploadManager = new UploadManager();

    // 简单上传，使用默认策略，只需要设置上传的空间名
    private String getUpToken() {
        // 密钥配置
        auth = Auth.create(ConstantUtil.qnappid, ConstantUtil.qnappsecret);
        return auth.uploadToken(ConstantUtil.qnbucket);
    }

    // 覆盖上传
    private String getUpToken(String destFileName) {
        // <bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许"修改"，已存在同名资源则会被本次覆盖。
        // 如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
        // 第三个参数是token的过期时间// 密钥配置
        Auth auth = Auth.create(ConstantUtil.qnappid, ConstantUtil.qnappsecret);
        return auth.uploadToken(ConstantUtil.qnbucket, destFileName, 3600,
                new StringMap().put("insertOnly", 0));
    }

    /**
     * url 上传图片 eg.三方登陆头像上传
     *
     * @param url
     * @param fileName
     */
    public void upload(String url, String fileName) {
        //获取空间管理器
        if (auth == null) {
            auth = Auth.create(ConstantUtil.qnappid, ConstantUtil.qnappsecret);
        }
        BucketManager bucketManager = new BucketManager(auth);
        try {
            // 要求url可公网正常访问BucketManager.fetch(url, bucketName, key);
            // @param url 网络上一个资源文件的URL
            // @param bucketName 空间名称
            // @param key 空间内文件的key[唯一的]
            DefaultPutRet putret = bucketManager.fetch(url, ConstantUtil.qnbucket, fileName);
            System.out.println(putret.key);
            System.out.println("succeed upload image");
        } catch (QiniuException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 上传图片
     *
     * @param filename     待上传的文件名,需要传入可以被访问的完整路径
     * @param destFileName 上传至云端时使用的文件名,不要包含路径
     * @param isUpdate     本次上传是否是更新操作。true表示更新。
     */
    public void upload(String filename, String destFileName, boolean isUpdate) throws IOException {
        Log.i(TAG, " filename" + filename + "  ,destFileName = " +
                destFileName + " ,isUpdate=" + isUpdate);
        try {
            // 密钥配置
            String extName = "";
            if (filename.lastIndexOf(".") > -1) {
                extName = filename.substring(filename.lastIndexOf(".") + 1);
            }
            if (destFileName == null || destFileName.length() == 0) {
                destFileName = UUID.randomUUID().toString().replaceAll("-", "");
                if (extName.length() > 0) {
                    destFileName = destFileName + "." + extName;
                }
            }
            // 根据用户需要获取token
            String token = isUpdate ? getUpToken(destFileName) : getUpToken();
            // 调用put方法上传
            Response res = uploadManager.put(filename, destFileName, token);
            // 打印返回的信息
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的下载链接
     *
     * @param destFileName 待下载云端文件的文件名,不包含路径及URI
     */
    public String getRemoteDownloadURL(String destFileName, int timeout) {
        // 密钥配置
        Auth auth = Auth.create(ConstantUtil.qnappid, ConstantUtil.qnappsecret);
        // 构造私有空间的需要生成的下载的链接
        String URL = "http://" + ConstantUtil.qnbucketdomain + "/" + destFileName;
        // 调用privateDownloadUrl方法生成下载链接,第二个参数是Token的过期时间
        String downloadURL = auth.privateDownloadUrl(URL, timeout);
        Log.i(TAG, "获取到云端下载链接 " + downloadURL + "  ,超时时间 " + timeout + "秒");
        return downloadURL;
    }

    /**
     * 删除文件
     *
     * @param destFileName 待删除的云端文件名
     */
    public boolean deleteRemoteFile(String destFileName) {
        try {
            // 密钥配置
            Auth auth = Auth.create(ConstantUtil.qnappid, ConstantUtil.qnappsecret);
            // 实例化一个BucketManager对象
            BucketManager bucketManager = new BucketManager(auth);
            // 调用delete方法移动文件
            bucketManager.delete(ConstantUtil.qnbucket, destFileName);
        } catch (QiniuException e) {
            // 捕获异常信息
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
