package com.jisucloud.clawler.regagent.service.impl.money;

import org.jsoup.Connection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;


@UsePapaSpider
public class ZhongAnBaoXianSpider extends PapaSpider {

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "众安保险，致力于给所有用户提供更放心的保障！众安会帮您降低家庭意外风险，会帮您降低看病的医疗费用，会给您提供各种必要的人性金融保险服务！";
	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "zhonganbx";
	}

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return "zhongan.com";
	}

	@Override
	public String platformName() {
		// TODO Auto-generated method stub
		return "众安保险";
	}

	@Override
	public boolean checkTelephone(String account) {
		 try {
			 Session session = JJsoup.newSession();
	            //此接口为老版本v1.1.1接口
	            String body = session.connect("https://app.zhongan.com/za-clare/app/user/sendCaptcha")
	                    .method(Connection.Method.POST)
	                    .requestBody("{\"phoneNo\":\""+account+"\",\"type\":\"3\"}")
	                    .header("Content-type", "application/json;charset=utf-8")
	                    .execute().body();
	            return !body.contains("账号不存在");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
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
		// TODO Auto-generated method stub
		return new String[] { "理财", "保险" };
	}

	@Override
	public Set<String> getTestTelephones() {
		// TODO Auto-generated method stub
		return Sets.newHashSet("13261165342", "18210538513");
	}

}
