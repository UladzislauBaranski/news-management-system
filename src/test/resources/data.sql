/*CREATE TABLE IF NOT EXISTS news;
CREATE TABLE IF NOT EXISTS comment;

CREATE TABLE news (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  date TIMESTAMP ,
  title  VARCHAR(255),
  text  VARCHAR(255)
);

CREATE TABLE comment (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  date TIMESTAMP ,
  text  VARCHAR(255),
  username  VARCHAR(255)
      FOREIGN KEY (id_news) REFERENCES news(id)
  );
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS news;

*/


INSERT INTO news ( date, title, text) VALUES ( '2021-11-11 12:12:12', 'title', 'text');
INSERT INTO news ( date, title, text) VALUES ( '2021-11-11 12:12:12', 'title', 'text');
INSERT INTO news ( date, title, text) VALUES ('2021-11-11 12:12:12', 'title', 'text');
INSERT INTO news ( date, title, text) VALUES ( '2021-11-11 12:12:12', 'title', 'text');
INSERT INTO news ( date, title, text) VALUES ( '2021-11-11 12:12:12', 'title', 'text');

INSERT INTO comment ( date, text, username, id_news) VALUES ( '2021-11-11 12:12:12', 'text', 'username', 1);
INSERT INTO comment ( date, text, username, id_news) VALUES ( '2021-11-11 12:12:12', 'text', 'username', 2);
INSERT INTO comment ( date, text, username, id_news) VALUES ( '2021-11-11 12:12:12', 'text', 'username', 3);
INSERT INTO comment ( date, text, username, id_news) VALUES ( '2021-11-11 12:12:12', 'text', 'username', 4);
INSERT INTO comment ( date, text, username, id_news) VALUES ( '2021-11-11 12:12:12', 'text', 'username', 5);