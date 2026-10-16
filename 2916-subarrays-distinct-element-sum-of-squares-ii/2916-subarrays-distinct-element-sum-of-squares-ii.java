
class Solution {
    static final int MOD = 1_000_000_007;

    long[] sum;
    long[] square;
    long[] lazy;
    int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        square = new long[4 * n];
        lazy = new long[4 * n];

        Map<Integer, Integer> last = new HashMap<>();
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            int prev = last.getOrDefault(x, -1);

            // Distinct counts increase by 1 for starts
            // after the previous occurrence of x.
            update(1, 0, n - 1, prev + 1, i, 1);

            last.put(x, i);

            // Sum of squared distinct counts for all
            // subarrays ending at index i.
            ans = (ans + square[1]) % MOD;
        }

        return (int) ans;
    }

    void apply(int node, int l, int r, long val) {
        long len = r - l + 1L;

        square[node] = (
            square[node]
            + 2L * val % MOD * sum[node] % MOD
            + len % MOD * val % MOD * val % MOD
        ) % MOD;

        sum[node] = (sum[node] + len * val) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }

    void push(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) {
            return;
        }

        int mid = (l + r) / 2;

        apply(node * 2, l, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, r, lazy[node]);

        lazy[node] = 0;
    }

    void update(int node, int l, int r,
                int ql, int qr, long val) {
        if (ql > r || qr < l) {
            return;
        }

        if (ql <= l && r <= qr) {
            apply(node, l, r, val);
            return;
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        update(node * 2, l, mid, ql, qr, val);
        update(node * 2 + 1, mid + 1, r, ql, qr, val);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        square[node] = (square[node * 2]
                      + square[node * 2 + 1]) % MOD;
    }
}
