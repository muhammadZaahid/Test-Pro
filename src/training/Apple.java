package training;

/*
Apple Game - Right Turn Only (0-1 BFS)

State: (row, col, eaten, dir)
- Move forward: cost 0 -> addFirst (high priority)
- Turn right:    cost 1 -> addLast  (low priority)

Time: O(N*N * M * 4) — same as C++ 0-1 BFS

=========================================
SAMPLE INPUT (10 Test Cases)
=========================================
10
5
0 1 0 0 0
0 0 0 2 0
0 0 0 0 0
0 3 0 0 0
0 0 0 0 0
5
0 1 0 0 0
0 0 0 0 0
0 2 0 0 0
0 0 0 3 0
0 0 0 0 0
5
0 1 2 3 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
5
0 0 0 0 0
0 1 0 0 0
0 0 0 0 0
0 0 0 2 0
0 0 0 0 0
5
0 1 0 0 0
-1 -1 -1 -1 0
0 0 0 0 0
0 2 0 3 0
0 0 0 0 0
6
0 1 0 0 0 0
0 0 0 0 0 0
0 0 2 0 0 0
0 0 0 0 0 0
0 3 0 0 4 0
0 0 0 0 0 0
7
0 0 0 0 0 0 0
0 1 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 2 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 3 0
0 0 0 0 0 0 0
5
0 0 -1 0 0
-1 1 -1 0 0
0 0 -1 2 0
0 -1 -1 -1 0
0 0 0 0 3
5
0 1 0 0 0
0 -1 0 0 0
0 0 2 0 0
0 0 -1 0 0
0 0 0 3 0
5
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0

=========================================
EXPECTED OUTPUT
=========================================
#1 2
#2 3
#3 0
#4 3
#5 2
#6 4
#7 4
#8 -1
#9 3
#10 -1
=========================================
*/

import java.util.*;

public class Apple {
    static final int INF = Integer.MAX_VALUE;
    static final int[] dRow = {0, 1, 0, -1}; // right, down, left, up
    static final int[] dCol = {1, 0, -1, 0};

    static int solve(int[][] map, int totalApple) {
        int size = map.length;

        // dp[row][col][eaten][dir] = min right turns
        int[][][][] dp = new int[size][size][totalApple + 1][4];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k <= totalApple; k++)
                    Arrays.fill(dp[i][j][k], INF);

        // 0-1 BFS: state = {row, col, eaten, dir}
        ArrayDeque<int[]> deque = new ArrayDeque<>();
        int startEaten = (map[0][0] == 1) ? 1 : 0;
        dp[0][0][startEaten][0] = 0;
        deque.addFirst(new int[]{0, 0, startEaten, 0});

        while (!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int row = cur[0], col = cur[1], eaten = cur[2], dir = cur[3];
            int cost = dp[row][col][eaten][dir];

            for (int turn = 0; turn <= 1; turn++) {
                int nextDir = (dir + turn) % 4;
                int nextRow = row + dRow[nextDir];
                int nextCol = col + dCol[nextDir];
                int nextCost = cost + turn;

                if (nextRow < 0 || nextRow >= size || nextCol < 0 || nextCol >= size) continue;
                if (map[nextRow][nextCol] == -1) continue;

                int nextEaten = eaten + (map[nextRow][nextCol] == eaten + 1 ? 1 : 0);
                if (nextCost < dp[nextRow][nextCol][nextEaten][nextDir]) {
                    dp[nextRow][nextCol][nextEaten][nextDir] = nextCost;
                    if (turn == 0) deque.addFirst(new int[]{nextRow, nextCol, nextEaten, nextDir});
                    else           deque.addLast(new int[]{nextRow, nextCol, nextEaten, nextDir});
                }
            }
        }

        int answer = INF;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int d = 0; d < 4; d++)
                    answer = Math.min(answer, dp[i][j][totalApple][d]);

        return (answer == INF) ? -1 : answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        for (int tc = 1; tc <= testCase; tc++) {
            int size = sc.nextInt();
            int[][] map = new int[size][size];
            int totalApple = 0;

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    map[i][j] = sc.nextInt();
                    totalApple = Math.max(totalApple, map[i][j]);
                }

            System.out.println("#" + tc + " " + solve(map, totalApple));
        }
    }
}
