# set up environment
FROM ubuntu:20.04

RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

RUN apt-get update && apt-get -y install gnupg

RUN apt-get update && apt-get install apt-transport-https

RUN apt-get -y install software-properties-common

RUN wget -O- https://apt.corretto.aws/corretto.key | apt-key add -
RUN add-apt-repository 'deb https://apt.corretto.aws stable main'

RUN apt-get update
RUN apt-get install -y java-17-amazon-corretto-jdk

WORKDIR /app
COPY . /app

CMD ["update-alternatives", "--list", "java"]

RUN ./gradlew bootJar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sources/app/app/build/libs/app-0.0.1-SNAPSHOT.jar"]