FROM rust:1.60

WORKDIR /usr/src/rust-rayon-demo
COPY . .

RUN cargo install --path .

CMD ["rust-rayon-demo"]