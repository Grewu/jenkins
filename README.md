# Senla-Course

Гуревич Павел Андреевич

## Task 7: Запуск Docker
# Запуск Docker

1. `docker build -t senla-app .`
2. `docker run --name senla-container -p 8080:8080 --link postgres:postgres senla-app`

Примеры запросов в файле `generated-requests.http`
