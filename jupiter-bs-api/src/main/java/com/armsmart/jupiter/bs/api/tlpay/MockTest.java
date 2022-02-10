package com.armsmart.jupiter.bs.api.tlpay;

import com.allinpay.sdk.OpenClient;
import com.allinpay.sdk.bean.BizParameter;
import com.allinpay.sdk.bean.OpenConfig;
import com.allinpay.sdk.bean.OpenResponse;
import com.allinpay.sdk.util.SecretUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class MockTest {

	protected OpenClient client;

	@Before
	public void configClient() {
		final String url = "http://test.allinpay.com/op/gateway";
		final String appId = "1283233906081939458";
		final String secretKey = "eMDVxpfvt1k8mIiCZvDLWx9wtJcdtVBQ";
		final String privateKeyPath = "1283233906081939458.pfx";
		final String pwd = "939458";
		final String tlPublicKey = "TLCert-test.cer";
//		final String url = "http://localhost:8888/gateway";
////		final String url = "http://localhost:8288/adapter";
//		final String appId = "661520093552836608";
//		final String secretKey = "CRcZWcErbbuOwwCPwsrqOtoIDwGmTJCa";
//		final String privateKeyPath = "D:\\key\\661520093552836608.pfx";
//		final String pwd = "123456";
//		final String tlPublicKey = "D:\\key\\TLCert-test.cer";
//		final String tlPublicKey = "E:\\openspace\\top-sdk-java\\src\\test\\resources/云开放平台测试证书.cer";
		final OpenConfig oc = new OpenConfig(url, appId, secretKey, privateKeyPath, pwd, tlPublicKey);
		try {
			client = new OpenClient(oc);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void mock() {
		final BizParameter param = new BizParameter();
		param.put("aaaa", "fdsfdsafdsadf");
		param.put("bbb", 2L);
		param.put("ccc", 1L);
		try {
			final OpenResponse response = client.execute("allinpay.top.mock.api", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
			assertTrue("10000".equals(response.getCode()));
		} catch (final Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void token() {
		final BizParameter param = new BizParameter();
		param.put("token", "05bdfbf9-4fe1-4c00-8ba7-fe837850d039");
		param.put("source", 2L);
		try {
			final OpenResponse response = client.execute("allinpay.yunst.memberService.getUserInfoThroughToken", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
			assertTrue("10000".equals(response.getCode()));
		} catch (final Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void mockJump() {
		final BizParameter param = new BizParameter();
		param.put("aaaa", "fdsfdsafdsadf");
		param.put("bbb", 2L);
		param.put("ccc", 1L);
		try {
//			final String url = client.concatUrlForServer("allinpay.top.common.jump", param, "https://www.baidu.com",
//					"661520093552836608");
//			System.out.println(url);
//			browser(url);
			client.execute("allinpay.top.common.jump", param);
		} catch (final Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void getCompanyInfo() {
		final BizParameter param = new BizParameter();
		param.put("companyId", "082003121000034");
		try {
			final OpenResponse response = client.execute("allinpay.top.info.getCompanyInfo", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOther() {
		System.out.println(SecretUtils.decryptAES("03770E9003CEF303C4B7B3EEA174D3AA4EAAEABA4B66C591EDEFC3D675AC45B9",
				"CmeIjpnKJuAtZQhJyYtdQSmZjstsaXId"));
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
