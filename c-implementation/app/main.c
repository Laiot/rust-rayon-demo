#include <stdio.h>
#include <stdint.h>
#include <sys/time.h>
#include <omp.h>

#define N 100000

/**
 * Simple implementation of an algorithm to find the fibonacci sequence given a starting number.
 */
uint64_t fibonacci(uint64_t x) {
    if(x==0) return 0;
    if(x==1) return 1;

    return fibonacci(x-1) + fibonacci(x-2);
}

/**
 * Simple implementation of an algorithm which returns the factorial of the non-negative integer i
 *  calculated from fn(i-1).
 */
uint64_t factorial(uint64_t x) {
    if(x<2) return 1;

    return x*factorial(x-1);
}

/**
 * Simple sequential iterator where a given vector is filtered for values greater than five, found
 * the fibonacci value for each filtered value and finally summed them up.
 */
uint64_t seq_iterator(uint64_t *foo_vector){
    uint64_t total = 0;

    for(size_t i=0; i<N; i++) {
        if(foo_vector[i]>5) {
            total = total+factorial(foo_vector[i]);
        }
    }

    return total;
}

/**
 * Simple parallel iterator where a given vector is filtered for values greater than five, found
 * the fibonacci value for each filtered value and finally summed them up.
 * The whole collection is automatically split among multiple threads, updates being processed 
 * independently without issues.
*/
uint64_t par_iterator(uint64_t *foo_vector) {
    uint64_t total = 0;

    #pragma omp parallel reduction(+:total)
    {
        for(size_t i=omp_get_thread_num(); i<N; i+=omp_get_num_threads()) {
            if(foo_vector[i]>5) {
                total = total+factorial(foo_vector[i]);
            }
        }
    }

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

int main() {

    uint64_t foo_vector[N];
    struct timeval start, end;
    long seconds, micros;

    for(size_t i=0; i<N; i++) {
        foo_vector[i] = i;
    }


    gettimeofday(&start, NULL);
    printf("The sum is %lu\n", seq_iterator(foo_vector));
    gettimeofday(&end, NULL);
    
    seconds = end.tv_sec - start.tv_sec;
    micros = end.tv_usec - start.tv_usec;
    printf("The elapsed time is %ld seconds and %ld micros\n", seconds, micros);
    
    gettimeofday(&start, NULL);
    printf("The sum is %lu\n", par_iterator(foo_vector));
    gettimeofday(&end, NULL);

    seconds = end.tv_sec - start.tv_sec;
    micros = end.tv_usec - start.tv_usec;
    printf("The elapsed time is %ld seconds and %ld micros\n", seconds, micros);

    // println!("The sum is {}", unsafe_par_iterator(&mut foo_vector));
}