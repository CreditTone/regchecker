package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class HuijiaChiFanSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "回家吃饭是一款家庭厨房共享APP，致力于在社区里发掘有时间、愿分享的民间厨艺达人，通过配送、上门自取等方式，给忙碌的上班族提供安心饭菜。";
	}

	@Override
	public String platform() {
		return "huijiachi";
	}

	@Override
	public String home() {
		return "huijiachi.com";
	}

	@Override
	public String platformName() {
		return "回家吃饭";
	}

	@Override
	public String[] tags() {
		return new String[] {"o2o", "外卖"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306966", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://user.mapi.jiashuangkuaizi.com/Passport/userLogin";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("_screen", "480x800")
	                .add("password", "ffgasdggg")
	                .add("coordinate", "110.047614,24.375625")
	                .add("_version", "3.9.7")
	                .add("_platform", "Android")
	                .add("channel", "huijiachifan")
	                .add("_device", "awift08:00:27:17:3c:e3")
	                .add("user_coordinate", "110.047614,24.375625")
	                .add("_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
	                .add("phone", account)
	                .add("is_password", "1")
	                .add("_osversion", "4.4.2")
	                .add("_cityid", "")
	                .add("_build", "58")
	                .add("dif", "q7jlEeWLt7OHJ/Z+1d72N7b3GnYnpVxI+FbKz/aOpi+Sp1f5tMdKEogtV1v+EYZegOzOJuaURNzjSIqXNzZvY7/SySCWMhrpuzMigW1kkVc/ylsJ2x7DlN7eTcYQ6RLz1wnD6tlsAfY02EtxrQTNzQ84z0zmWIbxFhZ+o4MUMopEQVeyQmY42QaJEpRJEYbkdGUf8MrwzHivGPqRX2fPWZyTruSl7sMaRhhtAmiPkGg5JNBZLF1XBBhZFDBAN56nvhQ5cv+wgTgMWI9Vj8aFoAfTHb64RmZqp/d1JMIySRmJyRCFmJlvBr1kOCcll+hme6cJyy8Aj0QO2IkNukjicI1+K3v9EldQh3lTmDlvpKvK2ge2aUUCWI+qxvlQd2mAOn/N0WttunX18FM9PUzk5PnuHwN1TVa/edSqHQZyGgm+sN5ZXTfhZUT7XQqJLavD2A/mkg3rRAEP0Fg3nqff416F7D9vCQadJIowPRphWEUe611GUBPYefFPt+eDCgXjD4YzfLDpnnfLK+ZuV0csnpF0vX5ljln7++2dY+qeU0uydZEaYRt6AW93tNM3cOfyUMHJpI7i1eugbRQSAcGHb+OTlvhKylCIWZBPRPWNkOkCkD2nxkm+hVTJIcALeYJl0GZXM7vR/p2Llgd9++74bvjRd9ucjNwJj9pc5kjXiGYVH1Nb/bSXqbdySNZppgIPrRS14nZikmn77CAo2qE9YEXihFwLZUL+QXbdQFJtvlhthZcmZID0ASOn1rsbSPqUaE/akiTbdarrx9E8pbBkuEVUPVdWQS+JYupBGonc0kqAyqPbEjPEB1x1R2cQ8kyz1OMXIRKlqghqU6ij92s7aJz27K6AYN3BNcGaFGLpVihZm0TCjNVR6Lf5Wh8zd7gdC7pyJlWhkVIy/G/vdbWzDSc0YbmMqLufnQ2ejgHl9zV/PeEi6yWQ8+eIPYJf+cxnJprDyPuswYB5Fsxe6xxXQh4humCBad681WanVranFdfHek9nGKHRtFjJITxA7qqnQgHO44/tNBu6OhNVxSKhKHonTYJlrs2j2C3fnnEzq2CYqXgGtvNXdOs8B0y53IYaeEszTvxRy/vij/xOignqEMtEAM2ABQXtXS3iNWbbCM8XBqfcWf3uQ9FRTw9KQytwbDqey91SugrC6PbeAcslh1Ql2M53mRPOH/tOwYCphf8=")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "okhttp/3.2.0")
					.addHeader("sign", "0da785081b92a9e3b5c78ed91de8e1e3")
					.addHeader("Host", "user.mapi.jiashuangkuaizi.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			System.out.println(res);
			if (res.contains("不存在")) {
				return false;
			}
			if (res.contains("密码")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
