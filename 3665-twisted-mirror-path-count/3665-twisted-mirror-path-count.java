
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        // dp[i][j][0] = ways entering cell while moving right
        // dp[i][j][1] = ways entering cell while moving down
        int[][][] dp = new int[m][n][2];

        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int right = dp[i][j][0];
                int down = dp[i][j][1];

                if (grid[i][j] == 1) {
                    // Mirror: moving right redirects down
                    if (right > 0 && i + 1 < m) {
                        dp[i + 1][j][1] =
                            (int)((dp[i + 1][j][1] + (long)right) % MOD);
                    }

                    // Mirror: moving down redirects right
                    if (down > 0 && j + 1 < n) {
                        dp[i][j + 1][0] =
                            (int)((dp[i][j + 1][0] + (long)down) % MOD);
                    }

                } else {
                    // Empty cell: can move right or down
                    int ways = (int)(((long)right + down) % MOD);

                    if (j + 1 < n) {
                        dp[i][j + 1][0] =
                            (int)((dp[i][j + 1][0] + (long)ways) % MOD);
                    }

                    if (i + 1 < m) {
                        dp[i + 1][j][1] =
                            (int)((dp[i + 1][j][1] + (long)ways) % MOD);
                    }
                }
            }
        }

        return (int)(((long)dp[m - 1][n - 1][0]
                    + dp[m - 1][n - 1][1]) % MOD);
    }
}
