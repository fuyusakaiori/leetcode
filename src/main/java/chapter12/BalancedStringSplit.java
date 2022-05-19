package chapter12;

public class BalancedStringSplit
{
    public static void main(String[] args)
    {
        balancedStringSplit("RLLLLRRRLR");
    }


    public static int balancedStringSplit(String s) {
        return recursive(s, 0);
    }

    public static int recursive(String string, int index){
        if(index == string.length())
            return 1;
        int res = 0;
        int first = 0;
        int second = 0;
        for(int i = index;i < string.length();i++){
            if('R' == string.charAt(i)){
                first++;
            }else{
                second++;
            }
            if(first == second)
                res += recursive(string, index + first + second);
        }

        return res;
    }

}
