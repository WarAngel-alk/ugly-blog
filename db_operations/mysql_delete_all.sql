BEGIN;

DELETE FROM tag_post;
DELETE FROM tag;
DELETE FROM user_comment_mark;
DELETE FROM user_post_mark;
DELETE FROM comment;
DELETE FROM post;
DELETE FROM message;
DELETE FROM user_role;
DELETE FROM user;
DELETE FROM role;

COMMIT;