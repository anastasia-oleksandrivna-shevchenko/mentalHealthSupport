-- Створення таблиці users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       age INTEGER,
                       avatar_url TEXT,
                       bio TEXT,
                       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                       email TEXT UNIQUE NOT NULL,
                       first_name TEXT NOT NULL,
                       gender TEXT,
                       last_name TEXT NOT NULL,
                       password TEXT NOT NULL,
                       username TEXT UNIQUE NOT NULL
);

-- Створення таблиці categories
CREATE TABLE categories (
                            categoryid SERIAL PRIMARY KEY,
                            description TEXT,
                            image_url TEXT,
                            name TEXT NOT NULL
);

-- Створення таблиці posts
CREATE TABLE posts (
                       postid SERIAL PRIMARY KEY,
                       content TEXT NOT NULL,
                       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                       is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
                       title TEXT NOT NULL,
                       category_id INTEGER NOT NULL,
                       user_id INTEGER NOT NULL,
                       CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(categoryid) ON DELETE CASCADE,
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Створення таблиці tags
CREATE TABLE tags (
                      id SERIAL PRIMARY KEY,
                      name TEXT UNIQUE NOT NULL
);

-- Створення таблиці post_tags (зв'язок багато-до-багатьох для постів і тегів)
CREATE TABLE post_tags (
                           post_id INTEGER NOT NULL,
                           tag_id INTEGER NOT NULL,
                           PRIMARY KEY (post_id, tag_id),
                           CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(postid) ON DELETE CASCADE,
                           CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Створення таблиці diary_entries
CREATE TABLE diary_entries (
                               id SERIAL PRIMARY KEY,
                               content TEXT NOT NULL,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                               is_private_status BOOLEAN NOT NULL DEFAULT TRUE,
                               mood TEXT,
                               updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                               user_id INTEGER NOT NULL,
                               CONSTRAINT fk_diary_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Створення таблиці diary_tags (зв'язок багато-до-багатьох для щоденників і тегів)
CREATE TABLE diary_tags (
                            diary_id INTEGER NOT NULL,
                            tag_id INTEGER NOT NULL,
                            PRIMARY KEY (diary_id, tag_id),
                            CONSTRAINT fk_diary FOREIGN KEY (diary_id) REFERENCES diary_entries(id) ON DELETE CASCADE,
                            CONSTRAINT fk_diary_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Створення таблиці comments
CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                          is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
                          post_id INTEGER NOT NULL,
                          user_id INTEGER NOT NULL,
                          CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES posts(postid) ON DELETE CASCADE,
                          CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
