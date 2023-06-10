FROM openjdk:11

EXPOSE 8090

ARG VAR_PROFILE

ENV env_var_profile=${VAR_PROFILE}

ADD target/pragma-monolitico-0.0.1-SNAPSHOT.jar app.jar

CMD ["/bin/sh"]

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${env_var_profile}","/app.jar"]