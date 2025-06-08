# Клиент-серверное приложение – чат с ui-интерфейсом клиента

## Системные требования
- **JDK**: Версия `17` и выше.
- **Gradle**: Версия `8.4` и выше.

## Зависимости
Используется `jackson` для сериализации и десериализации, см. в `build.gradle`:
- `com.fasterxml.jackson.core:jackson-databind:2.15.2`

## Сборка
- `gradle :task6:clean :task6:build`

## Запуск сервера и клиента
Запуск по умолчанию происходит на порту `12345`
- `java -jar task6/build/libs/server.jar`
- `java -jar task6/build/libs/client.jar`
