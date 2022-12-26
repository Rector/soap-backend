# Проект: SOAP веб-сервис, созданный с использованием Spring Framework.
___
## Стек: Spring Framework (Boot, Web Services, JPA Data), SOAP, JAXB, WSDL4J, Flyway, H2 Database, Lombok.
___
## Функционал:
___
* Реализована возможность получения WSDL-документа.
* Существует возможность поиска и отображения всех пользователей.
![](screenshots/users.jpg)

* Реализована возможность поиска и отображения пользователя с его ролями.
![](screenshots/user.jpg)

* Существует возможность добавления нового пользователя.
![](screenshots/add_user.jpg)

* Реализована возможность изменения данных пользователя.
![](screenshots/update_user.jpg)

* Существует возможность удаления пользователя.
* Реализована валидация данных и отображение ошибок при некорректном запросе.
![](screenshots/valid_login_1.jpg)
![](screenshots/valid_login_2.jpg)
![](screenshots/valid_password.jpg)
![](screenshots/valid_role.jpg)
  
* В папке "to_validate_requests" расположен файл "requests_xml.docx" со сформированными запросами,
  которые можно протестировать (например, через Postman).