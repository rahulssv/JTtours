FROM ppc64le/openjdk:8
MAINTAINER rahul_vishwakarma1
COPY jttours-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","/jttours-0.0.1-SNAPSHOT.jar"]