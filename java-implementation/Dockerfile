FROM  registry.access.redhat.com/ubi8/openjdk-11 as build

ADD demo/ .
RUN mvn package

FROM  registry.access.redhat.com/ubi8/openjdk-11

COPY --from=build --chown=jboss:0 /home/jboss/target/demo.jar .
CMD java -jar demo.jar