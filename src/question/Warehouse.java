package question;

import java.util.*;

public class Warehouse {
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int INF = 1_000_000_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int tc = sc.nextInt();

        while (tc-- > 0) {

            int h = sc.nextInt();
            int w = sc.nextInt();
            int c = sc.nextInt();

            int[][] g = new int[h][w];

            int gx = 0, gy = 0, ax = 0, ay = 0;

            List<int[]> wh = new ArrayList<>();

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    g[i][j] = sc.nextInt();

                    if (g[i][j] == 2) {
                        gx = i;
                        gy = j;
                    } else if (g[i][j] == 4) {
                        ax = i;
                        ay = j;
                    } else if (g[i][j] == 3) {
                        wh.add(new int[]{i, j});
                    }
                }
            }

            System.out.println(solve(g, wh, gx, gy, ax, ay, c));
        }
    }

    static long solve(int[][] g, List<int[]> wh,
                      int gx, int gy, int ax, int ay, int c) {

        int h = g.length, w = g[0].length;

        int[][] ds = new int[h][w];
        int[][] da = new int[h][w];

        for (int[] r : ds) Arrays.fill(r, INF);
        for (int[] r : da) Arrays.fill(r, INF);

        Queue<int[]> q = new LinkedList<>();

        ds[gx][gy] = 0;
        q.offer(new int[]{gx, gy});

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int k = 0; k < 4; k++) {
                int nx = cur[0] + dx[k];
                int ny = cur[1] + dy[k];

                if (nx < 0 || ny < 0 || nx >= h || ny >= w) continue;
                if (g[nx][ny] == 1) continue;
                if (ds[nx][ny] <= ds[cur[0]][cur[1]] + 1) continue;

                ds[nx][ny] = ds[cur[0]][cur[1]] + 1;
                q.offer(new int[]{nx, ny});
            }
        }

        da[ax][ay] = 0;
        q.offer(new int[]{ax, ay});

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int k = 0; k < 4; k++) {
                int nx = cur[0] + dx[k];
                int ny = cur[1] + dy[k];

                if (nx < 0 || ny < 0 || nx >= h || ny >= w) continue;
                if (g[nx][ny] == 1) continue;
                if (da[nx][ny] <= da[cur[0]][cur[1]] + 1) continue;

                da[nx][ny] = da[cur[0]][cur[1]] + 1;
                q.offer(new int[]{nx, ny});
            }
        }

        long ans = 0;

        for (int[] p : wh) {
            int x = p[0], y = p[1];

            if (ds[x][y] == INF || da[x][y] == INF) continue;

            long goods = (c - ds[x][y]) / da[x][y] - 1;

            ans = Math.max(ans, goods);
        }

        return Math.max(0, ans);
    }
}
