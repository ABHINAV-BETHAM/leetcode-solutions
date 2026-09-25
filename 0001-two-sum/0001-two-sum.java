class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int cap = Integer.highestOneBit(n) << 2;               // power of 2, always > 2n
        int mask = cap - 1;
        int shift = 32 - Integer.numberOfTrailingZeros(cap);
        int[] keys = new int[cap];
        int[] idx = new int[cap];                              // stores index + 1 (0 = empty slot)

        for (int i = 0; i < n; i++) {
            int need = target - nums[i];

            int h = (need * 0x9E3779B9) >>> shift;             // search for the complement
            while (idx[h] != 0) {
                if (keys[h] == need) return new int[]{idx[h] - 1, i};
                h = (h + 1) & mask;
            }

            h = (nums[i] * 0x9E3779B9) >>> shift;              // store the current number
            while (idx[h] != 0) h = (h + 1) & mask;
            keys[h] = nums[i];
            idx[h] = i + 1;
        }
        return new int[0];                                     // never reached: a solution is guaranteed
    }
}