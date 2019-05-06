package com.jisucloud.clawler.regagent.service;

import java.util.Map;

public interface PapaSpider {

    String message();

    String platform();

    String platformName();

    boolean checkTelephone(String account);

    boolean checkEmail(String account);

    Map<String, String> getFields();

}