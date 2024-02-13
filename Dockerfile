FROM gradle:8.4.0-jdk17 AS build
USER root

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim
EXPOSE 8080
RUN mkdir /telegram-bot

COPY --from=build /home/gradle/src/files /telegram-bot/files

COPY --from=build /home/gradle/src/build/libs/telegram-bot-wine-art-0.0.1-SNAPSHOT.jar /telegram-bot/

RUN mv /telegram-bot/*.jar /telegram-bot/wine-art-bot.jar

WORKDIR /telegram-bot
ENTRYPOINT ["java","-jar","wine-art-bot.jar"]
