# socialn schema
# --- !Ups

CREATE TABLE `socialn`.`user` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `userName` VARCHAR(50) NOT NULL ,
    `password` VARCHAR(32) NOT NULL,
    `firstName` VARCHAR(50) NULL DEFAULT NULL,
    `lastName` VARCHAR(50) NULL DEFAULT NULL,
    `email` VARCHAR(50) NULL,
    `dateOfBirth` DATETIME NULL DEFAULT NULL,
    `about` TEXT NULL DEFAULT NULL,
    `profilePicture` VARCHAR(1024) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_userName` ( `userName` ASC),
    UNIQUE INDEX `uq_email` ( `email` ASC)
);
CREATE TABLE `socialn`.`userFriend` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `sourceId` BIGINT NOT NULL,
    `targetId` BIGINT NOT NULL,
    `createdAt` DATETIME NOT NULL,
    `status` SMALLINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `idx_friend_source` (`sourceId` ASC),
    CONSTRAINT `fk_friend_source`
		FOREIGN KEY (`sourceId`)
        REFERENCES `socialn`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

ALTER TABLE `socialn`.`userFriend`
ADD INDEX `idx_friend_target` (`targetId` ASC);
ALTER TABLE `socialn`.`userFriend`
ADD CONSTRAINT `fk_friend_target`
	FOREIGN KEY (`targetId`)
    REFERENCES `socialn`.`user` (`id`)
    ON DELETE NO ACTION
	ON UPDATE NO ACTION;
ALTER TABLE `socialn`.`userFriend` ADD UNIQUE `uq_friend`(`sourceId`, `targetId`);

CREATE TABLE `socialn`.`post` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `userId` BIGINT NOT NULL,
    `message` TINYTEXT NULL DEFAULT NULL,
    `createdAt` DATETIME NOT NULL,
    `editedAt` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_post_user` (`userId` ASC),
    CONSTRAINT `fk_post_user`
		FOREIGN KEY (`userId`)
        REFERENCES `socialn`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE `socialn`.`likes`(
	`userId` BIGINT NOT NULL,
    `postId` BIGINT NOT NULL,
    INDEX `idx_like_user` (`userId` ASC),
    CONSTRAINT `fk_likes_user`
		FOREIGN KEY (`userId`)
        REFERENCES `socialn`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);
ALTER TABLE `socialn`.`likes`
ADD INDEX `idx_like_post` (`postId` ASC);
ALTER TABLE `socialn`.`likes`
ADD	CONSTRAINT `fk_likes_post`
	FOREIGN KEY (`postId`)
    REFERENCES `socialn`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
# --- !Downs

DROP SCHEMA