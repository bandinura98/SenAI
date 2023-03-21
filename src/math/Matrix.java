package math;

public class Matrix {

    private final int rows;
    private final int cols;
    private final double[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public void set(int i, int j, double value) {
        data[i][j] = value;
    }

    public double get(int i, int j) {
        return data[i][j];
    }

    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, data[i][j]);
            }
        }
        return result;
    }

    public Matrix times(Matrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible for multiplication");
        }
        Matrix result = new Matrix(rows, other.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += data[i][k] * other.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public Matrix solve(Matrix other) {
        if (rows != cols || rows != other.rows) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible for solving linear system");
        }
        Matrix result = new Matrix(rows, other.cols);
        double[][] a = new double[rows][rows];
        double[][] b = new double[rows][other.cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                a[i][j] = data[i][j];
            }
            for (int j = 0; j < other.cols; j++) {
                b[i][j] = other.get(i, j);
            }
        }
        for (int k = 0; k < rows; k++) {
            for (int i = k + 1; i < rows; i++) {
                double factor = a[i][k] / a[k][k];
                for (int j = k + 1; j < rows; j++) {
                    a[i][j] -= factor * a[k][j];
                }
                for (int j = 0; j < other.cols; j++) {
                    b[i][j] -= factor * b[k][j];
                }
            }
        }
        for (int k = rows - 1; k >= 0; k--) {
            for (int j = 0; j < other.cols; j++) {
                double sum = b[k][j];
                for (int i = k + 1; i < rows; i++) {
                    sum -= a[k][i] * result.get(i, j);
                }
                result.set(k, j, sum / a[k][k]);
            }
        }
        return result;
    }
    
    public int getRows() {
        return rows;
    }
    public void fill(double value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = value;
            }
        }
    }
    public int getCols() {
        return cols;
    }
    public Matrix concatColumns(Matrix other) {
	    if (rows != other.getRows()) {
	        throw new IllegalArgumentException("Matrices must have the same number of rows to concatenate columns");
	    }
	    Matrix result = new Matrix(rows, cols + other.getCols());
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            result.set(i, j, data[i][j]);
	        }
	        for (int j = 0; j < other.getCols(); j++) {
	            result.set(i, cols + j, other.get(i, j));
	        }
	    }
	    return result;
	}
    
    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix is not square and does not have an inverse.");
        }

        Matrix aug = new Matrix(rows, cols * 2);
        aug.fill(0);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                aug.set(i, j, data[i][j]);
            }
            aug.set(i, cols + i, 1);
        }

        // Perform Gaussian elimination with partial pivoting
        for (int i = 0; i < rows; i++) {
            int maxRow = i;
            double maxVal = Math.abs(aug.get(i, i));
            for (int j = i + 1; j < rows; j++) {
                double val = Math.abs(aug.get(j, i));
                if (val > maxVal) {
                    maxRow = j;
                    maxVal = val;
                }
            }

            if (maxRow != i) {
                for (int j = i; j < cols * 2; j++) {
                    double temp = aug.get(i, j);
                    aug.set(i, j, aug.get(maxRow, j));
                    aug.set(maxRow, j, temp);
                }
            }

            double pivot = aug.get(i, i);
            if (pivot == 0) {
                throw new ArithmeticException("Matrix is singular and does not have an inverse.");
            }

            for (int j = i + 1; j < cols * 2; j++) {
                aug.set(i, j, aug.get(i, j) / pivot);
            }
            for (int j = 0; j < rows; j++) {
                if (j != i) {
                    double factor = aug.get(j, i);
                    for (int k = i + 1; k < cols * 2; k++) {
                        aug.set(j, k, aug.get(j, k) - factor * aug.get(i, k));
                    }
                    aug.set(j, i, 0);
                }
            }
        }

        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, aug.get(i, cols + j));
            }
        }
        return result;
    }
}