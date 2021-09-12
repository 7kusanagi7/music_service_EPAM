# music_service_EPAM
Сервис продажи музыкальных подписок

СУБД: PostgreSQL
Для развертывания базы данных необходимо импортировать файл database.sql
Сделать это можно, используя pgAdmin:
Tools->Restore->Выбрать файл database.sql

При необходимости, изменить имя хоста, порт, пользователя и пароль к СУБД в файле Resources/database.properties

Скомпилировать артефакт Music service EPAM и разместить .war файл в папке Tomcat/webapps/..

Список пользователей и паролей, содержащихся в БД:
Пользователь-администратор:
admin
c493bgaTnD74B5z

Пользователь:
user
DHxQppKrB5wAKjh

Пользователь-должник:
debt_user
RDBau4ZxzhrhXuU

Заблокированный пользователь:
banned_user
4d84Yq83QHHVaqd
