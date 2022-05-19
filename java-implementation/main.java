import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        int[] fooVector = IntStream.range(1, 10).toArray();

        System.out.printf("The sum is: %d\n", seq_iterator(fooVector)); 
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
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

    public static int seq_iterator(int[] fooVector){
        int sum = 0;
        for(int value: fooVector){
            if (value > 5){
                sum += factorial(value);
            }
        }
        return sum;
    }
}