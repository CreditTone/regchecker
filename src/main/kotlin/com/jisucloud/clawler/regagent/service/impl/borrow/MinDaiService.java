package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@UsePapaSpider
public class MinDaiService implements PapaSpider {

    private ChromeAjaxListenDriver chromeDriver;
    private boolean checkTel = false;

//    public static void main(String[] args) throws InterruptedException {
//        System.out.println(new MinDaiService().checkTelephone("18210538513"));
//        System.out.println(new MinDaiService().checkTelephone("18369630455"));
//    }

    @Override
    public String message() {
        return "民贷天下，是一家专注为出借人和借款人提供网络借贷信息中介服务的互联网金融服务平台，通过借贷信息撮合服务为借款人快速、专业地解决资金需求，为出借人提供高效的资金回报。";
    }

    @Override
    public String platform() {
        return "mindai";
    }

    @Override
    public String home() {
        return "bank.mindai.com";
    }

    @Override
    public String platformName() {
        return "民贷天下";
    }

    @Override
    public String[] tags() {
        return new String[]{"理财", "P2P" , "借贷"};
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            chromeDriver = HeadlessUtil.getChromeDriver(true, null, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
            String url = "https://bank.mindai.com/password-find.html";
            chromeDriver.setAjaxListener(new AjaxListener() {

                @Override
                public String matcherUrl() {
                    return "api.mindai.com";
                }

                @Override
                public void ajax(Ajax ajax) throws Exception {
                    System.err.println(ajax.getResponse());
                    //{code: "0000", message: "成功", data: {isRegistered: "0", req_num: 0}, timestamp: 1560244994914}
                    checkTel = !ajax.getResponse().contains("isRegistered\":\"0\"");
                }

                @Override
                public String[] blockUrl() {
                    return new String[]{"common/captcha?"};
                }

				@Override
				public String fixPostData() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
            });
            chromeDriver.get(url);
            Thread.sleep(3000);
            chromeDriver.findElementByCssSelector("#userName").sendKeys("18701666062");
            chromeDriver.findElementByCssSelector("#imgCode").click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (chromeDriver != null) {
                chromeDriver.quit();
            }
        }
        return checkTel;
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
