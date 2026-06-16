package training;

import java.util.Scanner;

public class CarParking {

    static long parity(long[] dist){
        long maxDist = 0;
        for(long d : dist){
            maxDist = Math.max(maxDist,d);
        }

        long turns = (long) Math.ceil((Math.sqrt(1.0 + 8.0 * maxDist) - 1) / 2);
        long actual = turns * (turns + 1) / 2;

        long expectedParity = (actual - dist[0]) & 1;
        for(long d : dist){
            if(((actual - d) & 1) != expectedParity){
                return -1;
            }
        }

        if(expectedParity == 1 && (turns & 1) == 1){
            return turns + 2;
        }else if(expectedParity == 1){
            return turns + 1;
        }else {
            return turns;
        }
    }

    public static void main(String[] args){
        Scanner sc =  new Scanner(System.in);
        int T = sc.nextInt();

        for(int tc = 0; tc <= T; tc++){
            int carCount = sc.nextInt();
            sc.nextLong();

            long parkRow = sc.nextLong();
            long parkCol = sc.nextLong();

            long[] dist = new long[carCount];

            for(int i = 0; i < carCount; i++){
                long carRow = sc.nextLong();
                long carCol = sc.nextLong();
                dist[i] = Math.abs(parkRow - carRow) + Math.abs(parkCol - carCol);
            }

            System.out.println("#" + tc + " " + parity(dist));
        }
    }
}
