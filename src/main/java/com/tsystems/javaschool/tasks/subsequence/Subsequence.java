package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) throw new IllegalArgumentException();
        for (int iy = 0, ix =0; ix < x.size(); iy++) {
            if (x.size()-ix > y.size()-iy) return false;
            if (x.get(ix).equals(y.get(iy))) {
                ix++;
            }
        }
        return true;
    }
}
