package spaces;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class ViewSpace {
    private RealMatrix view;

    public ViewSpace(Vector3D eye, Vector3D target, Vector3D up) {
        Vector3D ZAxis = eye.subtract(target).normalize();
        Vector3D XAxis = up.crossProduct(ZAxis).normalize();
        Vector3D YAxis = up;
        view = MatrixUtils.createRealMatrix(new double[][]{
                {XAxis.getX(), XAxis.getY(), XAxis.getZ(), -scalarMultiply(XAxis, eye)},
                {YAxis.getX(), YAxis.getY(), YAxis.getZ(), -scalarMultiply(YAxis, eye)},
                {ZAxis.getX(), ZAxis.getY(), ZAxis.getZ(), -scalarMultiply(ZAxis, eye)},
                {0, 0, 0, 1}
        });
    }

    private double scalarMultiply(Vector3D v1, Vector3D v2) {
        return v1.getX() * v2.getX()
                + v1.getY() * v2.getY()
                + v1.getZ() * v2.getZ();
    }

    public RealMatrix getView() {
        return view;
    }
}
