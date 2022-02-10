
package com.armsmart.jupiter.bs.api.error;


import com.armsmart.common.api.ErrorCode;

/**
 * 业务响应码定义
 *
 * @author wei.lin
 */
public enum NEError implements ErrorCode {
    WRONG_USERNAME_OR_PWD(10001, "用户名或密码错误"),
    USER_IS_DISABLED(10002, "该用户已被禁用"),
    USER_IS_NOT_EXIST(10003, "用户不存在"),
    USER_OLD_PWD_NOT_MATCH(10004, "旧密码错误"),
    USER_PWD_CHANGE_ERROR(10005, "密码修改失败"),
    USERNAME_IS_EXIST(10006, "用户名已存在"),

    ROLE_NOT_EXIST_OR_DEL(10101, "角色不存在或已删除"),
    ROLE_NAME_IS_EXIST(10102, "角色名称已存在"),
    ROLE_TYPE_IS_USED(10103, "该类型的角色已存在"),

    GET_ACS_FAILED(10301, "获取OSS临时授权失败"),
    COIN_LOAD_FAILED(10401, "区块链信息录入失败"),
    PUB_KEY_LOAD_FAILED(10402, "区块链配置信息录入失败"),

    GOOD_PHOTO_NOT_EXIST(10501,"物品图片不可为空"),
    COIN_PHOTO_URL_INVALID(10502, "高清图片地址无效"),

    REAL_PERSON_TOKEN_ABNORMAL(50009,"获取实人认证TOKEN异常"),
    REAL_PERSON_ABNORMAL(50010,"获取实人认证结果异常"),
    REAL_PERSON_CERT_FILE(50011,"身份证对应实人认证记录不存在"),
    REAL_PERSON_PUBLIC_KEY_FILE(50012,"公钥信息不正确"),
    REAL_PERSON_USER_EXIST_FILE(50013,"当前身份证已和其他账户完成绑定，请先解绑再重新绑定！"),
    ART_TITLE_NOT_EXIST_FILE(50014,"当前用户实名认证不全，请先完成实名认证并绑定"),

    THING_SELL_PRICE_IS_NULL(60001,"一口价发布，价格不可为空"),
    THING_SELL_INFO_IS_NULL(60002,"拍卖发布，必填项不可为空"),
    THING_SELL_TIME_IS_FORMAL(60003,"拍卖发布，拍卖开始时间必须小于等于结束时间"),
    THING_NOT_EXIST(60004,"当前物品不存在"),
    THING_SELL_ING(60005,"当前物品已发布出售，请先下架再进行其他操作"),
    SELL_START_TIME_LT_CURRENT_TIME(60006, "拍卖开始时间必须大于当前时间"),
    THING_TYPE_CANNOT_CHANGE(60007,"此物品当前权证不可变更，请确定物品是否已发布"),
    THING_USER_NOT_MATCH(60008,"物品所有者与当前用户不匹配，无权操作！"),
    ORDER_NOT_EXIST(60009,"订单不存在！"),
    ORDER_STATUS_NOT_MATCH_2(60010,"订单非待发货状态！"),
    CAN_NOT_BUY_SELF(60010,"购买人不能与物品拥有者相同！"),
    ADDR_NOT_EXIST_OR_SELL_FAILE(600011,"当前物品不存在或者物品状态不可售"),
    THING_HAVE_GIVE(600012,"当前物品已赠送，请勿重复赠送"),
    USER_HAVE_NO_CERT(60015,"收货人用户未实名认证"),
    THING_GIVE_NO_EXIST(600013,"当前无待签收物品"),
    THING_GIVE_NO_MATCH(600014,"代签收物品和扫描物品不一致，请核实物品卡片"),
    THING_HAVING_GIVE_NO_SELL(600015,"当前物品已赠送，不可出售"),
    THING_MARGIN_PAY_NO_END(600016,"拍卖保证金未支付成功，请确认"),
    THING_IN_AUCTION(600017,"物品拍卖中，请勿下架"),
    BID_ORDER_EXPIRE(600018,"下单时间已截止"),
    COMPANY_HAVE_EXIST(700001,"当前企业名称已注册"),
    USER_COMPANY_HAVE_EXIST(700002,"当前用户已登记在其他企业，请先删除再添加"),
    COMPANY_NOT_EXIST(700003,"当前企业不存在"),
    USER_COMPANY_NOT_EXIST(70004,"用户企业信息不存在"),
    ORDER_PAY_FAIL(80001,"订单支付失败！"),
    ORDER_FEE_ERROR(80002,"订单金额不一致！"),
    ORDER_STATUS_UPDATED(80003,"订单状态已更新！"),
    ORDER_LOCK_GET_FAILED(80004,"未获取到更新状态的锁！"),
    PAY_OF_WECHAT_FAILED(80005,"微信支付失败！"),
    ORDER_LOGIN_FAILED(80006,"创建订单失败，请返回核验！"),
    WITHDRAWALS_ARE_NOT_ALLOWED(90001,"提现金额低于1.5元暂不支持"),
    THE_MARGIN_HAS_BEEN_PAID(90002,"用户当前物品已支付保证金，请勿重复支付"),
    INSUFFICIENT_REFUND_FLOW(90003,"当日流水不足，请联系管理员"),
    ;

    int code;
    String msg;

    NEError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }


}
