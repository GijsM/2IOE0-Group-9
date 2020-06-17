/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.Util.Vec2;
import app.Util.Vector3f;

/**
 *
 * @author iris https://www.youtube.com/watch?v=YKFYtekgnP8
 */
public class ObjectLoader {

    public static RawModel loadObjModel(String fileName, Loader loader) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(new File("2IOE0-Group-9\\res\\" + fileName + ".obj"));
        } catch (FileNotFoundException ex) {
            System.err.println("File not not found, cannot load");
            Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vec2> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] vertexArray = null;
        float[] normalArray = null;
        float[] textureArray = null;
        int[] indexArray = null;

        try {
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");

                //parse the .obj file in text format and add to correct List
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(
                            currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));

                    vertices.add(vertex);

                } else if (line.startsWith("vt ")) {
                    Vec2 texture = new Vec2(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));

                    textures.add(texture);

                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(
                            currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));

                    normals.add(normal);

                } else if (line.startsWith("f ")) {
                    textureArray = new float[vertices.size() * 2];
                    normalArray = new float[vertices.size() * 3];
                    break;
                }
            }

            //loop over the faces of the object
            while (line != null) {
                if (!line.startsWith("f")) {
                    line = reader.readLine(); //do nothing, read the next line
                    continue; //do nothing if the line does not describe a face
                }

                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                
                //process the vertices and put data in the normal/texture arrays
                processVertex(vertex1, indices, textures, normals, textureArray,
                        normalArray);
                processVertex(vertex2, indices, textures, normals, textureArray,
                        normalArray);
                processVertex(vertex3, indices, textures, normals, textureArray,
                        normalArray);
                
                line = reader.readLine(); //after processing, read the next line
            }
            
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        vertexArray = new float[vertices.size() * 3];
        indexArray = new int[indices.size()];
        
        //add vertices in the list to the vertex array (as floats)
        int vertexPointer = 0;
        for(Vector3f vertex : vertices) {
            vertexArray[vertexPointer++] = vertex.getX();
            vertexArray[vertexPointer++] = vertex.getY();
            vertexArray[vertexPointer++] = vertex.getZ();
        }
        
        //add indices in the list to the index array (as floats)
        for(int i = 0; i < indices.size(); i++) {
            indexArray[i] = indices.get(i);
        }
        
        return loader.loadToVAO(vertexArray, textureArray, indexArray);

    }
    
    private static void processVertex(String[] vertexData,
            List<Integer> indices, List<Vec2> textures,
            List<Vector3f> normals, float[] textureArray, float[] normalArray) {
        
        int currentVertexIndex = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexIndex);
        
        //add textures to the array of textures
        Vec2 currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexIndex * 2] = currentTex.getX();
        textureArray[currentVertexIndex * 2 + 1] = 1 - currentTex.getY();
        
        //add normals to the array of normals
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalArray[currentVertexIndex * 3] = currentNorm.getX();
        normalArray[currentVertexIndex * 3 + 1] = currentNorm.getY();
        normalArray[currentVertexIndex * 3 + 2] = currentNorm.getZ();
        
    }

}
