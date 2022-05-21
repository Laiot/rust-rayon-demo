import numpy as np
import multiprocessing as mp
import time

def factorial(n):
    if n < 2:
        return 1
    else:
        return n * factorial(n - 1)

def seq_iterator(fooVector):
    return sum(map(factorial, filter(lambda x: x > 5, fooVector)))

def par_iterator(fooVector):
    pool = mp.Pool(mp.cpu_count())
    res = sum(pool.map(factorial, filter(lambda x: x > 5, fooVector)))
    pool.close
    return res
            
start_time = time.time_ns() / 1000000
fooVector = np.arange(10)
print("The sum is " + str(seq_iterator(fooVector)))
print("--- %s milliseconds ---" % ((time.time_ns() / 1000000) - start_time))
print("The sum is " + str(par_iterator(fooVector)))
print("--- %s milliseconds ---" % ((time.time_ns() / 1000000) - start_time))
