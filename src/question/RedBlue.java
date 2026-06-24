package question;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
SOAL:
Diberikan kalung dengan batu merah (R) dan biru (B).
Buat jumlah R = B dengan menghapus batu dari ujung kiri/kanan.
Return jumlah batu minimum yang harus dihapus.

INSIGHT KUNCI:
- Hapus dari ujung = sisa adalah subarray contiguous
- Cari subarray terpanjang dengan R = B
- Jawaban = panjang string - panjang subarray seimbang terpanjang
- Gunakan prefix sum: R=+1, B=-1
  Jika prefix[i] == prefix[j], maka subarray i+1..j seimbang

CONTOH TEST CASE:

Input:
3
BBRRBRBRBRBBR
RBRB
RRRBBB

Output:
# 1 1
# 2 0
# 3 0
*/
public class RedBlue {

    static int minRemove(String s) {
        Map<Integer, Integer> first = new HashMap<>();
        first.put(0, -1);

        int balance = 0, max = 0;

        for (int i = 0; i < s.length(); i++) {
            balance += s.charAt(i) == 'R' ? 1 : -1;

            if (!first.containsKey(balance)) first.put(balance, i);
            else max = Math.max(max, i - first.get(balance));
        }

        return s.length() - max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();

        for (int tc = 1; tc <= testCaseCount; tc++) {
            String s = sc.next();
            System.out.println("# " + tc + " " + minRemove(s));
        }

        sc.close();
    }
}
