FROM alpine:3.15.0

RUN apk add --update-cache \
    openjdk17 \
  && rm -rf /var/cache/apk/*

COPY . .
CMD ./gradlew run --no-daemon