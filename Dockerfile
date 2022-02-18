FROM gradle:7-jdk17-alpine AS build

WORKDIR /src
COPY . .

RUN gradle bootJar --no-daemon


FROM alpine:3.15.0

RUN apk add --update-cache \
    openjdk17 \
  && rm -rf /var/cache/apk/*

COPY --from=build /src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]
