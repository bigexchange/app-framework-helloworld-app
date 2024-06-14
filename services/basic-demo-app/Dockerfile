FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre-jammy

COPY target/basic-demo-app-*-SNAPSHOT.jar /usr/src/basicapp.jar
EXPOSE 8083
CMD ["java","-jar","/usr/src/basicapp.jar"]
