public class ComplexMatrix {

    private Complex[][] matrix;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new RuntimeException("Размеры матрицы должны быть больше нуля");
        }
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

    public ComplexMatrix subtract(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new RuntimeException("Размеры матриц не совпадают");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Complex c1 = this.getElement(i, j);
                Complex c2 = other.getElement(i, j);
                Complex diff = c1.subtract(c2);
                result.setElement(i, j, diff);
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new RuntimeException("Недопустимые размеры матриц для умножения");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    Complex c1 = this.getElement(i, k);
                    Complex c2 = other.getElement(k, j);
                    Complex product = c1.multiply(c2);
                    sum = sum.add(product);
                }
                result.setElement(i, j, sum);
            }
        }
        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setElement(j, i, this.getElement(i, j));
            }
        }
        return result;
    }

    private ComplexMatrix getMinor(int excludeRow, int excludeCol) {
        ComplexMatrix minor = new ComplexMatrix(this.rows - 1, this.cols - 1);
        int r = 0;
        for (int i = 0; i < this.rows; i++) {
            if (i == excludeRow) continue;
            int c = 0;
            for (int j = 0; j < this.cols; j++) {
                if (j == excludeCol) continue;
                minor.setElement(r, c, this.getElement(i, j));
                c++;
            }
            r++;
        }
        return minor;
    }

    public Complex determinant() {
        if (this.rows != this.cols) {
            throw new RuntimeException("Определитель можно вычислить только для квадратной матрицы");
        }
        if (this.rows == 1) {
            return this.getElement(0, 0);
        }
        if (this.rows == 2) {
            Complex mainDiag = this.getElement(0, 0).multiply(this.getElement(1, 1));
            Complex antiDiag = this.getElement(0, 1).multiply(this.getElement(1, 0));
            return mainDiag.subtract(antiDiag);
        }
        Complex det = new Complex(0, 0);
        for (int j = 0; j < this.cols; j++) {
            Complex element = this.getElement(0, j);
            Complex minorDet = this.getMinor(0, j).determinant();
            Complex term = element.multiply(minorDet);
            if (j % 2 != 0) {
                term = term.multiply(new Complex(-1, 0));
            }
            det = det.add(term);
        }
        return det;
    }

    public ComplexMatrix inverse() {
        if (this.rows != this.cols) {
            throw new RuntimeException("Обратная матрица существует только для квадратных матриц");
        }
        Complex det = this.determinant();
        if (det.getReal() == 0 && det.getImag() == 0) {
            throw new RuntimeException("Определитель равен нулю, обратная матрица не существует");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);
        if (this.rows == 1) {
            Complex one = new Complex(1, 0);
            result.setElement(0, 0, one.divide(this.getElement(0, 0)));
            return result;
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Complex minorDet = this.getMinor(i, j).determinant();
                if ((i + j) % 2 != 0) {
                    minorDet = minorDet.multiply(new Complex(-1, 0));
                }
                Complex elementValue = minorDet.divide(det);
                result.setElement(j, i, elementValue);
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        ComplexMatrix inverseOther = other.inverse();
        return this.multiply(inverseOther);
    }
}


