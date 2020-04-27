package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

import com.deep007.spiderbase.DefaultHttpDownloader;
import com.deep007.spiderbase.request.PageRequest;
import com.deep007.spiderbase.request.PageRequestBuilder;
import com.deep007.spiderbase.response.Page;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "xiangshang360.com", 
		message = "向上金服(xiangshang360.com)-互联网金融协会会员,国内专业的互联网金融投资平台 ,注册资本1亿元,具备国家信息安全等级保护三级认证,平台用户超700+万,成交额破500亿,银行资金存管,投资体验升级.交易过程公开透明,诚信运营六周年。新手注册领1000元礼包。", 
		platform = "xiangshang360", 
		platformName = "向上金服", 
		tags = { "P2P" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class XiangShangJinFuSpider extends PapaSpider {
	
	private DefaultHttpDownloader downloader = createDefaultHttpDownloader();
	
	public boolean checkTelephone(String account) {
		String url = "https://www.xiangshang360.cn/xweb/register/checkMobile?mobile=" + account;
		try {
			PageRequest request = PageRequestBuilder.custom().url(url)
					.isPost()
					.header("User-Agent", CHROME_USER_AGENT)
					.header("Referer", "https://www.xiangshang360.cn/xweb/login")
					//.param("mobile", account)
					.build();
			Page page = downloader.download(request);
			String res = StringUtil.unicodeToString(page.getContent());
			System.out.println(res);
			if (res.contains("已注册")) {
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
