package lineerregression;

import math.Matrix;

public class MultiLineer {
	
	
	private Matrix X;
    private Matrix y;

    public MultiLineer(Matrix X, Matrix y) {
        if (X.getRows() != y.getRows()) {
            throw new IllegalArgumentException("X and y must have the same number of rows");
        }
        this.X = X;
        this.y = y;
    }

    public Matrix fit() {
        // Add column of ones for the intercept term
        Matrix ones = new Matrix(X.getRows(), 1);
        ones.fill(1.0);
        Matrix Xnew = ones.concatColumns(X);

        // Compute the parameters
        Matrix XTXinv = Xnew.transpose().times(Xnew).inverse();
        Matrix XTy = Xnew.transpose().times(y);
        return XTXinv.times(XTy);
    }

	
	
}
