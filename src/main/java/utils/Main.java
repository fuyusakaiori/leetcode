package utils;

import java.math.BigInteger;
import java.util.*;

public class Main{

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        int[] numbers = new int[count];
        for (int index = 0;index < count;index++){
            numbers[index] = in.nextInt();
        }
        long start = System.currentTimeMillis();
        System.out.println(solution(numbers));
        System.out.println(System.currentTimeMillis() - start);
    }

    public static long solution(int[] numbers){
        long count = 0;
        for (int left = 0;left < numbers.length;left++){
            BigInteger product = new BigInteger("1");
            for (int right = left;right < numbers.length;right++){
                product = product.multiply(new BigInteger(String.valueOf(numbers[right])));
                count += getZero(product);
            }
        }
        return count;
    }

    public static long getZero(BigInteger number){
        long count = 0;
        while (number.mod(new BigInteger("10")).compareTo(new BigInteger("0")) == 0){
            count++;
            number = number.divide(new BigInteger("10"));
        }
        return count;
    }
}

