package question;

import java.util.Arrays;

public class RoboGarbage {
    static long[][] dp;
    static long[] garbage;
    static int n;
    static long robotCost;

    static long dfs(int pos, int robotPos) {
        if (pos == n) return 0;
        if (dp[pos][robotPos] != -1) return dp[pos][robotPos];

        long newRobot =
                robotCost + dfs(pos + 1, pos);

        long useCurrentRobot =
                (long) (pos - robotPos) * garbage[pos]
                        + dfs(pos + 1, robotPos);

        return dp[pos][robotPos] =
                Math.min(newRobot, useCurrentRobot);
    }

    public static void main(String[] args) {
        garbage = new long[]{
                1,1,1,1,1,1,1,1,1,1,
                1,1,1,1,1,1,1,1,1,1
        };

        n = garbage.length;
        robotCost = 100;

        dp = new long[n][n];
        for (long[] row : dp) Arrays.fill(row, -1);

        int first = 0;
        while (first < n && garbage[first] == 0) first++;

        System.out.println(
                first == n ? 0 : robotCost + dfs(first + 1, first)
        );
    }
}
