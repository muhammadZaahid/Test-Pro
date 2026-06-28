package training;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;


public class AppleTrain {
    private static final int INF = Integer.MAX_VALUE;
    private static int[] dRow = new int[]{0, 1, 0, -1};
    private static int[] dCol = new int[]{1, 0, -1, 0};

    private static int solve(int[][] map, int totalApple){

        int n = map.length;

        if(map[0][0] == -1){
            return -1;
        }

        int[][][][] dp = new int[n][n][totalApple + 1][4];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k <= totalApple; k++){
                    Arrays.fill(dp[i][j][k], INF);
                }
            }
        }

        ArrayDeque<int[]> deque = new ArrayDeque<>();

        int startEaten = (map[0][0] == 1)  ? 1 : 0;

        dp[0][0][startEaten][0] = 0;
        deque.addFirst(new int[]{0,0,startEaten,0});

        while (!deque.isEmpty()){

            int[] cur = deque.pollFirst();

            int row = cur[0];
            int col = cur[1];
            int eaten = cur[2];
            int dir = cur[3];

            int cost = dp[row][col][eaten][dir];

            for(int turn = 0; turn <= 1; turn++){

                int nextDir = (dir + turn) % 4;

                int nextRow = row + dRow[nextDir];
                int nextCol = col + dCol[nextDir];

                if(nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n || map[nextRow][nextCol] == -1){
                    continue;
                }

                int nextCost = cost + turn;

                int nextEaten = eaten;

                if(map[nextRow][nextCol] == eaten + 1){
                    nextEaten++;
                }

                if(nextCost < dp[nextRow][nextCol][nextEaten][nextDir]){
                    dp[nextRow][nextCol][nextEaten][nextDir] = nextCost;

                    if(turn == 0){
                        deque.addFirst(new int[]{nextRow, nextCol, nextEaten, nextDir});
                    }else {
                        deque.addLast(new int[]{nextRow, nextCol, nextEaten, nextDir});
                    }
                }
            }
        }

        int answer = INF;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int d = 0; d < 4; d++){
                    answer = Math.min(answer, dp[i][j][totalApple][d]);
                }
            }
        }

        return answer == INF ? -1 : answer;

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for(int tc = 0; tc < T; tc++){

            int n = sc.nextInt();
            int[][] map = new int[n][n];
            int totalApple = 0;

            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    map[i][j] = sc.nextInt();

                    if(map[i][j] > 0){
                        totalApple = Math.max(totalApple, map[i][j]);
                    }
                }
            }

            System.out.println("#" + tc + " " + solve(map, totalApple));
        }
    }
}
