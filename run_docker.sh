#!/bin/bash

# Сборка Docker-образа
echo "Building the Docker image..."
docker build -t senla-app .

# Проверка, существует ли контейнер с таким именем
if [ $(docker ps -a -q -f name=senla-container) ]; then
    echo "Stopping and removing existing container..."
    docker stop senla-container
    docker rm senla-container
fi

# Запуск нового контейнера
echo "Running the Docker container..."
docker run --name senla-container -p 8080:8080 --link postgres:postgres senla-app

echo "Docker container is up and running!"
