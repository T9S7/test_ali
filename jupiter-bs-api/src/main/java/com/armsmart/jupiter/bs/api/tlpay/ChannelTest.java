package com.armsmart.jupiter.bs.api.tlpay;
import com.allinpay.sdk.OpenClient;
import com.allinpay.sdk.bean.BizParameter;
import com.allinpay.sdk.bean.OpenConfig;
import com.allinpay.sdk.bean.OpenResponse;
import com.allinpay.sdk.util.SecretUtils;

import org.junit.*;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class ChannelTest {

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
		final OpenConfig oc = new OpenConfig(url, appId, secretKey, privateKeyPath, pwd, tlPublicKey);
		try {
			client = new OpenClient(oc);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getProductList() {
		final BizParameter param = new BizParameter();
		param.put("pageSize", "10");
		param.put("pageNo", "1");
		try {
			final OpenResponse response = client.execute("allinpay.top.biz.productList", param);
			if ("000000".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
			assertTrue("10000".equals(response.getCode()));
		} catch (final Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void getProductDetail() {
		final BizParameter param = new BizParameter();
		param.put("productId", "1211849747824914433");
		try {
			final OpenResponse response = client.execute("allinpay.top.biz.productDetail", param);
			if ("000000".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
			assertTrue("10000".equals(response.getCode()));
		} catch (final Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}


	@Test
	public void productOpenApply() {
		final BizParameter param = new BizParameter();
		param.put("channelId", "10000");
		param.put("productId", "1267762600184233985");
		param.put("appId", "1289098771380158466");
//		param.put("organCode", "9999999999");
		param.put("goodsId", "1267764136633286658");
		try {
			final OpenResponse response = client.execute("allinpay.top.biz.productOpenApply", param);
			if ("000000".equals(response.getSubCode())) {
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
		param.put("channelId", 10000L);
		param.put("sellerOrgCode", "9999995500");
		param.put("sellerOrgMgr", "susan");
		param.put("purchaserId", "082004021001186");
		param.put("target", "productList");
		//param.put("target", "openProductList");
		//param.put("target", "productListMobile");
		//param.put("target", "openProductListMobile");
		try {
			final OpenResponse response = client.execute("allinpay.top.biz.productApplyUrl", param);
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
	public void tokenOld() {
		final BizParameter param = new BizParameter();
		param.put("channelId", "10000");
		param.put("channelUserId", "10086");
		param.put("purchaserId", "082004021001186");
		param.put("target", "goodsServer");
		//param.put("target", "openProduct");
		//param.put("target", "goodsServerMobile");
		//param.put("target", "openProductMobile");
		try {
			final OpenResponse response = client.execute("allinpay.top.biz.productApplyUrl", param);
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
