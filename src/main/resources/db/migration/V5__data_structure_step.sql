CREATE TABLE `step` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `created_at` datetime NOT NULL,
                        `updated_at` datetime NOT NULL,
                        `description` varchar(255) NOT NULL,
                        `recipe_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `fk_step_recipe` (`recipe_id`),
                        CONSTRAINT `fk_step_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
);
