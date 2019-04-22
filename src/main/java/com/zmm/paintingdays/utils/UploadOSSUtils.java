package com.zmm.paintingdays.utils;

import com.aliyun.oss.OSSClient;
import org.springframework.web.multipart.MultipartFile;

public class UploadOSSUtils {
	
	private static String endpoint="oss-cn-shanghai.aliyuncs.com";
	private static String accessKeyId="Nwh1FsDQrstVExU2";
	private static String accessKeySecret="LANDsFPT5fVNMBGdmVJMA1hCnuEQHS";
	private static String bucketName="painting-days";
	private static String picAddressHead="http://painting-days.oss-cn-shanghai.aliyuncs.com/";

	/**
	 * 上传单张图片到云端
	 * @return
	 * @throws Exception
	 */
	public static String uploadSinglePic(MultipartFile file,String fileName) throws Exception {
		String picId = KeyUtil.getKeyId();
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传文件流
		ossClient.putObject(bucketName, fileName+picId+".jpg", file.getInputStream());
		// 关闭client
		ossClient.shutdown();
		String path = picAddressHead+fileName+picId+".jpg";

		return path;
	}

}
