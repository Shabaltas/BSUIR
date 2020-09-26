package spaces;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class ViewportSpace {
    private RealMatrix viewport;

    public ViewportSpace(double width, double height, double xMin, double yMin) {
        viewport = MatrixUtils.createRealMatrix(new double[][]{
                {width / 2, 0, 0, xMin + width / 2},
                {0, -height / 2, 0, yMin + height / 2},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public RealMatrix getViewport() {
        return viewport;
    }
}
