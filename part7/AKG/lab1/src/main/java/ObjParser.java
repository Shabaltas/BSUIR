
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import spaces.ProjectionSpace;
import spaces.ViewSpace;
import spaces.ViewportSpace;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ObjParser {
    private List<Vertice> vertices = new ArrayList<Vertice>();
    public void loadObjFile(String path, String filename) throws IOException {
        File objFile = new File(path, filename);
        BufferedReader reader = new BufferedReader(new FileReader(objFile));
        String line;
        //TODO POLYGONS
        while((line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            String command = tokenizer.nextToken();
            if (command.equals("v")) {
                vertices.add(new Vertice(
                        Float.parseFloat(tokenizer.nextToken()),
                        Float.parseFloat(tokenizer.nextToken()),
                        Float.parseFloat(tokenizer.nextToken())));
            }
        }
    }
    public static void  main(String[] args) throws IOException {
        ObjParser op = new ObjParser();
        op.loadObjFile("C:\\Users\\Asus\\BSUIRlabs\\part7\\AKG\\lab1", "ex.txt");
        // call in the reverse order !!!!
        //TODO need to turnZ on 60 -> turnY on 45 -> turnX on 30 -> scale -> transform
        WorldCord wct = new WorldCord(op.vertices, 900, 400);
        wct.createProjection(0.1, 2000.0)
                .createView(new Vector3D(0, 0, 0), new Vector3D(10, 10, 10), new Vector3D(0, 0, 10))
                .createViewPort(0, 0)
                .allChangesMatrix();
        //TODO when changes comes
        wct
                .translateVertices(new double[]{10, 15, 20})
                .scaleVertices(new double[]{4, 2, 3})
                .turnXVertices(Math.toRadians(30))
                .turnYVertices(Math.toRadians(45))
                .turnZVertices(Math.toRadians(60))
                .lastTransform()
                .getTransformedVertices();

    }
}
