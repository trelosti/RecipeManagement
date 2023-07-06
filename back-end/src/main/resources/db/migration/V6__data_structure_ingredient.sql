CREATE TABLE `ingredient` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `created_at` datetime NOT NULL,
                              `updated_at` datetime NOT NULL,
                              `ingredient_name` varchar(100) NOT NULL,
                              `measure_unit` varchar(255) NOT NULL,
                              `quantity` int NOT NULL,
                              `recipe_id` bigint DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `fk_ingredients_recipe` (`recipe_id`),
                              CONSTRAINT `fk_ingredients_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
);
