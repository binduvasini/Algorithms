package com.Algorithms;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class Playground {

static int count = 0;

    public static void main(String[] args) {
        StringBuilder b = new StringBuilder();

    }

    @Test
    public void testCompressPositive() {
        String str = "stripe.com/payments/checkout/customer.john.doe";
        String actual = compress(str);

        String expected = "s4e/c1m/p6s/c6t/c6r/j2n/d1e/";
        assertEquals(expected, actual);
    }

    @Test
    public void testCompressBlankString() {
        String str = "   ";

        assertThrows(IllegalArgumentException.class, () -> compress(str));
    }

    private String compress(String str) {
        if (str.isBlank()) {
            throw new IllegalArgumentException("Can't be blank");
        }

        StringBuilder builder = new StringBuilder();

        String[] array = str.split("/");
        // System.out.println(Arrays.toString(array));

        for (String s : array) {
            String[] arr = s.split("\\.");
            // System.out.println(Arrays.toString(arr));
            for (String a : arr) {
                String tmp = a;
                tmp = tmp.substring(1, a.length() - 1);
                builder.append(a.charAt(0)).append(tmp.length()).append(a.charAt(a.length() - 1)).append('/');
            }
        }

        return builder.toString();
    }


    @Test
    public void testPenaltyPositive() {
        String log = "Y Y N Y";
        int actual = penalty(log, 4);
        assertEquals(1, actual);
    }

    @Test
    public void testPenaltyClosingTime0() {
        String log = "Y Y N Y";
        int actual = penalty(log, 0);
        assertEquals(3, actual);
    }

    @Test
    public void testPenaltyClosingTime3() {
        String log = "Y Y N Y";
        int actual = penalty(log, 3);
        assertEquals(1, actual);
    }

    public int penalty(String log, int closing) {
        int penalty = 0;
        for (int i = 0; i < closing; i++) {
            if (log.charAt(i) == 'N') {
                penalty += 1;
            }
        }
        for (int i = closing; i < log.length(); i++) {
            if (log.charAt(i) == 'Y') {
                penalty += 1;
            }
        }
        return penalty;
    }

    @Test
    public void testClosingTime2() {
        String log = "Y Y N Y";
        int actual = bestClosingTime(log);

        assertEquals(3, actual);
    }

    public int bestClosingTime(String log) {
        int bestClosing = 0, minPenalty = Integer.MAX_VALUE;
        int n = log.split(" ").length;
        for (int i = 0; i <= n; i++) {
            int penalty = penalty(log, i);
            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestClosing = i;
            }
        }
        return bestClosing;
    }

    public static List<Integer> bestClosingTimes(String log) {
        List<Integer> closing = new LinkedList<>();

        Deque<String> stack = new ArrayDeque<>();

        String[] str = log.split(" ");
        for (int i = str.length - 1; i >= 0; i--) {
            if (str[i].equals("END")) {
                stack.push(str[i]);
            }


        }


        return closing;
    }
}