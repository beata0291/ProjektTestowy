public class Testowa {
    public static void main(String[] args) {
        System.out.println(getMyAge());
        System.out.println(getMyName());
        System.out.println(numberEven(8));
        System.out.println(twoNumber(31,21));
        printMatEquationResults(5,6);
        System.out.println(number3(15));
    }


    private static double twoNumber(double number1, double number2) {
        return number1 + number2;
    }

    private static void printMatEquationResults(double arg1, double arg2) {
        System.out.println(arg1 + arg2);
        System.out.println(arg1 - arg2);
        System.out.println(arg1 * arg2);
    }

    private static boolean numberEven(double number) {
        return number % 2 == 0;

    }

    private static boolean number3 (double number) {
        return number % 3==0  && number % 5 ==0 ;

    }

    private static String getMyName() {
        return "Beti";
    }

    private static int getMyAge() {
        return 29;
    }


}
