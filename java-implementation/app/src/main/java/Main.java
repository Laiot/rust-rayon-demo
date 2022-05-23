import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        List<Integer> fooVector = new ArrayList<>();
        IntStream.range(1, 10).forEach(fooVector::add);

        System.out.printf("The sum is: %d\n", seq_iterator(fooVector)); 
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.printf("Running time in ms: %d\n", totalTime / 1000000);

        System.out.printf("The sum is: %d\n", par_iterator(fooVector)); 
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.printf("Running time in ms: %d\n", totalTime / 1000000);
    }

    public static int fibonacci(int n){
         switch(n){
            case 0:
                return 1;
            
            case 1:
                return 1;

            default:
                return fibonacci(n - 1) + fibonacci(n - 2);
         }
    }

    public static int factorial(int n){
        if (n < 2) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static int seq_iterator(List<Integer> fooVector){
        return fooVector.stream()
            .filter(x -> x>5)
            .map(x -> factorial(x))
            .reduce(0, (x,y) -> x+y);
    }

    public static int par_iterator(List<Integer> fooVector){
        return fooVector.parallelStream()
            .filter(x -> x>5)
            .map(x -> factorial(x))
            .reduce(0, (x,y) -> x+y);
    }
}