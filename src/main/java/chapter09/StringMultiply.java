package chapter09;

public class StringMultiply
{
    public static void main(String[] args)
    {
        System.out.println(multiply("123456789", "987654321"));
    }

    public static String multiply(String num1, String num2) {
        String result = "";
        for(int i = num1.length() - 1;i >= 0;i--){
            int carry = 0;
            int first = num1.charAt(i) - '0';
            StringBuilder current = new StringBuilder();
            for(int j = num2.length() - 1;j >= 0;j--){
                int second = num2.charAt(j) - '0';
                current.insert(0, (first * second + carry) % 10);
                carry = (first * second + carry) / 10;
            }
            if (carry != 0) current.insert(0, carry);
            result = add(current.toString(), result, num1.length() - i - 1);

        }
        return result;
    }

    public static String add(String num1, String num2, int count){
        if(num2.length() == 0) return num1;
        // 填充 0
        for(int index = 0;index < count;index++){
            num1 += '0';
        }
        // 对位相加
        int i, j;
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        for(i = num1.length() - 1, j = num2.length() - 1;j >= 0;i--, j--){
            int first = num1.charAt(i) - '0';
            int second = num2.charAt(j) - '0';
            sb.insert(0, (first + second + carry) % 10);
            carry = (first + second + carry) / 10;
        }
        if(carry != 0 && i == -1) sb.insert(0, carry);
        if(i != -1) sb.insert(0, Integer.parseInt(num1.substring(0, i + 1)) + carry);
        return sb.toString();
    }
}
