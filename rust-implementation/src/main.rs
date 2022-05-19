use rayon::prelude::*;
use std::time::Instant;

/**
 * Simple implementation of an algorithm to find the fibonacci sequence given a starting number.
 */
fn fibonacci(n: u32) -> u32 {
    match n {
        0 => 1,
        1 => 1,
        _ => fibonacci(n - 1) + fibonacci(n - 2),
    }
}

/**
 * Simple implementation of an algorithm which returns the factorial of the non-negative integer i
 *  calculated from fn(i-1).
 */
fn factorial(n: u32) -> u32 {
    if n < 2 {
        1
    } else {
        n * factorial(n - 1)
    }
}

/**
 * Simple sequential iterator where a given vector is filtered for values greater than five, found
 * the fibonacci value for each filtered value and finally summed them up.
 */
fn seq_iterator(foo_vector: &mut Vec<u32>) -> u32 {
    let total: u32 = foo_vector.iter_mut()
        .filter(|foo| **foo > 5)
        .map(|foo| factorial(*foo))
        .sum();
    return total;
}

/**
 * Simple parallel iterator where a given vector is filtered for values greater than five, found
 * the fibonacci value for each filtered value and finally summed them up.
 * The whole collection is automatically split among multiple threads, updates being processed 
 * independently without issues.
*/
fn par_iterator(foo_vector: &mut Vec<u32>) -> u32 {
    let total: u32 = foo_vector.par_iter_mut()
        .filter(|foo| **foo > 5)
        .map(|foo| factorial(*foo))
        .sum();
    return total;
}

/**
 * This code would be fine sequentially but by trying to make it parallel the compiler will return
 * error. This happens because there would be a data race if multiple threads tried to update 
 * total at the same time. You could solve this by using an atomic type for total, or by using 
 * Rayon’s built-in parallel sum() or your own custom fold+reduce on the iterator.
 */
// fn unsafe_par_iterator(foo_vector: &mut Vec<u32>) -> u32 {
//     let mut total = 0;
//     foo_vector.par_iter_mut()
//         .filter(|foo| **foo > 5)
//         .for_each(|foo| total += fibonacci(*foo));
//     return total;
// }

fn main() {
    let now = Instant::now();

    let mut foo_vector: Vec<u32> = (0..10).collect();

    println!("The sum is {}", seq_iterator(&mut foo_vector));

    //println!("The sum is {}", par_iterator(&mut foo_vector));

    // println!("The sum is {}", unsafe_par_iterator(&mut foo_vector));

    let elapsed = now.elapsed();
    println!("Running time: {:.2?}", elapsed);
}
