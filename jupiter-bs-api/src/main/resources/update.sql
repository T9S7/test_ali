
/*==============================================================*/
/* Table: art_type  类型管理表                                  */
/*==============================================================*/
CREATE TABLE `art_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `lever` int(11) NOT NULL COMMENT '级别',
  `type_name` varchar(100) NOT NULL COMMENT '类别名称',
  `parent_id` int(11) NOT NULL COMMENT '父id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_auth`;
CREATE TABLE `sys_user_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `identity_type` int(11) NOT NULL COMMENT '登录类型（1：手机、2：账号、3：邮箱）',
  `identifier` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份标识',
  `credential` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码凭证',
  `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'APP用户授权表' ROW_FORMAT = Dynamic;

ALTER TABLE `sys_user`
  DROP COLUMN `password`,
  DROP COLUMN `relate_app_user`,
  DROP COLUMN `modulus`,
  DROP COLUMN `exponent`,
  ADD COLUMN `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像' AFTER `telephone`;

ALTER TABLE `sys_user_auth`
  ADD COLUMN `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间' AFTER `is_del`,
  ADD COLUMN `update_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间' AFTER `create_time`;


  CREATE TABLE `thing_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `contract_addr` varchar(64) NOT NULL COMMENT '合约地址',
  `art_name` varchar(64) NOT NULL COMMENT '名称',
  `art_year` varchar(64) NOT NULL COMMENT '年代',
  `author` varchar(64) NOT NULL COMMENT '作者',
  `thing_desc` varchar(64) NOT NULL COMMENT '描述',
  `thing_size` varchar(64) DEFAULT NULL COMMENT '尺寸',
  `thing_weight` int(11) DEFAULT NULL COMMENT '重量',
  `thing_condition` varchar(60) DEFAULT NULL COMMENT '资质',
  `thing_element` varchar(60) DEFAULT NULL COMMENT '成分',
	`thing_type` int(11) DEFAULT NULL COMMENT '分类',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='艺术品信息';



CREATE TABLE `thing_pic_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `thing_id` bigint(20) NOT NULL COMMENT '物品ID',
  `pic_url` varchar(128) NOT NULL COMMENT '图片地址',
  `pic_md5` varchar(36) NOT NULL COMMENT '图片md5码',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='上链物品照片信息';

CREATE TABLE `thing_sell_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `thing_id` bigint(20) NOT NULL COMMENT '物品ID',
  `sell_type` int not null COMMENT '发布类型 1 一口价；2 拍卖',
	seller_info VARCHAR(2000) NOT NULL COMMENT '卖家寄语',
	thing_price bigint(20) COMMENT '一口价价格',
	seller_price bigint(20) COMMENT '拍卖 卖家估价',
	margin bigint(20) COMMENT '拍卖 保证金',
	sell_start_price bigint(20) COMMENT '拍卖 起拍价',
	sell_add_price bigint(20)  COMMENT '拍卖 加价幅度',
	auction_start_time BIGINT(20) COMMENT '拍卖开始时间',
	auction_end_time BIGINT(20) COMMENT '拍卖结束时间',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='物品发布信息';

#20210923
ALTER TABLE `user_info`
  ADD COLUMN `auth_code` varchar(20) NULL COMMENT '鉴权码' AFTER `is_del`;

--20210924
ALTER TABLE `thing_sell_info`
MODIFY COLUMN `sell_type`  int(11) NOT NULL COMMENT '发布类型 1 一口价；2 拍卖 ;0非卖展示' AFTER `thing_id`;

ALTER TABLE `user_info`
ADD COLUMN `desc`  varchar(2000) NULL COMMENT '用户描述' AFTER `auth_code`;

ALTER TABLE `thing_sell_info`
ADD COLUMN `put_on`  tinyint(1) NULL COMMENT '上架状态' AFTER `auction_end_time`;

ALTER TABLE `user_info`
CHANGE COLUMN `desc` `auth_desc`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户描述' AFTER `auth_code`;

