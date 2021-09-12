# music_service_EPAM
Сервис продажи музыкальных подписок

СУБД: PostgreSQL
Для развертывания базы данных необходимо импортировать файл database.sql
Сделать это можно, используя pgAdmin:
Tools->Restore->Выбрать файл database.sql

При необходимости, изменить имя хоста, порт, пользователя и пароль к СУБД в файле Resources/database.properties

Скомпилировать артефакт Music service EPAM и разместить .war файл в папке Tomcat/webapps/..
