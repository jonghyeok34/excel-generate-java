FROM mysql:8.0.21

# 인코딩
ENV LC_ALL=C.UTF-8
ENV character-set-server utf8
ENV collation-server utf8_general_ci
ENV default-character-set utf8
ENV default-collation utf8_general_ci

# 스키마 명, 비밀번호
ENV MYSQL_DATABASE SCHEMA_NAME // 스키마명
ENV MYSQL_ROOT_PASSWORD password

EXPOSE 3306