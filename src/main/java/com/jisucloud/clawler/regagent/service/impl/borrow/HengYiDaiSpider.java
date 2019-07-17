package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Base64;
import java.util.Map;
import java.util.Set;
import org.jsoup.Connection;
import javax.crypto.spec.DESKeySpec;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import me.kagura.JJsoup;
import me.kagura.Session;

import java.security.SecureRandom;

@Slf4j
@UsePapaSpider(exclude = true , excludeMsg = "windows下加密算法不适用")
public class HengYiDaiSpider extends PapaSpider {

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "恒易贷是由北京网众共创科技有限公司推出的移动借贷产品智选APP,利用大数据、移动互联网等前沿技术，将海量丰富的各类互联网金融业务模式得以简单、直观的呈现给用户。";
	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "hengyidai";
	}

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return "credithc.com";
	}

	@Override
	public String platformName() {
		// TODO Auto-generated method stub
		return "恒易贷";
	}

	@Override
	public boolean checkTelephone(String account) {
		// TODO Auto-generated method stub
		try {
			String jsonParams = "{\n" + "                    \"utmCode\":\"302\",\n"
					+ "                    \"tokenId\":\"AYc2dV8e1cclgcgXGAfkFHkQ0mKLb5E5uc5nJRyMCGndp_jmcnukujMIi1tX_cE2VyjkFrO7LF4s7gWoviFzPHmw80OshDDcIo-VJ-_SK4DQj5Un79IrgNCPlSfv0iuA0I-VJ-_SK4DQj5Un79IrgNCPlSfv0iuA0I-VJ-_SK4DQOo77oXk_ZXZt\",\n"
					+ "                    \"userId\":\"\",\n" + "                    \"version\":\"2.9.9\",\n"
					+ "                    \"osVersion\":\"android_5.1.1\",\n" + "                    \"userPhone\":\""
					+ account + "\",\n" + "                    \"deviceCode\":\"358239055441820\",\n"
					+ "                    \"sourceType\":\"3\"\n" + "                }";
			Session session = JJsoup.newSession();
			// 老版本2.9.9接口，邦邦加固+DES加密
			String body = session.connect("https://hyd.hengchang6.com/hyd/services/hydUser/checkPhone").userAgent(
					"Mozilla/5.0 (Linux; U; Android 5.1.1; zh-cn; Nexus 5 Build/LMY48M) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
					.data("jsonParams", encrypt(jsonParams)).method(Connection.Method.POST).execute().body();
			body = decrypt(body);
			log.info("body:"+body);
			return body.contains("已注册");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] tags() {
		return new String[] { "P2P", "借贷" };
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252000", "18210538513", "18210538511");
	}

	public String decrypt(String data) throws Exception {
		String DES_KEY = "o9WL!惹@#8*3${'" + data + "'}D5m赢7&4(荡F*";
		byte[] bytes = Base64.getDecoder().decode(data.replaceAll("[\\s]*", ""));
		SecretKey key = generateSecret(DES_KEY);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
		return new String(cipher.doFinal(bytes));
	}
	
	private SecretKey generateSecret(String des_key) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec desKeySpec = new DESKeySpec(des_key.getBytes());
		return keyFactory.generateSecret(desKeySpec);
	}

	public String encrypt(String data) throws Exception {
		String DES_KEY = "o9WL!惹@#8*3${'" + data + "'}D5m赢7&4(荡F*";
		SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(DES_KEY.getBytes()));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
		return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes())).replaceAll("[\\s]+", "");
	}

}
