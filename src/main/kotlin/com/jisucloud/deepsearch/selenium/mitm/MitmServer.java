package com.jisucloud.deepsearch.selenium.mitm;

import java.io.File;
import java.net.InetSocketAddress;
import java.security.Security;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.openqa.selenium.Proxy;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.mitm.CertificateAndKeySource;
import net.lightbody.bmp.mitm.KeyStoreCertificateSource;
import net.lightbody.bmp.mitm.KeyStoreFileCertificateSource;
import net.lightbody.bmp.mitm.RootCertificateGenerator;
import net.lightbody.bmp.mitm.TrustSource;
import net.lightbody.bmp.mitm.keys.ECKeyGenerator;
import net.lightbody.bmp.mitm.manager.ImpersonatingMitmManager;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class MitmServer {

	public static void main(String[] args) throws Exception {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		//proxy.setTrustAllServers(true);
		//proxy.setMitmManager(mitmManager);
//		proxy.enableEmptyWhitelist(200);
//		proxy.addWhitelistPattern(".*\\.png");
//		proxy.addWhitelistPattern(".*\\.gif");
//		proxy.addWhitelistPattern(".*\\.jpg");
//		proxy.addWhitelistPattern(".*\\.jpeg");
//		proxy.addWhitelistPattern(".*localhost.*");
//		proxy.addWhitelistPattern(".*127.0.0.1.*");
		proxy.start(8119);
		
		
		
		// enable more detailed HAR capture, if desired (see CaptureType for the
		// complete list)
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
//		proxy.addRequestFilter(new RequestFilter() {
//			@Override
//			public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents,
//					HttpMessageInfo messageInfo) {
//				System.out.println("------------------------request-------------------------");
//				System.out.println("url:" + messageInfo.getOriginalUrl());
//				System.out.println("isHttps:" + messageInfo.isHttps());
//				System.out.println("method:" + messageInfo.getOriginalRequest().method());
//				System.out.println("headers:" + messageInfo.getOriginalRequest().headers());
//				System.out.println("contentType:" + contents.getContentType());
//				System.out.println("textContents:" + contents.getTextContents());
//				System.out.println("decoderResult:" + messageInfo.getOriginalRequest().decoderResult().toString());
//				return null;
//			}
//		});

		// responses are equally as simple:
//		proxy.addResponseFilter(new ResponseFilter() {
//			@Override
//			public void filterResponse(HttpResponse response, HttpMessageContents contents,
//					HttpMessageInfo messageInfo) {
//				System.out.println("------------------------response-------------------------");
//				System.out.println("url:" + messageInfo.getOriginalUrl());
//				System.out.println("status:" + response.status());
//				System.out.println("headers:" + response.headers());
//				System.out.println("decoderResult:" + response.decoderResult());
//				System.out.println("contentType:" + contents.getContentType());
//				System.out.println("textContents:" + contents.getTextContents());
//			}
//		});
		
		// // get the Selenium proxy object
		// HttpsProxy httpsProxy = new HttpsProxy("127.0.0.1", 8119);
		// ChromeAjaxListenDriver chromeAjaxListenDriver =
		// HeadlessUtil.getChromeDriver(true, httpsProxy, null);
		// try {
		// chromeAjaxListenDriver.get("https://www.baidu.com/");
		// Thread.sleep(10000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (chromeAjaxListenDriver != null) {
		// chromeAjaxListenDriver.quit();
		// }
		// proxy.stop();
		// }
	}
}
