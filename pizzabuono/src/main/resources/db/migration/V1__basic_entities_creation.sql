CREATE TABLE `drink` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `name` varchar(255) NOT NULL DEFAULT '',
     `price` double DEFAULT NULL,
     `quantity` int DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pizza` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `name` varchar(255) NOT NULL DEFAULT '',
     `ingredients` varchar(255) DEFAULT NULL,
     `price` double DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_details` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `card` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `account_created` datetime(6) DEFAULT NULL,
     `full_name` varchar(255) DEFAULT NULL,
     `username` varchar(255) DEFAULT NULL,
     `user_details_id` bigint DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY FK_USER_DETAILS (`user_details_id`),
     CONSTRAINT FK_USER_DETAILS FOREIGN KEY (`user_details_id`) REFERENCES `user_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `shopping_cart` (
     `cart_id` bigint NOT NULL AUTO_INCREMENT,
     `created_date` date DEFAULT NULL,
     `created_time` time DEFAULT NULL,
     PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `shopping_cart_product` (
     `cart_id` bigint NOT NULL,
     `pizza_id` bigint NOT NULL default 0,
     `drink_id` bigint NOT NULL default 0,
     KEY FK_PIZZA (`pizza_id`),
     KEY FK_CART (`cart_id`),
     KEY FK_DRINK (`drink_id`),
     CONSTRAINT FK_CART FOREIGN KEY (`cart_id`) REFERENCES `shopping_cart` (`cart_id`),
     CONSTRAINT FK_PIZZA FOREIGN KEY (`pizza_id`) REFERENCES `pizza` (`id`),
     CONSTRAINT FK_Drink FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
