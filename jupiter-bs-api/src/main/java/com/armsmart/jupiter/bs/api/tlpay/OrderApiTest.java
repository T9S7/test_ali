/**
 * create this file at 下午4:35:35 by renhd.
 */
package com.armsmart.jupiter.bs.api.tlpay;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allinpay.sdk.bean.BizParameter;
import com.allinpay.sdk.bean.OpenResponse;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 通联开放平台行业分账产品 订单类接口测试demo 详细参考通商云开放平台接口规范
 *
 * @author 任海东 2020年1月10日
 *
 */
public class OrderApiTest extends JuintTest {

	/**
	 * 充值申请
	 */
	@Test
	public void depositApply() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 15);
		final Date date = calendar.getTime();
		final String orderExpireDatetime = sdf.format(date);

		final BizParameter param = new BizParameter();
		// 实名付（单笔）
		final HashMap<String, Object> realnamePay = new HashMap<>();
		realnamePay.put("bankCardNo", client.encrypt("6228480402637874321"));
		realnamePay.put("amount", 100); // 分

		// 收银宝网关
		final Map<String, Object> gatewayVsp = new HashMap<>();
		gatewayVsp.put("amount", 100L);
		gatewayVsp.put("paytype", "B2C");

		// 组装支付方式
		final HashMap<String, Object> payMethod = new HashMap<>();
//		payMethod.put("REALNAMEPAY", realnamePay);
		payMethod.put("GATEWAY_VSP", gatewayVsp);

		param.put("bizUserId", "test0002");
		param.put("bizOrderNo", System.currentTimeMillis() + "cz");
		param.put("accountSetNo", "200001");
		param.put("amount", 100L);
		param.put("fee", 0L);
		param.put("validateType", 1L);
		param.put("frontUrl", "http://ceshi.allinpay.com");
		param.put("backUrl", "http://ceshi.allinpay.com");
		param.put("orderExpireDatetime", orderExpireDatetime);
		param.put("payMethod", JSONObject.toJSON(payMethod));
		param.put("goodsName", "computer");
		param.put("industryCode", "1010");
		param.put("industryName", "保险代理");
		param.put("source", 2L);
		param.put("summary", "deposit");
		param.put("extendInfo", "this is extendInfo");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.depositApply", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 消费申请
	 */
	@Test
	public void consumeApply() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 15);
		final Date date = calendar.getTime();
		final String orderExpireDatetime = sdf.format(date);
		final BizParameter param = new BizParameter();
		// 支付方式
		// 实名付（单笔）
		final Map<String, Object> realnamePay = new HashMap<>();
		realnamePay.put("bankCardNo", client.encrypt("6228480402637874321"));
		realnamePay.put("amount", 200L);

		// 组装支付方式
		final HashMap<String, Object> payMethod = new HashMap<>();
		payMethod.put("REALNAMEPAY", realnamePay);

		param.put("payerId", "test0002");
		param.put("recieverId", "enterprise0001");
		param.put("bizOrderNo", System.currentTimeMillis() + "xf");
		param.put("amount", 200L);
		param.put("fee", 0L);
		param.put("validateType", 1L);

		// *** split rule
		final JSONArray splitRule = new JSONArray();

		final HashMap<String, Object> splitRule1 = new HashMap<>();
		splitRule1.put("bizUserId", "test0001");
		splitRule1.put("accountSetNo", "200001");
		splitRule1.put("amount", 50L);
		splitRule1.put("fee", 0L);
		splitRule1.put("remark", " 消费一级分账");

		final JSONArray splitRule2List1 = new JSONArray();
		final HashMap<String, Object> splitRule2List = new HashMap<>();
		splitRule2List.put("bizUserId", "renhd001");
		splitRule2List.put("accountSetNo", "200001");
		splitRule2List.put("amount", 20L);
		splitRule2List.put("fee", 0L);
		splitRule2List.put("remark", "消费二级分账");

		splitRule2List1.add(new JSONObject(splitRule2List));

		splitRule1.put("splitRuleList", splitRule2List1);
		splitRule.add(new JSONObject(splitRule1));

