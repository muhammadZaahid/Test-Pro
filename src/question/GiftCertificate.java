package question;

import java.util.Scanner;

public class GiftCertificate {
    private static final int MOD = 1000_000_000;
    private static String A;
    private static int[][][] dp;

    private static int solve(int pos, int remSum, boolean tight){
        if(remSum < 0){
            return 0;
        }
        if(pos == A.length()){
            return remSum == 0 ? 1 :0;
        }
        if(dp[pos][remSum][tight ? 1 : 0] != -1){
            return dp[pos][remSum][tight ? 1 : 0];
        }

        int limit = tight ? (A.charAt(pos) - '0') : 9;
        int ans = 0;

        for(int digit = 0; digit <= limit; digit++){
            boolean newTight = tight && (digit == limit);
            ans = (ans + solve(pos + 1, remSum - digit, newTight)) % MOD;
        }

        dp[pos][remSum][tight ? 1 : 0] = ans;
        return ans;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int tc = 0; tc < T; tc++){
            A = sc.next();
            int S = sc.nextInt();
            int n = A.length();
            dp = new int[n + 1][S + 1][2];

            for(int i = 0; i <=n; i++){
                for(int j = 0; j <= S; j++){
                    dp[i][j][0] = -1;
                    dp[i][j][1] = -1;
                }
            }

            System.out.println(solve(0, S, true));
        }
    }
}