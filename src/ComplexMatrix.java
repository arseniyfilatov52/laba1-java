public class ComplexMatrix {

    private Complex[][] matrix;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Complex(0, 0);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Complex getElement(int row, int col) {
        return matrix[row][col];
    }

    public void setElement(int row, int col, Complex element) {
        matrix[row][col] = element;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new RuntimeException("Размеры матриц не совпадают");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Complex c1 = this.getElement(i, j);
                Complex c2 = other.getElement(i, j);
                Complex sum = c1.add(c2);
                result.setElement(i, j, sum);
            }
        }
        return result;
    }
}


