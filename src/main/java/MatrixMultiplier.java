import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Alexandr Smirnov
 *
 * Сlass for multiplying two matrices using multithreading and without multithreading
 */
public class MatrixMultiplier {

    private int threadsCount;
    private int[][] matrixOne;
    private int[][] matrixTwo;
    private volatile int[][] matrixFinal;

    /**
     *
     * @param matrixOne - the first matrix to be multiplied
     * @param matrixTwo - the second matrix to be multiplied
     */
    public MatrixMultiplier(int[][] matrixOne, int[][] matrixTwo) {
        this.matrixOne = matrixOne;
        this.matrixTwo = matrixTwo;
        this.matrixFinal = new int[matrixOne.length][matrixTwo[0].length];

        threadsCount = Runtime.getRuntime().availableProcessors();
    }

    /**
     * Multiply matrices without multithreading
     *
     * @return multiplied result matrix
     */
    public int[][] multiplyMatricesClassic() {

        if (matrixOne[0].length != matrixTwo.length)
            return null;

        int[][] mResult = new int[matrixOne.length][matrixTwo[0].length];

        for (int i = 0; i < matrixOne.length; i++) {
            for (int j = 0; j < matrixTwo[0].length; j++) {
                for (int k = 0; k < matrixOne[0].length; k++) {
                    mResult[i][j] += matrixOne[i][k] * matrixTwo[k][j];
                }
            }
        }
        return mResult;
    }

    /**
     * Multiply matrices with multithreading
     *
     * @return multiplied result matrix
     */
    public int[][] multiplyMatricesMultiThreading() {

        if (matrixOne[0].length != matrixTwo.length)
            return null;

        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < matrixOne.length; i++) {
            service.execute(new RowsMultiplier(i));
        }
        service.shutdown();
        return matrixFinal;
    }

    private class RowsMultiplier implements Runnable {
        private int multiplicationIndex;

        public RowsMultiplier(int multiplicationIndex) {
            this.multiplicationIndex = multiplicationIndex;
        }

        @Override
        public void run() {
            for (int j = 0; j < matrixOne.length; j++) {
                for (int k = 0; k < matrixTwo.length; k++) {
                    matrixFinal[multiplicationIndex][j] += matrixOne[multiplicationIndex][k] * matrixTwo[k][j];
                }
            }
        }
    }
}
