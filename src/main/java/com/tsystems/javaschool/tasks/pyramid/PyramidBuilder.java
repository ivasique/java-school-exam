package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // Checking if the pyramid can be built with given input. Also calculating the height of the pyramid.
        int pyrHeight = 0;
        for (int vol = inputNumbers.size(); vol > 0; vol -= pyrHeight) {
            if (vol < pyrHeight) throw new CannotBuildPyramidException();
            pyrHeight++;
        }

        int pyrWidth = pyrHeight + (pyrHeight - 1); // The number of elements on each level is equal to
                                                    // the level's sequence number (top-to-bottom).
                                                    // The elements on each level are separated by zeros.
        int[][] pyramid = new int[pyrHeight][pyrWidth];

        List<Integer> inputCopy = new ArrayList<>(inputNumbers);  // This allows to keep the original input list unchanged.
        try {
            Collections.sort(inputCopy);
        } catch (NullPointerException e) {
            throw new CannotBuildPyramidException();
        }

        int center = pyrWidth / 2 ;
        int counter = 0;
        for ( int i = 0; i < pyrHeight; i++) {
            int curPosInRow = center - i;
            for (int j = 0; j <= i; j++){
                pyramid[i][curPosInRow] = inputCopy.get(counter);
                counter++;
                curPosInRow += 2;
            }
        }
        return pyramid;
    }
}
