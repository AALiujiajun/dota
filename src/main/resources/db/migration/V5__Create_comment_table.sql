create table comment
(
	id BIGINT AUTO_INCREMENT PRIMARY  KEY ,
	parent_id bigint,
	type int,
	commentator int,
	gmt_create BIGINT,
	gmt_modified BIGINT,
	like_count BIGINT default 0,
);