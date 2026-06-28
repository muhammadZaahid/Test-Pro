package training;

import java.util.HashMap;
import java.util.Scanner;

public class RedBlue {

    private static int minRemove(String s){
        HashMap<Integer, Integer> first = new HashMap<>();
        first.put(0, -1);

        int balance = 0; int max = 0;
        for(int i = 0; i < s.length(); i++){
            balance += s.charAt(i) == 'R' ? 1 : -1;
            if(!first.containsKey(balance)) first.put(balance, i);
            else max = Math.max(max, i - first.get(balance));
        }

        return s.length() - max;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for(int tc = 0; tc < T; tc++){
            String s = sc.next();
            System.out.println("#" + tc + " " + minRemove(s));
        }
    }
}