#2021007
DROP TABLE IF EXISTS `bid_info`;
CREATE TABLE `bid_info`  (
                           `bid_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '竞标ID',
                           `bid_price` bigint(20) NOT NULL COMMENT '竞标价格',
                           `sell_id` bigint(20) NOT NULL COMMENT '拍卖编号',
                           `user_id` int(11) NOT NULL COMMENT '竞拍人ID',
                           `nickname` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞拍人昵称',
                           `bid_time` bigint(20) NOT NULL COMMENT '竞标时间',
                           `is_auto` tinyint(1) NULL DEFAULT 0 COMMENT '自动出价',
                           `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                           PRIMARY KEY (`bid_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '竞拍出价信息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
                             `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                             `thing_id` bigint(20) NOT NULL COMMENT '产品ID',
                             `thing_sell_id` bigint(20) NOT NULL COMMENT '拍卖ID',
                             `price` bigint(20) NOT NULL COMMENT '价格（单位：分）',
                             `insurance` bigint(20) NULL DEFAULT NULL COMMENT '保险（单位：分）',
                             `buyer_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家收货地址',
                             `buyer_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家姓名',
                             `buyer_mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家联系电话',
                             `order_status` int(11) NOT NULL COMMENT '订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）',
                             `seller_id` int(11) NOT NULL COMMENT '卖家用户ID',
                             `buyer_id` int(11) NOT NULL COMMENT '买家用户ID',
                             `express_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递单号',
                             `express_company` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司',
                             `pay_type` int(11) NULL DEFAULT 1 COMMENT '支付方式（1：微信；2：支付宝）',
                             `pay_time` bigint(20) NULL DEFAULT NULL COMMENT '支付时间',
                             `pay_deadline` bigint(20) NULL DEFAULT NULL COMMENT '可支付截止时间',
                             `send_out_deadline` bigint(20) NULL DEFAULT NULL COMMENT '发货截止时间',
                             `create_time` bigint(20) NOT NULL COMMENT '创建时间',
                             `update_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
                             `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                             PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '艺术品订单信息' ROW_FORMAT = Dynamic;


ALTER TABLE `thing_info`
ADD COLUMN `upload_status`  tinyint(1) NOT NULL COMMENT '上传状态 1 成功，0 失败' AFTER `user_id`;


ALTER TABLE `thing_info`
ADD COLUMN `current_price`  bigint(20) NULL COMMENT '当前价格' AFTER `upload_status`,
ADD COLUMN `current_state`  int(2) NULL COMMENT '当前状态(0未发布，1售卖中，2已下架)' AFTER `current_price`;


CREATE TABLE `check_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_addr` varchar(64) NOT NULL,
  `thing_id` bigint(20) DEFAULT NULL COMMENT '物品id',
  `check_type` int(2) NOT NULL COMMENT '校验状态(0-失败，1-成功)',
  `file_info` varchar(200) DEFAULT NULL COMMENT '失败原因',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `create_time` bigint(20) NOT NULL COMMENT '校验时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `company_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(200) NOT NULL COMMENT '企业名称',
  `company_desc` varchar(2000) DEFAULT NULL COMMENT '企业描述',
  `company_type` int(2) DEFAULT NULL COMMENT '企业类型',
  `company_url` varchar(200) DEFAULT NULL COMMENT '企业头像地址',
  `is_official` tinyint(1) DEFAULT '0' COMMENT '是否官方认证',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_company` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `company_id` int(20) NOT NULL COMMENT '企业id',
  `user_type` int(2) DEFAULT NULL COMMENT '用户类型',
  `is_company_official` tinyint(1) DEFAULT '0' COMMENT '是否企业官方指定用户',
  `create_time` bigint(20) DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户企业对应表';

#2021-12-13
ALTER TABLE `order_info`
  ADD COLUMN `buyer_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '买家是否删除' AFTER `is_del`,
  ADD COLUMN `seller_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '卖家是否删除' AFTER `buyer_del`;

ALTER TABLE `thing_info`
  ADD COLUMN `uploader_id` int(11) NOT NULL COMMENT '上传者用户ID' AFTER `thing_type`;
#2021-12-26
ALTER TABLE `app_version`
  ADD COLUMN `download_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '程序下载地址' AFTER `version_des`;
ALTER TABLE `app_version`
  ADD COLUMN `version_code` int(11) NOT NULL DEFAULT 0 COMMENT '版本code' AFTER `is_force_update`;

DROP TABLE IF EXISTS `app_notice`;
CREATE TABLE `app_notice`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知标题',
                             `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
                             `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '签名落款',
                             `category` int(11) NOT NULL COMMENT '类型（0：维护通知）',
                             `enabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否启用',
                             `create_time` bigint(20) NOT NULL COMMENT '创建时间',
                             `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
                             `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `app_tutorial_video`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                     `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频地址',
                                     `create_time` bigint(20) NOT NULL COMMENT '创建时间',
                                     `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
                                     `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教程视频表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `account_cash_detail`;
CREATE TABLE `account_cash_detail` (
  `id` int(11) NOT NULL COMMENT '主键',
  `account_id` int(11) DEFAULT NULL COMMENT '账户id',
  `trade_type` int(11) DEFAULT NULL COMMENT '保证金状态(1.代收未代付；2.已代付；3.已退还；4.转账(平台账户转出))',
  `payee` varchar(255) DEFAULT NULL COMMENT '收款方',
  `trade_time` datetime DEFAULT NULL COMMENT '交易时间',
  `trade_order_no` bigint(20) NOT NULL COMMENT '交易订单号',
  `pre_account_balance` bigint(20) DEFAULT NULL COMMENT '交易前金额',
  `trade_money` bigint(20) DEFAULT NULL COMMENT '交易金额',
  `cash_account_balance` bigint(20) DEFAULT NULL COMMENT '交易后保证金余额',
  `sell_id` int(11) DEFAULT NULL COMMENT '拍卖id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '交易时间',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户保证金余额明细表';


DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `balance` bigint(20) NOT NULL DEFAULT '0' COMMENT '账户余额',
  `frozen_capital` bigint(20) DEFAULT '0' COMMENT '冻结资金',
  `cash_deposit` bigint(20) DEFAULT NULL COMMENT '保证金金额',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户信息表';

#2022-01-03
ALTER TABLE `thing_pic_info`
  ADD COLUMN `type` int(11) NULL DEFAULT 0 COMMENT '类型 0：图片；1：视频' AFTER `is_del`;

ALTER TABLE `thing_info`
  ADD COLUMN `is_official` tinyint(1) NULL DEFAULT 0 COMMENT '是否为官方' AFTER `is_del`;

update thing_info set is_official = 1 where contract_addr in ('0x0000000000000000000000000000000000000000','0x1111111111111111111111111111111111111111') and is_del = 0;

#2022-01-04
ALTER TABLE `thing_info`
  ADD COLUMN `cover_pic` varchar(128) NULL COMMENT '封面图' AFTER `is_official`;

ALTER TABLE `thing_info`
  ADD COLUMN `long_pic` varchar(128) NULL COMMENT '长图' AFTER `cover_pic`;

UPDATE `thing_info` SET `cover_pic` = 'http://jupiter-avatar-bucket.oss-cn-hangzhou.aliyuncs.com/20211123/598adbb814ba47a7a1166e7134132480.png' WHERE `id` = 206;
UPDATE `thing_info` SET `cover_pic` = 'http://jupiter-avatar-bucket.oss-cn-hangzhou.aliyuncs.com/20211123/1db68c1822944bc3a30b44d8438f292b.png' WHERE `id` = 207;

UPDATE `user_info` SET `user_type` = 99 WHERE `nickname` = '爱较真官方'

#2022-01-06
ALTER TABLE `order_info`
	ADD COLUMN `tl_order_request` varchar(50) NULL COMMENT '再次发起支付订单号' AFTER `seller_del`;

ALTER TABLE `thing_info`
  MODIFY COLUMN `thing_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述' AFTER `author`;

#2022-01-07
DROP TABLE IF EXISTS `refund_info`;
CREATE TABLE `refund_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL COMMENT '原始订单号',
  `user_id` int(20) DEFAULT NULL COMMENT '会员号',
  `pay_match` int(2) DEFAULT NULL COMMENT '支付方式',
  `amount` bigint(20) DEFAULT NULL COMMENT '订单金额',
  `order_status` int(2) DEFAULT NULL COMMENT '退款订单状态（0-待退款；1-退款成功；2-退款失败）',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='订单退款明细表';

#2022-01-10
ALTER TABLE `account_cash_detail`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键' FIRST ;

#2022-01-12
ALTER TABLE `order_info`
  ADD COLUMN `order_category` int(11) NOT NULL DEFAULT 1 COMMENT '订单类别(1：普通物品；2：上链工具)' AFTER `order_sn`;

#2022-01-13
ALTER TABLE `order_info`
	MODIFY COLUMN `order_category` int(11) NULL DEFAULT 1 COMMENT '订单类别(1：普通物品；2：上链工具)' AFTER `order_sn`
;

#2022-01-1
ALTER TABLE `refund_info`
MODIFY COLUMN `order_id`  varchar(20) NULL DEFAULT NULL COMMENT '原始订单号' AFTER `id`;

#2022-01-17
ALTER TABLE `bid_info`
  ADD COLUMN `bid_states` int(11) NULL COMMENT '状态 0--出局，1--成交请支付，2--领先，3--出局继续出价' AFTER `is_del`,
  ADD COLUMN `order_deadline` longblob NULL COMMENT '下订单截止时间' AFTER `bid_states`;

#2022-01-19
ALTER TABLE `refund_info`
MODIFY COLUMN `order_id`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '原始订单号' AFTER `id`;


ALTER TABLE `order_info`
	MODIFY COLUMN `order_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '订单编号' AFTER `order_id`
;

ALTER TABLE `order_info`
  ADD COLUMN `buyer_failure_reason` varchar(32) NULL COMMENT '交易失败原因-买家' AFTER `seller_del`,
  ADD COLUMN `seller_failure_reason` varchar(32) NULL COMMENT '交易失败原因-卖家' AFTER `buyer_failure_reason`;


ALTER TABLE `order_info`
	MODIFY COLUMN `buyer_failure_reason` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT ' ' COMMENT '交易失败原因-买家' AFTER `seller_del`,
	MODIFY COLUMN `seller_failure_reason` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT ' ' COMMENT '交易失败原因-卖家' AFTER `buyer_failure_reason`
;
