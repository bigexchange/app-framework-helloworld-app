FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre-alpine

COPY target/basic-demo-app-*.jar /usr/src/
EXPOSE 8083
CMD ["java","-jar","/usr/src/basic-demo-app-*.jar"]
