/**
 * create this file at 下午2:12:44 by renhd.
 */
package com.armsmart.jupiter.bs.api.tlpay;

import com.allinpay.sdk.OpenClient;
import com.allinpay.sdk.bean.OpenConfig;
import org.junit.Before;

import java.lang.reflect.Method;

/**
 * junit 测试demo示例
 *
 * @author 任海东 2020年1月10日
 *
 */
public class JuintTest {

	protected OpenClient client;


	@Before
	public void configClient() {
		final String url = "https://cloud.allinpay.com/gateway";
		final String appId = "1469220498779484162";
		final String secretKey = "wht6zLZRaydIEe7a9v6DSRdUO6M8tcI8";
//		final String privateKeyPath = "E:\\tl_pfx\\1469220498779484162.pfx";
		final String privateKeyPath = "/usr/local/jupiter/jupiter-bs/config/1469220498779484162.pfx";
		final String pwd = "484162";
//		final String tlPublicKey = "E:\\tl_pfx\\TLCert.cer";
		final String tlPublicKey = "/usr/local/jupiter/jupiter-bs/config/TLCert.cer";
//		final String url = "http://test.allinpay.com/op/gateway";
//		final String url = "http://localhost:8288/adapter";
//		final String appId = "661520093552836608";
//		final String secretKey = "CRcZWcErbbuOwwCPwsrqOtoIDwGmTJCa";
//		final String privateKeyPath = "/Users/renhd/work/cert/661520093552836608.pfx";
//		final String pwd = "123456";
//		final String tlPublicKey = "/Users/renhd/work/cert/TLCert-test.cer";
		final OpenConfig oc = new OpenConfig(url, appId, secretKey, privateKeyPath, pwd, tlPublicKey);
		try {
			client = new OpenClient(oc);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 打开默认浏览器，支持苹果和windows
	 *
	 * @param url
	 */
	protected void browser(final String url) {
		try {
			final String osName = System.getProperty("os.name");
			if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler  " + url);
			} else if (osName.startsWith("Mac OS")) {
				final Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
				final Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
