# Compile step
FROM registry.redhat.io/codeready-workspaces/stacks-cpp-rhel8 as build

ADD app/* .
RUN gcc main.c -fopenmp -o /home/jboss/main.o

# Build step
FROM registry.access.redhat.com/ubi8/ubi:8.0

ENV OMP_NUM_THREADS=8

RUN useradd -u 1001 dev && yum install -y libgomp
USER dev
WORKDIR /home/dev

COPY --from=build --chown=1001:0 /home/jboss/main.o /home/dev

CMD ./main.o
