FROM openjdk:17-jdk-alpine
MAINTAINER rahul_vishwakarma1
COPY jttours-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","/jttours-0.0.1-SNAPSHOT.jar"]

FROM ubuntu:22.04
WORKDIR /opt

RUN apt-get update -y && \
    apt-get install -y wget git unzip

RUN wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.2%2B8/OpenJDK17U-jdk_ppc64le_linux_hotspot_17.0.2_8.tar.gz && \
    tar -C /usr/local -xzf OpenJDK17U-jdk_ppc64le_linux_hotspot_17.0.2_8.tar.gz &&\
    export JAVA_HOME=/usr/local/jdk-17.0.2+8/ && \    
    export JAVA17_HOME=/usr/local/jdk-17.0.2+8/ && \    
    export PATH=$PATH:/usr/local/jdk-17.0.2+8/bin && \
    ln -sf /usr/local/jdk-17.0.2+8/bin/java /usr/bin/ && \
    rm -f OpenJDK17U-jdk_ppc64le_linux_hotspot_17.0.2_8.tar.gz

COPY jttours-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","/opt/jttours-0.0.1-SNAPSHOT.jar"]