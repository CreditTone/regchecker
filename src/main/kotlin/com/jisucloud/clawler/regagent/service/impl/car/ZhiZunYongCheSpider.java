package com.jisucloud.clawler.regagent.service.impl.car;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ZhiZunYongCheSpider implements PapaSpider {

	@Override
	public String message() {
		return "至尊用车是至尚威汽车服务（深圳）有限公司斥巨资打造的连锁即时用车服务品牌，其前身是至尊汽车租赁股份有限公司，为消费者即时提供代驾、接送、租车、违章查缴等服务。";
	}

	@Override
	public String platform() {
		return "top1";
	}

	@Override
	public String home() {
		return "top1.cn";
	}

	@Override
	public String platformName() {
		return "至尊用车";
	}

	@Override
	public String[] tags() {
		return new String[] {"租车"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhiZunYongCheSpider().checkTelephone("18720982607"));
//		System.out.println(new ZhiZunYongCheSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.top1.cn/home/Reg.aspx?act=check_user_reg_small&clientid=MobilePhoneNo&MobilePhoneNo="+account+"&_=" + System.currentTimeMillis();
			Response response = Jsoup.connect(url)
//					.header("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
//					.header("Host", "www.top1.cn")
//					.header("Referer", "https://www.top1.cn/home/Reg.aspx")
					.execute();
			String res = StringUtil.unicodeToString(response.body());
			if (res.contains("已存在")) {
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
