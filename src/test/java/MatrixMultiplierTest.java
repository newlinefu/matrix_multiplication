import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMultiplierTest {

    @Test
    void multiplyMatricesMultiThreadingTest() {
        int[][][] firstMatrices = {
                {
                        {1, 2},
                        {3, 4}
                },
                {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                }
        };
        int[][][] secondMatrices = {
                {
                        {5, 6},
                        {7, 8}
                },
                {
                        {10, 11, 12},
                        {13, 14, 15},
                        {16, 17, 18}
                }
        };
        int[][][] finalMatrices = {
                {
                        {19, 22},
                        {43, 50}
                },
                {
                        {84, 90, 96},
                        {201, 216, 231},
                        {318, 342, 366}
                }
        };

        for(int i = 0; i < firstMatrices.length; i++) {
            MatrixMultiplier mm = new MatrixMultiplier(firstMatrices[i], secondMatrices[i]);
            int[][] result = mm.multiplyMatricesMultiThreading();
            Assert.assertArrayEquals(result, finalMatrices[i]);
        }
    }
}