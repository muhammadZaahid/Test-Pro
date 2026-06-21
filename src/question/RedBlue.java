package question;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RedBlue {
    static int minRemove(String s) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int bal = 0, max = 0;

        for (int i = 0; i < s.length(); i++) {
            bal += s.charAt(i) == 'R' ? 1 : -1;

            if (!map.containsKey(bal)) map.put(bal, i);
            else max = Math.max(max, i - map.get(bal));
        }

        return s.length() - max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(minRemove(sc.next()));
    }
}
