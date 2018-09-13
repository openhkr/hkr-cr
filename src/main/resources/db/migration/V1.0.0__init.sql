DROP TABLE IF EXISTS `balance_wallet`;
CREATE TABLE `balance_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(20) NOT NULL COMMENT '用户主键',
  `balance_type` int(3) NOT NULL COMMENT '押金类型：1 普通余额，2 保证金余额',
  `amount` decimal(8,2) NOT NULL COMMENT '余额金额',

  `created_by` varchar(64) NULL COMMENT '创建人',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(64) NULL COMMENT '更新人id',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `deleted` varchar(100) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='余额钱包表';

DROP TABLE IF EXISTS `balance_record`;
CREATE TABLE `balance_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(20) NOT NULL COMMENT '用户主键',
  `balance_type` int(3) NOT NULL COMMENT '押金类型：1 普通余额，2 保证金余额',
  `change` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '余额变化值,正数为存入,负数为支出消费',
  `balance_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '异动后,用户账户余额',
  `uuid` varchar(100) NOT NULL DEFAULT '' COMMENT '防重id',
  `rollback_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '回滚标志0：未回滚，1：已回滚',

  `created_by` varchar(64) NULL COMMENT '创建人',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(64) NULL COMMENT '更新人id',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `deleted` varchar(100) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='余额记录表';

alter table balance_wallet add unique index user_and_balance_type(user_id,balance_type);
ALTER TABLE `balance_record` ADD UNIQUE(`uuid`);