FROM openjdk:17-jdk
ENV TZ="America/La_Paz"
RUN date
VOLUME /tmp
EXPOSE 9001
ADD ./activo1.jar /activo1.jar
ENTRYPOINT ["java"."-jar","/activo1.jar"]