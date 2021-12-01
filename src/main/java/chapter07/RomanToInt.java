package chapter07;

public class RomanToInt
{
    public static void main(String[] args)
    {


    }


    public static boolean isPalindrome(String s) {
        String str = s.toLowerCase();
        int start = 0;
        int last = str.length() - 1;
        boolean flag = true;
        while(start < last){
            if(str.charAt(start) == str.charAt(last)){
                start++;
                last--;
            }else{
                if(isInteger(str.charAt(start)) || isInteger(str.charAt(last)))
                    return false;
                if(str.charAt(start) < 'a' || str.charAt(start) > 'z'){
                    flag = false;
                    start++;
                }
                if(str.charAt(last) < 'a' || str.charAt(last) > 'z'){
                    flag = false;
                    last--;
                }
                if(flag)
                    return false;
                flag = true;
            }
        }
        return true;
    }

    public static boolean isInteger(char c){
        return c <= '9' && c >= '0';
    }
}
