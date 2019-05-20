/*
 * �f�[�^�x�[�X(webbook)��
 * ���[�U(webbookuser)�̍쐬
 */
DROP DATABASE IF EXISTS mywebbook;
DROP USER IF EXISTS mywebbookuser;
CREATE USER mywebbookuser WITH PASSWORD 'himitu';
CREATE DATABASE mywebbook OWNER mywebbookuser ENCODING 'UTF8';
\c mywebbook

-- ���p�҃e�[�u���̍쐬
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
	unsubscribed_at DATE --�ǉ�
);

-- ���ރe�[�u���̍쐬
CREATE TABLE category (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

-- �o�ŎЃe�[�u���̍쐬
CREATE TABLE publisher (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

-- ���ʐ}�����e�[�u���쐬
CREATE TABLE book_info (
	isbn CHAR(13) PRIMARY KEY, --ISBN�������͂����Ƃ��ɏC���������Ȃ邩���m��Ȃ��̂ł����id�͕K�v�����B
	category_id INTEGER NOT NULL REFERENCES category, --��ނ����Ȃ��̂őI�ׂ�悤�ɂ��Ă����ƕ֗��B
	publisher_id INTEGER NOT NULL REFERENCES publisher, --category_id�Ɠ��l�B
	name VARCHAR(100) NOT NULL,
	author VARCHAR(20) NOT NULL, --��ނ������̂�Bean�ɂ���K�v���Ȃ����ȁB��l������̒����������Ȃ����B
	released_at DATE NOT NULL --�ԋp�����̔��f�ɕK�v
);

-- �ŗL�}�����e�[�u���̍쐬
CREATE TABLE book_copy (
	id SERIAL PRIMARY KEY,
	isbn CHAR(13) NOT NULL REFERENCES book_info
);

-- �ݏo�ԋp�e�[�u���̍쐬
CREATE TABLE rental (
	book_copy_id INTEGER NOT NULL REFERENCES book_copy,
	id SERIAL NOT NULL,
	member_id INTEGER NOT NULL REFERENCES member,
	rented_at DATE NOT NULL,
	return_by DATE NOT NULL,
	returned_at DATE --�ǉ�
);

-- �e�[�u���̏��L�Ґݒ�
ALTER TABLE member OWNER TO mywebbookuser;
ALTER TABLE rental OWNER TO mywebbookuser;
ALTER TABLE book_copy OWNER TO mywebbookuser;
ALTER TABLE book_info OWNER TO mywebbookuser;
ALTER TABLE category OWNER TO mywebbookuser;
ALTER TABLE publisher OWNER TO mywebbookuser;

-- ���p�҃e�[�u���̃T���v���f�[�^
INSERT INTO member VALUES(1, '����', '���Y', '1000000', '�����s', '090-1111-1111', 'ai@dd.co.jp', '1984-10-01', CURRENT_DATE);
INSERT INTO member VALUES(2, '�ɓc', '���Y', '1100000', '��t��', '090-2222-2222', 'ida@dd.co.jp', '1954-10-2', CURRENT_DATE);
INSERT INTO member VALUES(3, '�F�c', '�O�Y', '1200000', '���ꌧ', '090-3333-3333', 'uda@dd.co.jp', '1939-10-3', CURRENT_DATE);
INSERT INTO member VALUES(4, '�]��', '�l�Y', '1300000', '���ꌧ', '090-4444-4444', 'egawa@dd.co.jp', '1948-10-4', CURRENT_DATE);
INSERT INTO member VALUES(5, '���{', '�ܘY', '1400000', '��ʌ�', '090-5555-5555', 'okamoto@dd.co.jp', '1972-10-5', CURRENT_DATE);
INSERT INTO member VALUES(6, '�b��', '���Y', '1500000', '������', '090-6666-6666', 'kai@dd.co.jp', '1971-10-6', CURRENT_DATE);
INSERT INTO member VALUES(7, '�ؓc', '���Y', '1600000', '�Q�n��', '090-7777-7777', 'kida@dd.co.jp', '1970-3-7', CURRENT_DATE);
INSERT INTO member VALUES(8, '����', '�O�Y', '1700000', '���m��', '090-8888-8888', 'kusakabe@dd.co.jp', '1966-5-8', CURRENT_DATE);
INSERT INTO member VALUES(9, '����', '�l�Y', '1800000', '�H�c��', '090-9999-9999', 'kemmochi@dd.co.jp', '1961-10-9', CURRENT_DATE);
INSERT INTO member VALUES(10, '����', '�ܘY', '1900000', '��茧', '090-0000-0000', 'komuro@dd.co.jp', '1993-10-10', CURRENT_DATE);

SELECT setval('member_id_seq', (SELECT MAX(id) FROM member));

-- ���ރe�[�u���̃T���v���f�[�^
INSERT INTO category VALUES(0, '���w�E�]�_');
INSERT INTO category VALUES(1, '�l���E�v�z');
INSERT INTO category VALUES(2, '�Љ�E�����E�@��');
INSERT INTO category VALUES(3, '���j�E�n��');
INSERT INTO category VALUES(4, '�Ȋw�E�e�N�m���W�[');
INSERT INTO category VALUES(5, '��w�E��w');
INSERT INTO category VALUES(6, '�R���s���[�^�E�C���^�[�l�b�g');
INSERT INTO category VALUES(7, '��炵�E���N�E�q���');

SELECT setval('category_id_seq', (SELECT MAX(id) FROM category));

-- �o�ŎЃe�[�u���̃T���v���f�[�^
INSERT INTO publisher VALUES(0, 'A�o��');
INSERT INTO publisher VALUES(1, 'B�o��');
INSERT INTO publisher VALUES(2, 'C�o��');
INSERT INTO publisher VALUES(3, 'D�o��');
INSERT INTO publisher VALUES(4, 'E�o��');
INSERT INTO publisher VALUES(5, 'F�o��');

SELECT setval('publisher_id_seq', (SELECT MAX(id) FROM publisher));

-- ���ʐ}�����e�[�u���̃T���v���f�[�^
INSERT INTO book_info VALUES('kei000001', 0, 0, '�ŋ��͂Ȃ������̂�', '�Ŕ��m', '1993-10-10');
INSERT INTO book_info VALUES('kei000002', 0, 0, '���Z�̂��炭��', '���ҍ�', '1993-10-10');
INSERT INTO book_info VALUES('rek000001', 3, 0, '���{�̗��j', '�����M��', '1993-10-10');
INSERT INTO book_info VALUES('rek000002', 3, 0, '�č��̗��j', '�O�b�V��', '1993-10-10');
INSERT INTO book_info VALUES('com000001', 6, 0, '�킩��₷��Java', '�v�c�z��', '2010-5-1');
INSERT INTO book_info VALUES('com000002', 6, 0, 'DB���t�@�����X', '�˒ːM��', '2010-5-1');
INSERT INTO book_info VALUES('bun000001', 2, 0, '�푈�Ǝ���', '�g���g��', '2019-5-1');
INSERT INTO book_info VALUES('bun000002', 2, 0, '�E�ݓq��', '�h�X�g�A�C�X�L�[', '2019-5-1');
INSERT INTO book_info VALUES('sei000001', 7, 0, '�L�ƒ��ǂ��Ȃ�ɂ�', '�L�c�b��', '2019-5-1');
INSERT INTO book_info VALUES('sei000002', 7, 0, '�炭����_�C�G�b�g', '�׋�ޗ���', '2019-5-1');

-- ���ʐ}�����e�[�u���̃T���v���f�[�^
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