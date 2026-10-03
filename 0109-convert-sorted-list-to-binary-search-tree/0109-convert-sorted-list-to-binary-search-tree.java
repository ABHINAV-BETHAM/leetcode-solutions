/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */import java.util.*;

class Solution {

    public TreeNode sortedListToBST(ListNode head) {

        ArrayList<Integer> list = new ArrayList<>();

        // Convert linked list to ArrayList
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        // Convert ArrayList to BST
        return createTree(list, 0, list.size() - 1);
    }

    public TreeNode createTree(ArrayList<Integer> list,
                               int left, int right) {

        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;

        TreeNode root = new TreeNode(list.get(mid));

        root.left = createTree(list, left, mid - 1);

        root.right = createTree(list, mid + 1, right);

        return root;
    }
}