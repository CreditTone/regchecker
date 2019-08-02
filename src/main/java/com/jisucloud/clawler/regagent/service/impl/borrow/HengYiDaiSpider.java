package com.jisucloud.clawler.regagent.service.impl.borrow;


import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HengYiDaiSpider extends PapaSpider {
	
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

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
			FormBody formBody = new FormBody
	                .Builder()
	                .add("jsonParams", encrypt(jsonParams))
	                .build();
			Request request = new Request.Builder().url("https://hyd.hengchang6.com/hyd/services/hydUser/checkPhone")
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.1.1; zh-cn; Nexus 5 Build/LMY48M) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String body = decrypt(response.body().string());
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