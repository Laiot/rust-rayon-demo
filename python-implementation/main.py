import numpy as np
import time

def factorial(n):
    if n < 2:
        return 1
    else:
        return n * factorial(n - 1)

def seq_iterator(fooVector):
    sum = 0
    for value in fooVector:
        if value > 5:
            sum += factorial(value)
    return sum
            
start_time = time.time_ns() / 1000000
fooVector = np.arange(10);
print("The sum is " + str(seq_iterator(fooVector)));
print("--- %s milliseconds ---" % ((time.time_ns() / 1000000) - start_time))
