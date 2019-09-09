package com.jisucloud.clawler.regagent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.impl.b2c.YouZanSpider;
import com.jisucloud.clawler.regagent.service.impl.knowledge.ZhiHuSpider;
import com.jisucloud.clawler.regagent.service.impl.life.BaiduSpider;
import com.jisucloud.clawler.regagent.service.impl.life.DaZhongDianPingSpider;
import com.jisucloud.clawler.regagent.service.impl.life.MeiTuanSpider;
import com.jisucloud.clawler.regagent.service.impl.life.QiHu360Spider;
import com.jisucloud.clawler.regagent.service.impl.life._58Spider;
import com.jisucloud.clawler.regagent.service.impl.news.MeiTuWangSpider;
import com.jisucloud.clawler.regagent.service.impl.news.QuTouTiaoSpider;
import com.jisucloud.clawler.regagent.service.impl.news.TouTiaoSpider;
import com.jisucloud.clawler.regagent.service.impl.news.ZhongGuanCunZaiXianSpider;
import com.jisucloud.clawler.regagent.service.impl.pay.LaKaLaSpider;
import com.jisucloud.clawler.regagent.service.impl.pay.YunFuTongSpider;
import com.jisucloud.clawler.regagent.service.impl.saas.YunZhiJiaSpider;
import com.jisucloud.clawler.regagent.service.impl.social.MainMainSpider;
import com.jisucloud.clawler.regagent.service.impl.social.QQSpider;
import com.jisucloud.clawler.regagent.service.impl.social.ShiJiJiaYuanSpider;
import com.jisucloud.clawler.regagent.service.impl.social.WeiXinSpider;
import com.jisucloud.clawler.regagent.service.impl.trip.DiDiSpider;
import com.jisucloud.clawler.regagent.service.impl.trip.QunarSpider;
import com.jisucloud.clawler.regagent.service.impl.trip.Web12306Spider;
import com.jisucloud.clawler.regagent.service.impl.util.MingPianQuanNengWangSpider;
import com.jisucloud.clawler.regagent.service.impl.util.QiChaChaSpider;
import com.jisucloud.clawler.regagent.service.impl.util.XunLeiSpider;
import com.jisucloud.clawler.regagent.service.impl.video.LeTVSpider;
import com.jisucloud.clawler.regagent.service.impl.video.PPTVSpider;
import com.jisucloud.clawler.regagent.service.impl.work.HighpinSpider;
import com.jisucloud.clawler.regagent.service.impl.work.LiePinWangSpider;

import lombok.Data;

@Data
public class TempTaskA {

	@SuppressWarnings("unchecked")
	public static Set<Class<? extends PapaSpider>> taska_clzs = Sets.newHashSet(
			DaZhongDianPingSpider.class, 
			MeiTuanSpider.class, 
			_58Spider.class,
			QQSpider.class,
			WeiXinSpider.class,
			BaiduSpider.class
			);
	
	private String telephone;
	
	private Map<String, Boolean> result = new HashMap<String, Boolean>();
	
	public TempTaskA(String telephone) {
		this.telephone = telephone;
		for (Class<? extends PapaSpider> clz : taska_clzs) {
			try {
				PapaSpider papaSpider = clz.newInstance();
				PapaSpiderConfig papaSpiderConfig = clz.getAnnotation(PapaSpiderConfig.class);
				result.put(papaSpiderConfig.platform(), papaSpider.checkTelephone(telephone));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
