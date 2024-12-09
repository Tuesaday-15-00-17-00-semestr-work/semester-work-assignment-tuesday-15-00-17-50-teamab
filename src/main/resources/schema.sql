CREATE TABLE IF NOT EXISTS Book (
    book_id INT NOT NULL,
    title varchar(250) NOT NULL,
    author varchar(250) NOT NULL,
    isbn varchar(250) NOT NULL,
    available_copies INT NOT NULL,
    PRIMARY KEY(book_id)

    );

CREATE TABLE IF NOT EXISTS Users (
    userId INT NOT NULL,
    username varchar(250) NOT NULL,
    password  varchar(250) NOT NULL,
    roleId  INT NOT NULL,
    email  varchar(250) NOT NULL,
    PRIMARY KEY(userId)
);

CREATE TABLE IF NOT EXISTS LoginLog (
    loggedUserId INT NOT NULL,
    loggedUsername varchar(250) NOT NULL,
    PRIMARY KEY(loggedUserId)
    );