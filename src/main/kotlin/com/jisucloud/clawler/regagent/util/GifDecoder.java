package com.jisucloud.clawler.regagent.util;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class GifDecoder {

	/**
	 * 获取GIF图片一帧图片 - 同步执行
	 * 
	 * @param src
	 *            源图片路径
	 * @param target
	 *            目标图片路径
	 * @param frame
	 *            获取第几帧
	 * @throws Exception
	 */
	public static byte[] getGifOneFrame(byte[] src, int frame) throws Exception {
		IMOperation op = new IMOperation();
		String path = "gif/"+UUID.randomUUID()+".png";
		FileUtils.writeByteArrayToFile(new File(path), src);
		op.addImage(path + "[" + frame + "]");
		String target = "gif/"+UUID.randomUUID()+".gif";
		op.addImage(target);
		ConvertCmd cmd = new ConvertCmd();
		cmd.setAsyncMode(false);
		cmd.run(op);
		byte[] result = FileUtils.readFileToByteArray(new File(target));
//		new File(target).delete();
//		new File(path).delete();
		return result;
	}
}
