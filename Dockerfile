from java:8

volume /usr/src/instagit
copy . /usr/src/instagit
run cd /usr/src/instagit && ./gradlew -g .gradle shadowJar && cp -a build/libs/instagit.jar /

volume /repos
workdir /repos
expose 8080
cmd ["java", "-jar", "/instagit.jar"]
