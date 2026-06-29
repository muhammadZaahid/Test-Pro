package question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class OilField {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] A = new int[N];
        int[] B = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }

        // C[i] = A[i] - B[i] (net profit per field)
        // We need max sum of contiguous subarray of length >= M
        // Using prefix sum with running minimum

        long maxRevenue = Long.MIN_VALUE;
        long prefixSum = 0;
        long minPrefix = 0; // minimum prefix[0..r-M]

        // prefix[0] = 0
        // prefix[i] = sum(C[0..i-1])
        // For subarray [l, r-1] with length r-l >= M:
        //   sum = prefix[r] - prefix[l], where l <= r-M

        // We process r from 1 to N
        // When r >= M, we can consider subarrays ending at r-1 with length >= M
        // minPrefix tracks min(prefix[0..r-M])

        for (int r = 1; r <= N; r++) {
            prefixSum += (A[r - 1] - B[r - 1]);

            if (r >= M) {
                // Update minPrefix with prefix[r-M] before using it
                // Actually, we need min of prefix[0..r-M]
                // We track this incrementally
                long candidateRevenue = prefixSum - minPrefix;
                if (candidateRevenue > maxRevenue) {
                    maxRevenue = candidateRevenue;
                }
            }

            // Update minPrefix with prefix[r] (which becomes prefix[r-M] for future iterations)
            // After processing position r, prefix[r] is available
            // For the next iteration r+1, we need min(prefix[0..(r+1)-M]) = min(prefix[0..r-M+1])
            // So we include prefix[r] in the minimum
            if (prefixSum < minPrefix) {
                minPrefix = prefixSum;
            }
        }

        System.out.println(maxRevenue);
    }
}

/*
#include<iostream>
using namespace std;

const int MAX = 2e5 + 5;

int n,m;
int arr[MAX];
long long prefixSum[MAX];
long long ans,minPrefixSumLeft;

int main(){
	cin >> n >> m;
	prefixSum[0] = 0;
	for(int i = 1; i <= n; i++){
		cin >> arr[i];
		prefixSum[i] = prefixSum[i-1] + arr[i];
	}
	// ide solusi
	// prefix sum berguna untuk menghitung sum dari suatu subarray
	// sum(le .. ri) = prefixSum[ri] - prefixSum[le - 1]
	// berarti kalau coba semua kombinasi left dan right maka bakal nemu subarray maximal tapi O(N^2)
	// jadi cukup looping right aja, sedangkan left itu plih nilai prefixSum[left - 1] yang paling kecil
	// sehingga solusi hanya O(N)
	// karena ukuran subarray minimal m, maka left yang boleh dipilih jaraknya dari right minimal sebesar m
	minPrefixSumLeft = 0;
	ans = -1e18;
	for(int right = m; right <= n; right++){
		minPrefixSumLeft = min(minPrefixSumLeft, prefixSum[right - m]);
		ans = max(ans, prefixSum[right] - minPrefixSumLeft);
	}
	cout << ans << endl;
	return 0;
}
 */
