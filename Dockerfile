FROM tomcat:10.0

RUN rm -rf /usr/local/tomcat/webapps/*

ENV POSTGRES_URL=jdbc:postgresql://localhost:5433/postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

COPY build/libs/senla.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
