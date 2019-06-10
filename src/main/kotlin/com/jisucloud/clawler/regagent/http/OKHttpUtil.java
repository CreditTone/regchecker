package com.jisucloud.clawler.regagent.http;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;


public class OKHttpUtil {

	private static SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {
		}

		return ssfFactory;
	}
	
	@SuppressWarnings("deprecation")
	public static OkHttpClient createOkHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true)
				.cookieJar(new PersistenceCookieJar());
        builder.sslSocketFactory(createSSLSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
	}
}
