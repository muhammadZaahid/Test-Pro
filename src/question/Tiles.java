package question;

import java.util.Scanner;

public class Tiles {
    static final int SIZE = 401; // width/height maksimal 0-400

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();

        for (int t = 0; t < testCases; t++) {
            int n = sc.nextInt();
            int k = sc.nextInt();

            int[][] grid = new int[SIZE][SIZE];
            for (int i = 0; i < n; i++) {
                int width = sc.nextInt();
                int height = sc.nextInt();
                grid[width][height]++;
            }

            System.out.println(findMinDifference(grid, k));
        }
    }

    // SATU method ini berisi seluruh logic: prefix sum + binary search
    static int findMinDifference(int[][] grid, int k) {
        // Ubah grid jadi peta kumulatif (prefix sum 2D)
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (x > 0) grid[x][y] += grid[x-1][y];
                if (y > 0) grid[x][y] += grid[x][y-1];
                if (x > 0 && y > 0) grid[x][y] -= grid[x-1][y-1];
            }
        }

        // Cari ukuran kotak terkecil yang muat >= k tile
        int low = 0, high = 400, answer = 400;
        while (low <= high) {
            int boxSize = (low + high) / 2;
            if (hasEnoughTiles(grid, boxSize, k)) {
                answer = boxSize;
                high = boxSize - 1;
            } else {
                low = boxSize + 1;
            }
        }
        return answer;
    }

    // Cek apakah ada kotak (boxSize+1) x (boxSize+1) yang isinya >= k tile
    static boolean hasEnoughTiles(int[][] grid, int boxSize, int k) {
        for (int x = 0; x + boxSize < SIZE; x++) {
            for (int y = 0; y + boxSize < SIZE; y++) {
                int count = grid[x+boxSize][y+boxSize];
                if (x > 0) count -= grid[x-1][y+boxSize];
                if (y > 0) count -= grid[x+boxSize][y-1];
                if (x > 0 && y > 0) count += grid[x-1][y-1];
                if (count >= k) return true;
            }
        }
        return false;
    }
}

/*
=========================================
SAMPLE INPUT (10 Test Cases)
=========================================
10
1 1
5 5
2 2
0 0
0 0
2 2
0 0
5 5
3 2
0 0
0 0
10 10
3 3
0 0
0 5
5 0
4 2
1 1
1 2
10 10
10 11
5 3
0 0
1 1
2 2
100 100
101 101
2 1
50 50
200 200
6 4
0 0
0 1
1 0
1 1
50 50
50 51
3 2
400 400
400 400
0 0

=========================================
EXPECTED OUTPUT
=========================================
0
0
5
0
5
1
2
0
1
0
=========================================
*/