//		request.put("splitRule", splitRule);

		param.put("frontUrl", "http://ceshi.allinpay.com");
		param.put("backUrl", "http://ceshi.allinpay.com");
		param.put("orderExpireDatetime", orderExpireDatetime);
		param.put("payMethod", payMethod);
		param.put("goodsName", "computer");
		param.put("goodsDesc", "computer made in china");
		param.put("industryCode", "1010");
		param.put("industryName", "保险代理");
		param.put("source", 2L);
		param.put("summary", "consume");
		param.put("extendInfo", "商户上送扩展信息");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.consumeApply", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 托管代收申请（标准版）
	 */
	@Test
	public void agentCollectApply() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 15);
		final Date date = calendar.getTime();
		final String orderExpireDatetime = sdf.format(date);
		final BizParameter param = new BizParameter();
		// 支付方式
		// 实名付（单笔）
		final Map<String, Object> realnamePay = new HashMap<>();
		realnamePay.put("bankCardNo", client.encrypt("6228480402637874321"));
		realnamePay.put("amount", 30L);

		// 组装支付方式
		final HashMap<String, Object> payMethod = new HashMap<>();
		payMethod.put("REALNAMEPAY", realnamePay);

		// 收款列表
		final JSONArray receiverList = new JSONArray();
		final HashMap<String, Object> receiver1 = new HashMap<>();
		receiver1.put("bizUserId", "test0001");
		receiver1.put("amount", 30L);
		receiverList.add(new JSONObject(receiver1));

		param.put("payerId", "test0002");
		param.put("bizOrderNo", System.currentTimeMillis() + "ds");
		param.put("recieverList", receiverList);
		param.put("tradeCode", "1003");
		param.put("amount", 30L);
		param.put("fee", 0L);
		param.put("validateType", 1L); // 1 短信验证
		param.put("frontUrl", "http://ceshi.allinpay.com");
		param.put("backUrl", "http://ceshi.allinpay.com");
		param.put("orderExpireDatetime", orderExpireDatetime);
		param.put("payMethod", payMethod);
		param.put("goodsName", "computer");
		param.put("goodsDesc", "computer made in china");
		param.put("industryCode", "1010");
		param.put("industryName", "保险代理");
		param.put("source", 2L);
		param.put("summary", "consume");
		param.put("extendInfo", "this is extendInfo");
		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.agentCollectApply", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单笔托管代付（标准版）
	 */
	@Test
	public void signalAgentPay() {
		final BizParameter param = new BizParameter();
		// 源托管代收订单付款信息
		final JSONArray collectPayList = new JSONArray();
		final HashMap<String, Object> collect1 = new HashMap<>();
		collect1.put("bizOrderNo", "1578901808944ds");
		collect1.put("amount", 30L);
		collectPayList.add(new JSONObject(collect1));

		param.put("bizUserId", "test0001");
		param.put("accountSetNo", "200001");
		param.put("bizOrderNo", System.currentTimeMillis() + "df");
		param.put("collectPayList", collectPayList);
		param.put("tradeCode", "2003");
		param.put("amount", 30L);
		param.put("fee", 0L);
		param.put("backUrl", "http://ceshi.allinpay.com");
		param.put("summary", "consume");
		param.put("extendInfo", "this is extendInfo");
		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.signalAgentPay", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量托管代付（标准版）
	 */
	@Test
	public void batchAgentPay() {
		final BizParameter param = new BizParameter();
		// 源托管代收订单付款信息
		final JSONArray collectPayList = new JSONArray();
		final HashMap<String, Object> collect1 = new HashMap<>();
		collect1.put("bizOrderNo", "1578901712750ds");
		collect1.put("amount", 30L);
		collectPayList.add(new JSONObject(collect1));

		final HashMap<String, Object> pay1 = new HashMap<>();
		pay1.put("bizOrderNo", System.currentTimeMillis() + "df");
		pay1.put("collectPayList", collectPayList);
		pay1.put("bizUserId", "test0001");
		pay1.put("accountSetNo", "200001");
		pay1.put("backUrl", "http://ceshi.allinpay.com");
		pay1.put("amount", 30L);
		pay1.put("fee", 0L);

		final JSONArray batchPayList = new JSONArray();
		batchPayList.add(pay1);

		param.put("bizBatchNo", System.currentTimeMillis() + "ba");
		param.put("batchPayList", batchPayList);
		param.put("tradeCode", "2003");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.batchAgentPay", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提现申请
	 */
	@Test
	public void withdrawApply() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 15);
		final Date date = calendar.getTime();
		final String orderExpireDatetime = sdf.format(date);
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizOrderNo", System.currentTimeMillis() + "tx");
		param.put("accountSetNo", "200001");
		param.put("amount", 1L);
		param.put("fee", 0L);
		param.put("validateType", 1L);
		param.put("backUrl", "http://ceshi.allinpay.com");
		param.put("orderExpireDatetime", orderExpireDatetime);
		param.put("bankCardNo", client.encrypt("6228480402637874321"));
		param.put("bankCardPro", 0L);
		param.put("withdrawType", "D0");
		param.put("industryCode", "1010");
		param.put("industryName", "保险代理");
		param.put("source", 2L);
		param.put("summary", "withdraw");
		param.put("extendInfo", "this is extendInfo");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.withdrawApply", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 确认支付（后台+短信验证码）
	 */
	@Test
	public void payByBackSMS() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizOrderNo", "1578901712750ds");
		param.put("tradeNo", "");
		param.put("verificationCode", "11111"); // 测试环境 验证码 11111
		param.put("consumerIp", "192.168.11.11");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.payByBackSMS", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 确认支付（前台+短信验证码）
	 */
	@Test
	public void payBySMS() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizOrderNo", "1578897792888cz");
		param.put("verificationCode", "11111"); // 测试环境 验证码 11111
		param.put("consumerIp", "192.168.11.11");
		try {
			final String url = client.concatUrlParams("allinpay.yunst.orderService.payBySMS", param);
			System.out.println(url);
			browser(url);// 打开浏览器
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 确认支付（前台+短支付密码）
	 */
	@Test
	public void payByPwd() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizOrderNo", "15788977912321cz");
		param.put("tradeNo", "");
		param.put("jumpUrl", "http://ceshi.allinpay.com");
		param.put("consumerIp", "192.168.11.11");
		try {
			final String url = client.concatUrlParams("allinpay.yunst.orderService.payByPwd", param);
			System.out.println(url);
			browser(url);// 打开浏览器
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 冻结金额
	 */
	@Test
	public void freezeMoney() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizFreezenNo", System.currentTimeMillis() + "dj");
		param.put("accountSetNo", "200001");
		param.put("amount", 1);

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.freezeMoney", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解冻金额
	 */
	@Test
	public void unfreezeMoney() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizFreezenNo", "1578900930033dj");
		param.put("accountSetNo", "200001");
		param.put("amount", 1);

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.unfreezeMoney", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退款
	 */
	@Test
	public void refund() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("bizOrderNo", System.currentTimeMillis() + "tk");
		param.put("oriBizOrderNo", "1578884948956cz");
		param.put("amount", 1);
		param.put("refundType", "D0");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.refund", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询余额
	 */
	@Test
	public void queryBalance() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("accountSetNo", "200001");

		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.queryBalance", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询订单状态
	 */
	@Test
	public void getOrderDetail() {
		final BizParameter param = new BizParameter();
		param.put("bizOrderNo", "1578884948956cz");
		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.getOrderDetail", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询账户收支明细
	 */
	@Test
	public void queryInExpDetail() {
		final BizParameter param = new BizParameter();
		param.put("bizUserId", "test0002");
		param.put("accountSetNo", "200001");
		param.put("dateStart", "2020-01-10");
		param.put("dateEnd", "2020-01-17");
		param.put("startPosition", 1L);
		param.put("queryNum", 10L);
		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.queryInExpDetail", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重发支付短信验证码
	 */
	@Test
	public void resendPaySMS() {
		final BizParameter param = new BizParameter();
		param.put("bizOrderNo", "1578883586614xf");
		try {
			final OpenResponse response = client.execute("allinpay.yunst.orderService.resendPaySMS", param);
			if ("OK".equals(response.getSubCode())) {
				System.out.println(response.getData());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
