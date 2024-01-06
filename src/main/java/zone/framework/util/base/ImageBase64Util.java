package zone.framework.util.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class ImageBase64Util {
	/**
	 * 根据Base64编码生成原图
	 */
	public static void makeOriginalImg(String base64str,String webPath, String fileName) {
		String osName = System.getProperties().getProperty("os.name");
		String realPath = "D:/home/ftphome/imgs/";
		if (osName != null && osName.toLowerCase().indexOf("linux") > -1) {
            realPath = "/home/ftphome/imgs/";
        } 
		// 生成原图文件
		base642Image(base64str, webPath + fileName);
		base642Image(base64str, realPath + fileName);
	}

	/**
	 * 将base64的字符串转换为图片
	 */
	private static void base642Image(String base64str, String filePath) {
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			//避免文件不存在而报错
			File file = new File(filePath);
			File fileParent = file.getParentFile();
			if(!fileParent.exists()){
				fileParent.mkdirs();
			}
			
			byte[] b = decoder.decodeBuffer(base64str.replace("data:image/png;base64,", ""));
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			out = new FileOutputStream(filePath);
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
