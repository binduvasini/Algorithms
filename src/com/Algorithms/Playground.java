package com.Algorithms;

import java.util.*;

public class Playground {

    public static void main(String[] args) {

    }

    static void find3Numbers(int A[],
                             int arr_size, int sum) {
        // Fix the first element as A[i]
        for (int i = 0; i < arr_size - 2; i++) {

            // Find pair in subarray A[i+1..n-1]
            // with sum equal to sum - A[i]
            HashSet<Integer> s = new HashSet<Integer>();
            int curr_sum = sum - A[i];
            for (int j = i + 1; j < arr_size; j++) {
                if (s.contains(curr_sum - A[j]) && curr_sum - A[j] != (int) s.toArray()[s.size() - 1]) {
                    System.out.printf("Triplet is %d, %d, %d", A[i],
                            A[j], curr_sum - A[j]);
                    System.out.println();
                }
                s.add(A[j]);
            }
        }
    }

    static boolean isPalindrome(String str) {
        return isPalindrome(str, 0, str.length() - 1);
    }

    static boolean isPalindrome(String str, int start, int end) {
        if (start >= end)
            return true;
        if (str.charAt(start) != str.charAt(end))
            return false;
        return isPalindrome(str, start + 1, end - 1);
    }

    private static int minDays(int[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        int target = grid.length * grid[0].length;
        int cnt = 0, res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    q.offer(new int[]{i, j});
                    cnt++;
                }
            }
        }
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!q.isEmpty()) {
            int size = q.size();
            if (cnt == target)
                return res;
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for (int[] dir : dirs) {
                    int ni = cur[0] + dir[0];
                    int nj = cur[1] + dir[1];
                    if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[0].length && grid[ni][nj] == 0) {
                        cnt++;
                        q.offer(new int[]{ni, nj});
                        grid[ni][nj] = 1;
                    }
                }
            }
            res++;
        }
        return -1;
    }

    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        int total = 0;
        for (int i = 0; i < nums.length; i++) total += nums[i];
        if (total % 2 != 0) return false;
        List<Integer> list = new ArrayList<Integer>();
        Arrays.sort(nums);
        return helper(nums, list, 0, 0, total);
    }

    public boolean helper(int[] nums, List<Integer> list, int pos, int sum, int total) {
        if (total == 2 * sum) return true;
        if (total < 2 * sum) return false;
        boolean res = false;
        for (int i = pos; i < nums.length; i++) {
            if (i == pos || nums[i - 1] != nums[i]) {
                list.add(nums[i]);
                res = res || helper(nums, list, i + 1, sum + nums[i], total);
                if (res) return true;
                list.remove(list.size() - 1);
            }
        }
        return false;
    }

    int[] count;

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<Integer>();

        count = new int[nums.length];
        int[] indexes = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            indexes[i] = i;
        }
        mergesort(nums, indexes, 0, nums.length - 1);
        for (int i = 0; i < count.length; i++) {
            res.add(count[i]);
        }
        return res;
    }

    private void mergesort(int[] nums, int[] indexes, int start, int end) {
        if (end <= start) {
            return;
        }
        int mid = (start + end) / 2;
        mergesort(nums, indexes, start, mid);
        mergesort(nums, indexes, mid + 1, end);

        merge(nums, indexes, start, end);
    }

    private void merge(int[] nums, int[] indexes, int start, int end) {
        int mid = (start + end) / 2;
        int left_index = start;
        int right_index = mid + 1;
        int inversionCount = 0;
        int[] new_indexes = new int[end - start + 1];

        int sort_index = 0;
        while (left_index <= mid && right_index <= end) {
            if (nums[indexes[left_index]] <= nums[indexes[right_index]]) {
                new_indexes[sort_index] = indexes[left_index];
                count[indexes[left_index]] += inversionCount;
                left_index++;
            }
            else {
                new_indexes[sort_index] = indexes[right_index];
                inversionCount++;
                right_index++;
            }
            sort_index++;
        }
        while (left_index <= mid) {
            new_indexes[sort_index] = indexes[left_index];
            count[indexes[left_index]] += inversionCount;
            left_index++;
            sort_index++;
        }
        while (right_index <= end) {
            new_indexes[sort_index++] = indexes[right_index++];
        }
        if (end + 1 - start >= 0) System.arraycopy(new_indexes, 0, indexes, start, end + 1 - start);
    }


    /*

    HashMap<Integer, Integer> map;
    public List<Integer> countSmaller(int[] nums) {
        int[] num = nums.clone();
        map = new HashMap<>();
        int[] tmp = new int[nums.length];
        divide(nums, 0, nums.length - 1, tmp);

        List<Integer> result = new LinkedList<>();

        for(int i = 0; i < num.length; i++){
            if(map.containsKey(i))
                result.add(map.get(i));
            else
                result.add(0);
        }

        return result;
    }

    private void divide(int[] nums, int start, int end, int[] tmp){
        if(start >= end)
            return;
        int middle = start + (end - start) / 2;
        divide(nums, start, middle, tmp);
        divide(nums, middle + 1, end, tmp);
        conquer(nums, start, end, tmp);
    }

    private void conquer(int[] array, int leftStart, int rightEnd, int[] tmp) {
        int leftEnd = leftStart + (rightEnd - leftStart) / 2; //determine the leftEnd for left array
        int rightStart = leftEnd + 1; //determine the rightStart for the right array
        int i = leftStart, lefti = leftStart, righti = rightStart, inversionCount = 0;

        while (lefti <= leftEnd && righti <= rightEnd) {
            if (array[lefti] <= array[righti]) {
                map.put(lefti, map.getOrDefault(lefti, 0)+inversionCount);
                tmp[i] = array[lefti];
                lefti += 1;
            } else {
                inversionCount += 1;
                tmp[i] = array[righti];
                righti += 1;
            }
            i += 1;
        }
        while(lefti <= leftEnd){
            map.put(lefti, map.getOrDefault(lefti, 0)+inversionCount);
            tmp[i] = array[lefti];
            lefti += 1;
            i += 1;
        }
        while(righti <= rightEnd){
            tmp[i] = array[righti];
            righti += 1;
            i += 1;
        }
        System.arraycopy(tmp, leftStart, array, leftStart, rightEnd - leftStart + 1);
    }

     */

}
