package spaces;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class ProjectionSpace {
    private RealMatrix projection;

    public ProjectionSpace(double width, double height, double zNear, double zfar) {
        projection = MatrixUtils.createRealMatrix(new double[][]{
                {2 * zNear/ width, 0, 0, 0},
                {0, 2 * zNear/ height, 0, 0},
                {0, 0, zfar / (zNear - zfar), zNear * zfar / (zNear - zfar)},
                {0, 0, -1, 0}
        });
    }

    public RealMatrix getProjection() {
        return projection;
    }
}
