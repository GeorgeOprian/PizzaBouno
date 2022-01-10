CREATE TABLE course (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    room INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_course (
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, course_id)
);

ALTER table user_course
ADD constraint FK_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER table user_course
ADD constraint FK_COURSE FOREIGN KEY (course_id) REFERENCES course (id);
