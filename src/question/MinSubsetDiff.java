package question;

import java.util.Scanner;

public class MinSubsetDiff {
    private static int[] weights;
    private static boolean[] dp;

    private static int solve(int total){
        dp = new boolean[total / 2 + 1];
        dp[0] = true;

        for(int weight : weights){
            for(int j = total / 2; j >= weight; j--){
                if(dp[j - weight]){
                    dp[j] = true;
                }
            }
        }

        int best = 0;
        for(int i = total / 2; i >= 0; i--){
            if(dp[i]){
                best = i;
                break;
            }
        }

        return Math.abs(total - 2 * best);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int tc = 0; tc < T; tc++){

            int n = sc.nextInt();
            weights = new int[n];
            int total = 0;

            for(int i = 0; i < n; i++){
                weights[i] = sc.nextInt();
                total += weights[i];
            }

            System.out.println(solve(total));
        }
    }
}
