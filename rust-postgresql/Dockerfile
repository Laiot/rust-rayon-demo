FROM docker.io/rust:1.60

WORKDIR /usr/src/rust-rayon-demo
COPY app .

RUN cargo install --path .

CMD ["rust-rayon-demo"]