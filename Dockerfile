FROM openjdk:17
LABEL authors="byeonjuhwan"
ARG JAR_FILE=build/libs/translator.jar
COPY ${JAR_FILE} ./translator.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar","./translator.jar"]