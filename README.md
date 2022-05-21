# Rust Data Parallelism Demo
This demo is meant to show the wonders of Data Parallelism in Rust using the [Rayon crate](https://crates.io/crates/rayon).

| Runtime | Sequential | Parallelized |
  | :-----: | :-----------------: | :-----------------: |
  | rust | ~15µs | >1ms |
  | c | ? | ? |
  | java | ~15ms | ~18ms |
  | python | ~170µs | >1s |
