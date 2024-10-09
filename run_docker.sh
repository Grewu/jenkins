#!/bin/bash

# ������ Docker-������
echo "Building the Docker image..."
docker build -t senla-app .

# ��������, ���������� �� ��������� � ����� ������
if [ $(docker ps -a -q -f name=senla-container) ]; then
    echo "Stopping and removing existing container..."
    docker stop senla-container
    docker rm senla-container
fi

# ������ ������ ����������
echo "Running the Docker container..."
docker run --name senla-container -p 8080:8080 --link postgres:postgres senla-app

echo "Docker container is up and running!"
