/*
 * データベース(webbook)と
 * ユーザ(webbookuser)の作成
 */
DROP DATABASE IF EXISTS mywebbook;
DROP USER IF EXISTS mywebbookuser;
CREATE USER mywebbookuser WITH PASSWORD 'himitu';
CREATE DATABASE mywebbook OWNER mywebbookuser ENCODING 'UTF8';
\c mywebbook

-- 利用者テーブルの作成
CREATE TABLE member (
	id SERIAL PRIMARY KEY,
	family_name VARCHAR(10) NOT NULL,
	given_name VARCHAR(10) NOT NULL,
	postal_code CHAR(7) NOT NULL,
	address VARCHAR(100) NOT NULL,
	tel VARCHAR(20) NOT NULL,
	email VARCHAR(100) NOT NULL,
	birthday DATE NOT NULL,
	subscribed_at DATE NOT NULL,
	unsubscribed_at DATE --追加
);

-- 分類テーブルの作成
CREATE TABLE category (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

-- 出版社テーブルの作成
CREATE TABLE publisher (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

-- 共通図書情報テーブル作成
CREATE TABLE book_info (
	isbn CHAR(13) PRIMARY KEY, --ISBNをご入力したときに修正したくなるかも知れないのでやっぱidは必要かも。
	category_id INTEGER NOT NULL REFERENCES category, --種類が少ないので選べるようにしておくと便利。
	publisher_id INTEGER NOT NULL REFERENCES publisher, --category_idと同様。
	name VARCHAR(100) NOT NULL,
	author VARCHAR(20) NOT NULL, --種類が多いのでBeanにする必要がないかな。一人当たりの著書数も少ないし。
	released_at DATE NOT NULL --返却期日の判断に必要
);

-- 固有図書情報テーブルの作成
CREATE TABLE book_copy (
	id SERIAL PRIMARY KEY,
	isbn CHAR(13) NOT NULL REFERENCES book_info
);

-- 貸出返却テーブルの作成
CREATE TABLE rental (
	book_copy_id INTEGER NOT NULL REFERENCES book_copy,
	id SERIAL NOT NULL,
	member_id INTEGER NOT NULL REFERENCES member,
	rented_at DATE NOT NULL,
	return_by DATE NOT NULL,
	returned_at DATE --追加
);

-- テーブルの所有者設定
ALTER TABLE member OWNER TO mywebbookuser;
ALTER TABLE rental OWNER TO mywebbookuser;
ALTER TABLE book_copy OWNER TO mywebbookuser;
ALTER TABLE book_info OWNER TO mywebbookuser;
ALTER TABLE category OWNER TO mywebbookuser;
ALTER TABLE publisher OWNER TO mywebbookuser;

-- 利用者テーブルのサンプルデータ
INSERT INTO member VALUES(1, '阿井', '太郎', '1000000', '東京都', '090-1111-1111', 'ai@dd.co.jp', '1984-10-01', CURRENT_DATE);
INSERT INTO member VALUES(2, '伊田', '次郎', '1100000', '千葉県', '090-2222-2222', 'ida@dd.co.jp', '1954-10-2', CURRENT_DATE);
INSERT INTO member VALUES(3, '宇田', '三郎', '1200000', '滋賀県', '090-3333-3333', 'uda@dd.co.jp', '1939-10-3', CURRENT_DATE);
INSERT INTO member VALUES(4, '江川', '四郎', '1300000', '佐賀県', '090-4444-4444', 'egawa@dd.co.jp', '1948-10-4', CURRENT_DATE);
INSERT INTO member VALUES(5, '岡本', '五郎', '1400000', '埼玉県', '090-5555-5555', 'okamoto@dd.co.jp', '1972-10-5', CURRENT_DATE);
INSERT INTO member VALUES(6, '甲斐', '太郎', '1500000', '徳島県', '090-6666-6666', 'kai@dd.co.jp', '1971-10-6', CURRENT_DATE);
INSERT INTO member VALUES(7, '木田', '次郎', '1600000', '群馬県', '090-7777-7777', 'kida@dd.co.jp', '1970-3-7', CURRENT_DATE);
INSERT INTO member VALUES(8, '草壁', '三郎', '1700000', '高知県', '090-8888-8888', 'kusakabe@dd.co.jp', '1966-5-8', CURRENT_DATE);
INSERT INTO member VALUES(9, '剣持', '四郎', '1800000', '秋田県', '090-9999-9999', 'kemmochi@dd.co.jp', '1961-10-9', CURRENT_DATE);
INSERT INTO member VALUES(10, '小室', '五郎', '1900000', '岩手県', '090-0000-0000', 'komuro@dd.co.jp', '1993-10-10', CURRENT_DATE);

SELECT setval('member_id_seq', (SELECT MAX(id) FROM member));

-- 分類テーブルのサンプルデータ
INSERT INTO category VALUES(0, '文学・評論');
INSERT INTO category VALUES(1, '人文・思想');
INSERT INTO category VALUES(2, '社会・政治・法律');
INSERT INTO category VALUES(3, '歴史・地理');
INSERT INTO category VALUES(4, '科学・テクノロジー');
INSERT INTO category VALUES(5, '医学・薬学');
INSERT INTO category VALUES(6, 'コンピュータ・インターネット');
INSERT INTO category VALUES(7, '暮らし・健康・子育て');

SELECT setval('category_id_seq', (SELECT MAX(id) FROM category));

-- 出版社テーブルのサンプルデータ
INSERT INTO publisher VALUES(0, 'A出版');
INSERT INTO publisher VALUES(1, 'B出版');
INSERT INTO publisher VALUES(2, 'C出版');
INSERT INTO publisher VALUES(3, 'D出版');
INSERT INTO publisher VALUES(4, 'E出版');
INSERT INTO publisher VALUES(5, 'F出版');

SELECT setval('publisher_id_seq', (SELECT MAX(id) FROM publisher));

-- 共通図書情報テーブルのサンプルデータ
INSERT INTO book_info VALUES('kei000001', 0, 0, '税金はなぜ高いのか', '税博士', '1993-10-10');
INSERT INTO book_info VALUES('kei000002', 0, 0, '金融のからくり', '利惟哉', '1993-10-10');
INSERT INTO book_info VALUES('rek000001', 3, 0, '日本の歴史', '足利信長', '1993-10-10');
INSERT INTO book_info VALUES('rek000002', 3, 0, '米国の歴史', 'グッシュ', '1993-10-10');
INSERT INTO book_info VALUES('com000001', 6, 0, 'わかりやすいJava', '益田陽一', '2010-5-1');
INSERT INTO book_info VALUES('com000002', 6, 0, 'DBリファレンス', '戸塚信二', '2010-5-1');
INSERT INTO book_info VALUES('bun000001', 2, 0, '戦争と試合', 'トルトル', '2019-5-1');
INSERT INTO book_info VALUES('bun000002', 2, 0, '摘み賭罰', 'ドストアイスキー', '2019-5-1');
INSERT INTO book_info VALUES('sei000001', 7, 0, '猫と仲良くなるには', '猫田恵美', '2019-5-1');
INSERT INTO book_info VALUES('sei000002', 7, 0, 'らくちんダイエット', '細区奈留代', '2019-5-1');

-- 共通図書情報テーブルのサンプルデータ
INSERT INTO book_copy (isbn) VALUES('kei000001');
INSERT INTO book_copy (isbn) VALUES('kei000001');
INSERT INTO book_copy (isbn) VALUES('kei000001');
INSERT INTO book_copy (isbn) VALUES('kei000002');
INSERT INTO book_copy (isbn) VALUES('kei000002');
INSERT INTO book_copy (isbn) VALUES('kei000002');
INSERT INTO book_copy (isbn) VALUES('rek000001');
INSERT INTO book_copy (isbn) VALUES('rek000002');
INSERT INTO book_copy (isbn) VALUES('com000001');
INSERT INTO book_copy (isbn) VALUES('com000002');
INSERT INTO book_copy (isbn) VALUES('bun000001');
INSERT INTO book_copy (isbn) VALUES('bun000002');
INSERT INTO book_copy (isbn) VALUES('sei000001');
INSERT INTO book_copy (isbn) VALUES('sei000002');