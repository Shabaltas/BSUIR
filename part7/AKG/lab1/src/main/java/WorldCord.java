import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldCord {

    private RealMatrix translateMatrix = MatrixUtils.createRealMatrix(new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
    private RealMatrix scaleMatrix = MatrixUtils.createRealDiagonalMatrix(new double[]{0, 0, 0, 1});
    private RealMatrix turnXMatrix = MatrixUtils.createRealMatrix(new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
    private RealMatrix turnYMatrix = MatrixUtils.createRealMatrix(new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
    private RealMatrix turnZMatrix = MatrixUtils.createRealMatrix(new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
    private RealMatrix viewport, view, projection, all;

    private List<Vertice> initialVertices;
    private List<Vertice> transformedVertices;

    private double width, height;

    public WorldCord(List<Vertice> vertices, double viewWidth, double viewHeight) {
        initialVertices = vertices;
        transformedVertices = new ArrayList<>(vertices.size());
        vertices.forEach(vertice -> {
            transformedVertices.add(new Vertice(vertice.getX(), vertice.getY(), vertice.getZ(), vertice.getW()));
        });
        width = viewWidth;
        height = viewHeight;
    }

    public List<Vertice> getInitialVertices() {
        return initialVertices;
    }

    public List<Vertice> getTransformedVertices() {
        return transformedVertices;
    }

    public RealMatrix getProjection() {
        return projection;
    }

    public RealMatrix getViewport() {
        return viewport;
    }

    public RealMatrix getView() {
        return view;
    }

    public WorldCord translateVertices(double[] translation) {
        translateMatrix.setColumn(3, new double[]{translation[0], translation[1], translation[2], 1});
        all = all.multiply(translateMatrix);
        //multiplyMatrix(translateMatrix);
        return this;
    }

    public WorldCord scaleVertices(double[] scale) {
        for (int i = 0; i < scale.length; i++) {
            scaleMatrix.setEntry(i, i, scale[i]);
        }
        all = all.multiply(scaleMatrix);
        //multiplyMatrix(scaleMatrix);
        return this;
    }

    public WorldCord turnXVertices(double xAngleRadian) {
        turnXMatrix.setEntry(1, 1, Math.cos(xAngleRadian));
        turnXMatrix.setEntry(1, 2, -Math.sin(xAngleRadian));
        turnXMatrix.setEntry(2, 2, Math.cos(xAngleRadian));
        turnXMatrix.setEntry(2, 1, Math.sin(xAngleRadian));
        all = all.multiply(turnXMatrix);
        //multiplyMatrix(turnXMatrix);
        return this;
    }

    public WorldCord turnYVertices(double yAngleRadian) {
        turnYMatrix.setEntry(0,0, Math.cos(yAngleRadian));
        turnYMatrix.setEntry(0, 2, Math.sin(yAngleRadian));
        turnYMatrix.setEntry(2, 0, -Math.sin(yAngleRadian));
        turnYMatrix.setEntry(2, 2, Math.cos(yAngleRadian));
        all = all.multiply(turnYMatrix);
        //multiplyMatrix(turnYMatrix);
        return this;
    }

    public WorldCord turnZVertices(double zAngleRadian) {
        turnZMatrix.setEntry(0,0, Math.cos(zAngleRadian));
        turnZMatrix.setEntry(0, 1, -Math.sin(zAngleRadian));
        turnZMatrix.setEntry(1, 0, Math.sin(zAngleRadian));
        turnZMatrix.setEntry(1, 1, Math.cos(zAngleRadian));
        all = all.multiply(turnZMatrix);
        //multiplyMatrix(turnZMatrix);
        return this;
    }

    public WorldCord createViewPort(double xMin, double yMin) {
        viewport = MatrixUtils.createRealMatrix(new double[][]{
                {width / 2, 0, 0, xMin + width / 2},
                {0, -height / 2, 0, yMin + height / 2},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        return this;
    }

    public WorldCord createView(Vector3D eye, Vector3D target, Vector3D up) {
        Vector3D ZAxis = eye.subtract(target).normalize();
        Vector3D XAxis = up.crossProduct(ZAxis).normalize();
        Vector3D YAxis = up;
        view = MatrixUtils.createRealMatrix(new double[][]{
                {XAxis.getX(), XAxis.getY(), XAxis.getZ(), -scalarMultiply(XAxis, eye)},
                {YAxis.getX(), YAxis.getY(), YAxis.getZ(), -scalarMultiply(YAxis, eye)},
                {ZAxis.getX(), ZAxis.getY(), ZAxis.getZ(), -scalarMultiply(ZAxis, eye)},
                {0, 0, 0, 1}
        });
        return this;
    }

    public WorldCord createProjection(double zNear, double zfar) {
        projection = MatrixUtils.createRealMatrix(new double[][]{
                {2 * zNear/ width, 0, 0, 0},
                {0, 2 * zNear/ height, 0, 0},
                {0, 0, zfar / (zNear - zfar), zNear * zfar / (zNear - zfar)},
                {0, 0, -1, 0}
        });
        return this;
    }

    public WorldCord allChangesMatrix() {
        all = viewport.multiply(projection).multiply(view);
        return this;
    }

    public WorldCord lastTransform() {
        transformedVertices.forEach(vertice -> {
             double[] newCord = all.operate(vertice.getVector());
             vertice.setX(newCord[0]/newCord[3]);
             vertice.setY(newCord[1]/newCord[3]);
             vertice.setZ(newCord[2]/newCord[3]);
             vertice.setW(newCord[3]/newCord[3]);
        });
        return this;
    }
    private double scalarMultiply(Vector3D v1, Vector3D v2) {
        return v1.getX() * v2.getX()
                + v1.getY() * v2.getY()
                + v1.getZ() * v2.getZ();
    }

    private void multiplyMatrix(RealMatrix toMatrix) {
        transformedVertices.forEach( vertice -> {
            double[] mult = toMatrix.operate(vertice.getVector());
            vertice.setX(mult[0]);
            vertice.setY(mult[0]);
            vertice.setZ(mult[2]);
            vertice.setW(mult[3]);
        });
    }

    public static void main(String[] args) {
        double[] v = new double[]{0.122606, 0.631867, 0.0339156, 1};
        double zNear = 0.1, zfar = 2000;
        int width = 900, height = 400;
        RealMatrix projection = MatrixUtils.createRealMatrix(new double[][]{
                {2 * zNear/ width, 0, 0, 0},
                {0, 2 * zNear/ height, 0, 0},
                {0, 0, zfar / (zNear - zfar), zNear * zfar / (zNear - zfar)},
                {0, 0, -1, 0}
        });
        v = projection.operate(v);
        Arrays.stream(v).forEachOrdered(System.out::println);
    }
}
