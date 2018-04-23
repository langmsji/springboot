DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` INT(10) NOT NULL AUTO_INCREMENT,
  `u_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NUll,
  `phone_num` VARCHAR(11) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `real_name` VARCHAR(30) NOT NULL,
  `sex` VARCHAR(10) NOT NULL,
  `register_time` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_name` (`u_name`),
  UNIQUE KEY `phone_num` (`phone_num`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `g_id` INT(10) NOT NULL AUTO_INCREMENT,
  `g_name` VARCHAR(20) NOT NULL,
  `category` VARCHAR(20) NOT NULL,
  `g_price` INT(10) NOT NULL,
  `salevol` INT(10) NOT NULL,
  `reserve` INT(10) NOT NULL,
  `description` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `shoppingcar`;
CREATE TABLE `shoppingcar` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `user_id` INT(10) NOT NULL ,
  `goods_id` INT(10) NOT NULL ,
  `total_price` INT(10) NOT NULL ,
  `counts` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `shopping_car_ibfk_2` (`goods_id`),
  CONSTRAINT `shopping_car_ibfk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `shopping_car_ibfk2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `commodity_order`;
CREATE TABLE `commodity_order` (
  `o_id` INT(10) NOT NULL AUTO_INCREMENT,
  `o_date` VARCHAR(20) NOT NULL ,
  `user_id` INT(10) NOT NULL ,
  `goods_id` INT(10) NOT NULL ,
  `total_price` INT(10) NOT NULL ,
  `counts` int(11) NOT NULL,
  `addr` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`o_id`),
  KEY `order_ibfk_2` (`goods_id`),
  CONSTRAINT `order_ibfk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `order_ibfk2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `favourite`;
CREATE TABLE `favourite` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `user_id` INT(10) NOT NULL ,
  `goods_id` INT(10) NOT NULL ,
  PRIMARY KEY (`id`),
  CONSTRAINT `favourite_ibfk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `favourite_ibfk2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`g_id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `from_uid` INT(10) NOT NULL ,
  `goods_id` INT(10) NOT NULL ,
  `comment_date` VARCHAR(20) NOT NULL ,
  `content` VARCHAR(200),
  PRIMARY KEY (`id`),
  CONSTRAINT `comment_ibfk1` FOREIGN KEY (`from_uid`) REFERENCES `user` (`u_id`),
  CONSTRAINT `comment_ibfk3` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`g_id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
