package com.jisucloud.clawler.regagent.util;

import me.kagura.JJsoup;
import me.kagura.Session;

public class JJsoupUtil {
    public static boolean useProxy = false;

    public static Session newProxySession() {
        if (useProxy) {
            return JJsoup.newSession().proxy("http-dyn.abuyun.com", 9020);
        }
        return JJsoup.newSession();
    }

}
