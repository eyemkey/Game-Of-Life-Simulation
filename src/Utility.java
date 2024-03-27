public class Utility {
    public static int[][] copyMatrix(int[][] matrix){
        int[][] newMatrix = new int[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    public static boolean contains(int[] array, int elem){
        for(int e: array){
            if(e == elem) return true;
        }
        return false;
    }


    public static void printMatrix(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
