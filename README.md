[![Build status](https://ci.appveyor.com/api/projects/status/9aem34dr8735f264?svg=true)](https://ci.appveyor.com/project/uasmirnova/qa-diploma)

## Дипломный проект профессии «Тестировщик» ##

### Документы ###

1. [План автоматизации](https://github.com/uasmirnova/QA-Diploma/blob/main/docs/Plan.md)
2. [Отчет по итогам тестирования](https://github.com/uasmirnova/QA-Diploma/blob/main/docs/Report.md)
3. [Отчет по итогам автоматизации](https://github.com/uasmirnova/QA-Diploma/blob/main/docs/Summary.md)

### Запуск проекта, тестирование, отчет ###

1. Запустить Docker Desktop
2. Запустить IntelliJ IDEA
3. Открыть перенесенный с GitHub проект
4. Запустить контейнеры командой в корне проекта `docker-compose up -d`
5. Запустить приложение командой:
- для запуска с подключением к MySQL: 

`java -jar artifacts\aqa-shop\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`
- для запуска с подключением к PostgreSQL: 

`java -jar artifacts\aqa-shop\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`

6. Запустить тесты во второй вкладке Terminal командой:
- для запуска с подключением к MySQL:

`.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`
- для запуска с подключением к PostgreSQL: 

`.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`

7. Получить отчет после полного завершения тестов в браузере командой `.\gradlew allureServe`
8. Закрыть отчет командой `Ctrl + C`, подтвердить выход, нажав на `Y`
9. Закрыть приложение командой `CTRL + C` в первой вкладке Terminal
10. После остановки приложения остановить контейнеры командой `docker-compose down`
