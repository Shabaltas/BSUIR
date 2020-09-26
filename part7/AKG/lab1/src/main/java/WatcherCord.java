import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

public class WatcherCord {
    private List<Vertice> wordCordVertices;
    private List<Vertice> transformedVertices;

    public WatcherCord(List<Vertice> vertices) {
        wordCordVertices = vertices;
        transformedVertices = new ArrayList<>(vertices.size());
        vertices.forEach(vertice -> {
            transformedVertices.add(new Vertice(vertice.getX(), vertice.getY(), vertice.getZ(), vertice.getW()));
        });
    }

    public List<Vertice> getWordCordVertices() {
        return wordCordVertices;
    }

    public List<Vertice> getTransformedVertices() {
        return transformedVertices;
    }

    /**
     * позиция камеры в мировом пространстве (𝑒𝑦𝑒);
     * позиция цели, на которую направлена камера (𝑡𝑎𝑟𝑔𝑒𝑡);
     * вектор, направленный вертикально вверх с точки зрения камеры (𝑢𝑝).
     */
    //TODO вынестив класс утилит
    public WatcherCord transformVertices(Vector3D eye, Vector3D target, Vector3D up) {
        RealMatrix lookAtMatrix = getLookAtMatrix(eye, target, up);
        transformedVertices.forEach( vertice -> {
            RealVector mult = lookAtMatrix.preMultiply(MatrixUtils.createRealVector(vertice.getVector()));
            vertice.setX(mult.getEntry(0));
            vertice.setY(mult.getEntry(1));
            vertice.setZ(mult.getEntry(2));
            vertice.setW(mult.getEntry(3));
        });

        return this;
    }

    private RealMatrix getLookAtMatrix(Vector3D eye, Vector3D target, Vector3D up) {
        Vector3D ZAxis = eye.subtract(target).normalize();
        Vector3D XAxis = up.crossProduct(ZAxis).normalize();
        Vector3D YAxis = up;
        return MatrixUtils.createRealMatrix(new double[][]{
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
}
