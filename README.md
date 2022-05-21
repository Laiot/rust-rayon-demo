# Rust Data Parallelism Demo
This demo is meant to show the wonders of Data Parallelism in Rust using the [Rayon crate](https://crates.io/crates/rayon).

## Benchmark for iterating 1000 elements

| Runtime | Sequential | Parallelized |
  | :-----: | :-----------------: | :-----------------: |
  | rust | ~15ms | ~5ms |
  | c | ? | ? |
  | java | ~25ms | ~20ms |
  | python | ? | lol |
