package com.jisucloud.clawler.regagent.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

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
	
	private static X509TrustManager createX509TrustManager() {
		X509TrustManager trustManager = new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				 return new X509Certificate[0];
			}
	       };
		return trustManager;
	}

	public static OkHttpClient createOkHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).cookieJar(new PersistenceCookieJar());
		builder.sslSocketFactory(createSSLSocketFactory(), createX509TrustManager());
		builder.hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		return builder.build();
	}

	public static OkHttpClient createOkHttpClientWithRandomProxy() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true)
				.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("http-dyn.abuyun.com", 9020)))
				.proxyAuthenticator(new Authenticator() {
					@Override
					public Request authenticate(Route route, Response response) throws IOException {
						if (response.request().header("Proxy-Authorization") != null) {
							// Give up, we've already failed to authenticate.
							return null;
						}

						String credential = Credentials.basic("H6224X2YF291C2AD", "2EADA65DEE87F60C");
						return response.request().newBuilder().header("Proxy-Authorization", credential).build();
					}
				}).cookieJar(new PersistenceCookieJar());
		builder.sslSocketFactory(createSSLSocketFactory(), createX509TrustManager());
		builder.hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		return builder.build();
	}
}
