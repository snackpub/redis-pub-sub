CREATE TABLE `t_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(60) DEFAULT NULL COMMENT '优惠券名称',
  `money` decimal(10,0) DEFAULT NULL COMMENT '金额',
  `coupon_desc` varchar(128) DEFAULT NULL COMMENT '优惠券说明',
  `create_time` datetime DEFAULT NULL COMMENT '获取时间',
  `expire_time` datetime DEFAULT NULL COMMENT '失效时间',
  `state` int(1) DEFAULT NULL COMMENT '状态，0-有效，1-已失效，2-已使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8