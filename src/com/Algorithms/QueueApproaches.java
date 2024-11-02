package com.Algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;

/**
 * The inner classes are static because they need to be clubbed inside this class.
 * But the actual implementation in the interview can be a normal class.
 */
public class QueueApproaches {

    /**
     * Implement a hit counter that records and returns the number of hits in the last 5 minutes.
     */
    static class HitCounter {
        private Queue<Integer> queue;

        public HitCounter() {
            queue = new LinkedList<>();
        }

        /**
         * Record a hit.
         *
         * @param timestamp - The current timestamp (in seconds granularity) and
         *                  is strictly increasing, each new call to hit() will have a timestamp greater than or equal to previous calls
         */
        public void hit(int timestamp) {  //Runtime: O(1)
            queue.add(timestamp);
        }

        /**
         * Return the number of hits in the past 5 minutes.
         *
         * @param timestamp - The current timestamp (in seconds granularity).
         */
        public int getHits(int timestamp) {//Runtime: O(n) where n is the number of hits we are removing from the queue.
            // Remove hits that are older than 5 minutes
            int oldTimestamp = timestamp - 300;  // 300 because 5 minutes means 300 seconds.
            while (!queue.isEmpty() && queue.peek() <= oldTimestamp) {
                queue.poll();
            }

            return queue.size();
        }

        /*
            HitCounter counter = new HitCounter();
            counter.hit(1);   // hit at timestamp 1
            counter.hit(2);   // hit at timestamp 2
            counter.hit(3);   // hit at timestamp 3
            System.out.println(counter.getHits(4));  //   3

            counter.hit(300); // hit at timestamp 300
            System.out.println(counter.getHits(300)); //  4

            System.out.println(counter.getHits(302)); //  2
        */
    }

    /**
     * Implement a moving average class that computes the average of values within a specified time window.
     */
    static class TimeBasedMovingAverage {
        record Data(double value, long timestamp) {
        }

        private final Queue<Data> queue;
        private final long windowSizeInMillis;

        public TimeBasedMovingAverage(long windowSizeInMillis) {
            this.queue = new LinkedList<>();
            this.windowSizeInMillis = windowSizeInMillis;
        }

        /**
         * Add a new data point with the specified value and associate it with the current system timestamp.
         *
         * @param value
         */
        public void add(double value) {
            long currentTime = System.currentTimeMillis();
            // Add the new data point
            queue.offer(new Data(value, currentTime));
        }

        public double getAverage() {
            long currentTime = System.currentTimeMillis();
            long oldTimestamp = currentTime - windowSizeInMillis;

            // Remove data points that are outside the time window
            while (!queue.isEmpty() && queue.peek().timestamp < oldTimestamp) {
                queue.poll();
            }

            double sum = 0;
            for (Data data : queue) {
                sum += data.value();
            }

            return sum / queue.size();
        }

        /*
            TimeBasedMovingAverage movingAverage = new TimeBasedMovingAverage(60000); // 60 seconds window
            movingAverage.add(10);  // Adds 10 at current timestamp
            movingAverage.add(20);  // Adds 20 at a later timestamp

            System.out.println(movingAverage.getAverage()); // Calculates average of data points within last 60 seconds

            Thread.sleep(30000); // Wait for 30 seconds

            movingAverage.add(30);  // Adds 30 at a later timestamp

            System.out.println(movingAverage.getAverage()); // Calculates average of data points within the window

        */
    }

    /**
     * Implement a key-value store where entries expire after a certain configured expiration time.
     * The data will be received in increasing timestamps, allowing us to manage the expiration effectively.

     * This question is exactly same as above.
     * Here they are asking to implement a map, so maintain a map in addition to the queue.
     */
    static class TimeBasedKeyValueStore {
        static class Entry {
            String key;
            double value;
            long timestamp;

            Entry(String key, double value, long timestamp) {
                this.key = key;
                this.value = value;
                this.timestamp = timestamp;
            }
        }

        Map<String, Entry> map;
        Queue<Entry> queue;
        long windowSizeInMillis;

        public TimeBasedKeyValueStore(long windowSizeInMillis) {
            map = new HashMap<>();
            queue = new LinkedList<>();
            this.windowSizeInMillis = windowSizeInMillis;
        }

        public void put(String key, double value) {
            long currentTime = System.currentTimeMillis();

            Entry entry = new Entry(key, value, currentTime);
            queue.add(entry);
            map.put(key, entry);
        }

        /**
         * Retrieve a value by key, removing it if expired
         * @param key
         * @return
         */
        public Double get(String key) {
            cleanupOldEntries();
            if (map.containsKey(key)) {
                return map.get(key).value;
            }
            return null;
        }

        /**
         * Get the average of all non-expired values
         * @return
         */
        public double getAverage() {
            cleanupOldEntries();

            double sum = 0;
            for (Entry entry : queue) {
                sum += entry.value;
            }

            return sum / queue.size();
        }

        private void cleanupOldEntries() {
            long currentTime = System.currentTimeMillis();
            long oldTime = currentTime - windowSizeInMillis;

            while (!queue.isEmpty() && queue.peek().timestamp < oldTime) {
                Entry oldEntry = queue.remove();
                map.remove(oldEntry.key);
            }
        }

        /*
          If there is a need to include update and delete methods, do them as follows.
          For update, remove the entry with this key. Create a new entry with the current timestamp and store them.
          For delete, delete the entry from both map and queue.
         */
    }

    /**
     * Implement a moving average class that calculates the average of the last k values from a data stream.
     * Maintain a sliding window of the last k elements and compute the average based on those values.
     */
    static class WindowBasedMovingAverage {
        int windowSize;  // window size in terms of number of elements
        int sum;
        Queue<Integer> queue;

        public WindowBasedMovingAverage(int windowSize) {
            this.windowSize = windowSize;
            this.sum = 0;
            queue = new LinkedList<>();
        }

        public double next(int val) {  // Runtime: O(1)
            queue.add(val);  //Add the new value to the queue and update the sum.
            sum += val;

            if (queue.size() > windowSize) {
                // The queue size can increase by at most one element with each call to next().
                // This is the only method where we add an element and find the average at the same time.
                // So the if condition is enough instead of a while loop.
                int firstVal = queue.remove();
                sum -= firstVal;
            }

            return (double) sum / queue.size();
        }

        /*
            MovingAverage m = new MovingAverage(3);
            System.out.println(m.next(1));   // returns 1.0, as there's only one element.
            System.out.println(m.next(10));  // returns 5.5, as average of [1, 10] is 5.5.
            System.out.println(m.next(3));   // returns 4.666, as average of [1, 10, 3] is 4.666.
            System.out.println(m.next(5));   // returns 6.0, as average of [10, 3, 5] is 6.0.
        */
    }
}
