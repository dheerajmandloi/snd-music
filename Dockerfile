FROM tomcat:9.0-jdk11
RUN rm -rf /usr/local/tomcat/webapps/*
COPY dist/My_Music.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
