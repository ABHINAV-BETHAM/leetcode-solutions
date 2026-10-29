import java.util.*;

class Solution {
    int LOG;
    int[][] up;
    int[] depth, tin, tout, bit;
    List<Integer>[] graph;
    int timer;

    void add(int i, int val) {
        while (i < bit.length) {
            bit[i] ^= val;
            i += i & -i;
        }
    }

    int get(int i) {
        int ans = 0;
        while (i > 0) {
            ans ^= bit[i];
            i -= i & -i;
        }
        return ans;
    }

    void rangeUpdate(int l, int r, int val) {
        add(l, val);
        add(r + 1, val);
    }

    int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int t = a;
            a = b;
            b = t;
        }

        int diff = depth[a] - depth[b];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                a = up[j][a];
            }
        }

        if (a == b) return a;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][a] != up[j][b]) {
                a = up[j][a];
                b = up[j][b];
            }
        }

        return up[0][a];
    }

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        bit = new int[n + 2];
        timer = 0;

        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] order = new int[n];
        int size = 0;

        order[size++] = 0;
        parent[0] = 0;

        for (int i = 0; i < size; i++) {
            int u = order[i];

            for (int v : graph[u]) {
                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                order[size++] = v;
            }
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        int[] stack = new int[n];
        int[] index = new int[n];
        int top = 0;
        stack[0] = 0;

        while (top >= 0) {
            int u = stack[top];

            if (index[u] == 0) {
                tin[u] = ++timer;
            }

            if (index[u] < graph[u].size()) {
                int v = graph[u].get(index[u]++);

                if (v != parent[u]) {
                    stack[++top] = v;
                }
            } else {
                tout[u] = timer;
                top--;
            }
        }

        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            rangeUpdate(tin[i], tout[i], mask);
        }

        List<Boolean> result = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');

                rangeUpdate(tin[u], tout[u], oldMask ^ newMask);
                chars[u] = c;

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int w = lca(u, v);

                int mask = get(tin[u]) ^ get(tin[v]);
                mask ^= 1 << (chars[w] - 'a');

                result.add((mask & (mask - 1)) == 0);
            }
        }

        return result;
    }
}