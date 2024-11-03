package com.Algorithms;

import java.util.*;

public class Intervals {

    /**
     * Given a collection of intervals, merge all overlapping intervals.
     * Input: [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     *
     * @param intervals
     */
    public int[][] mergeIntervals(int[][] intervals) {
        //sort the starting point of the intervals.
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        //Here the interval is considered a pair (an array).
        // We can also represent an interval as a class with start & end.

        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);

        for (int[] curr : intervals) {
            int[] prev = result.get(result.size() - 1); //Returns the last element in the list

            if (prev[1] >= curr[0]) {  //The end point of the previous interval is greater than or equal to the
                // start point of the current interval. Therefore, it needs to be merged.
                result.get(result.size() - 1)[1] = Math.max(prev[1], curr[1]);
            }
            else {
                result.add(curr);
            }
        }

        return result.toArray(int[][]::new);  //This is to convert the ArrayList to array.
    }


    /**
     * Given an array of meeting time intervals consisting of start and end times,
     * determine the minimum number of conference rooms required to hold all meetings.
     * Input: [[0, 30], [5, 10], [15, 20]]
     * Output: 2
     *
     * @param meetings
     * @return
     */
    public int meetingRooms(int[][] meetings) {
        Arrays.sort(meetings, Comparator.comparingInt(o -> o[0]));

        // MinHeap represents the rooms.
        // We store the end times of the meetings.
        Queue<Integer> minHeap = new PriorityQueue<>();

        for (int[] meeting : meetings) {
            // The root of the minHeap gives the earliest ending meeting.
            // Compare it with the current meeting's start time.
            // Are we good to start the current meeting in this meeting room?
            if (!minHeap.isEmpty() && minHeap.peek() <= meeting[0]) {
                // The meeting has finished before the current meeting starts. So free up the room.
                minHeap.poll();
            }
            minHeap.offer(meeting[1]);
        }

        return minHeap.size();
    }
    /**
     * Given an array of meeting time intervals consisting of start and end times,
     * determine if a person could attend all meetings without conflicts.
     * Input = [[0, 30], [5, 10], [15, 20]]
     * Output = false
     *
     * @param intervals
     * @return
     */
    public boolean meetingWithoutConflicts(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        for (int i = 1; i < intervals.length; i++) {
            // If the previous interval's end time is greater than the current interval's start time, return false.
            if (intervals[i - 1][1] > intervals[i][0]) {
                return false;
            }
        }

        return true;
    }
    /**
     * Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
     * Return the intersection of these two interval lists.
     * A = [[0,2],[5,10],[13,23],[24,25]]
     * B = [[1,5],[8,12],[15,24],[25,26]]
     * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
     *
     * @param A
     * @param B
     * @return
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int a = 0, b = 0;
        List<int[]> list = new LinkedList<>();

        while (a < A.length && b < B.length) {
            //The maximum start point from any two intervals and
            // the minimum end point from any two intervals must intersect.
            int maxStartpoint = Math.max(A[a][0], B[b][0]);
            int minEndpoint = Math.min(A[a][1], B[b][1]);

            if (minEndpoint >= maxStartpoint) {
                list.add(new int[]{maxStartpoint, minEndpoint});
            }

            if (minEndpoint == A[a][1])  //increment based on the minimum end point.
                a += 1;
            else
                b += 1;
        }

        return list.toArray(new int[list.size()][]);
    }
}
