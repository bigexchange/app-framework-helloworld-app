FROM public.ecr.aws/docker/library/eclipse-temurin:11-jre-alpine

COPY target/basic-demo-app-1.1.0-SNAPSHOT.jar /home/basicapp.jar
EXPOSE 8083
CMD ["java","-jar","/home/basicapp.jar"]
