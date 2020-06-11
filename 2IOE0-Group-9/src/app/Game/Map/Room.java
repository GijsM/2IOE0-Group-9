package app.Game.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.Game.Object.GameObject;
import app.engine.Mesh;
import org.jbox2d.dynamics.World;

import app.Game.Object.StaticGameObject;
import app.Util.Interfaces.ILoadable;
import app.Util.Interfaces.IRenderable;
import app.Util.Interfaces.IUpdateable;

import static java.lang.Math.round;

public class Room implements IUpdateable, IRenderable, ILoadable {

    private GameMap map;
    private List<GameObject> gameobjects = new ArrayList<>();
    Random ran = new Random();
    ArrayList<ArrayList> room;
    //holds the meshes that will be rendered in the render() method
    ArrayList<Mesh> meshes;
    
    /*
    doorwayDirection = 0 when the door is on the north side
    doorwayDirection = 1 when the door is on the east side
    doorwayDirection = 2 when the door is on the south side
    doorwayDirection = 3 when the door is on the west side
     */
    int doorwayDirection;
    int exitDoorwayDirection;
    int[] doorWayLocation = new int[2];
    int[] exitDoorWayLocation = new int[2];

    public Room(GameMap map) {
    	this.room = standardroom(10);
//        this.setMap(map);
    }

    public List<GameObject> getGameobjects() {
        return gameobjects;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }
    
    private ArrayList<ArrayList> standardroom(int width) {
        int doorwayDirection = this.ran.nextInt(5);
        int randomDoorLocation = this.ran.nextInt(8)+1;
        int exitDoorwayDirection = this.ran.nextInt(5);
        int randomExitDoorLocation = this.ran.nextInt(8)+1;
        boolean entranceExitCollision = true;
        
        while(entranceExitCollision){
            if(exitDoorwayDirection == doorwayDirection){
                randomExitDoorLocation = this.ran.nextInt(9)+1;
            } else {
                entranceExitCollision = false;
            }
        }
        
        ArrayList<ArrayList> room = new ArrayList<>();
        for(int i = 0; i < width ; i ++){
            room.add(new ArrayList<Integer>());
        }
        for(int i = 0 ; i < width ; i++){
            for(int j = 0; j < width ; j++){
                if(i == 0 || i == width -1 || j == 0 || j == width-1){
                    room.get(i).add(0);
                } else {
                    room.get(i).add(1);
                }
            }
        }
        if(doorwayDirection == 0){
            room.get(0).set(randomDoorLocation,2);
            this.doorWayLocation[0] = 0;
            this.doorWayLocation[1] = randomDoorLocation;
        } else if(doorwayDirection == 1){
            room.get(randomDoorLocation).set(width-1,2);
            this.doorWayLocation[0] = randomDoorLocation;
            this.doorWayLocation[1] = width-1;
        } else if(doorwayDirection == 2 ){
            room.get(width-1).set(randomDoorLocation,2);
            this.doorWayLocation[0] = width-1;
            this.doorWayLocation[1] = randomDoorLocation;
        } else {
            room.get(randomDoorLocation).set(0,2);
            this.doorWayLocation[0] = randomDoorLocation;
            this.doorWayLocation[1] = 0;
        }
        this.doorwayDirection = doorwayDirection;

        if (exitDoorwayDirection == 0) {
            room.get(0).set(randomExitDoorLocation,3);
            this.exitDoorWayLocation[0] = 0;
            this.exitDoorWayLocation[1] = randomExitDoorLocation;
        } else if (exitDoorwayDirection == 1) {
            room.get(randomExitDoorLocation).set(width-1,3);
            this.exitDoorWayLocation[0] = randomExitDoorLocation;
            this.exitDoorWayLocation[1] = width-1;
        } else if (exitDoorwayDirection == 2) {
            room.get(width-1).set(randomExitDoorLocation,3);
            this.exitDoorWayLocation[0] = width-1;
            this.exitDoorWayLocation[1] = randomExitDoorLocation;
        } else {
            room.get(randomExitDoorLocation).set(0,3);
            this.exitDoorWayLocation[0] = randomExitDoorLocation;
            this.exitDoorWayLocation[1] = 0;
        }
        this.exitDoorwayDirection = exitDoorwayDirection;
        return room;
    }
    
    /*
    Rotates the map clockwise
     */
    public void rotateClockwise(){
        ArrayList<ArrayList> room = this.room;
        ArrayList<ArrayList> newRoom = new ArrayList<>();
        for(int i = 0 ; i < this.room.size() ; i++){
            newRoom.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < this.room.size() ; i++){
            for(int j = 0 ; j < this.room.size() ; j++){
                newRoom.get(i).add(this.room.get(this.room.size()-1-j).get(i));
            }
        }
        this.room =  newRoom;
    }
    
    /*
    Rotates the map counter clockwise
     */
    public void rotateCounterClockwise(){
        ArrayList<ArrayList> room = this.room;
        ArrayList<ArrayList> newRoom = new ArrayList<>();
        for(int i = 0 ; i < this.room.size() ; i++){
            newRoom.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < this.room.size() ; i++){
            for(int j = 0 ; j < this.room.size() ; j++){
                newRoom.get(i).add(this.room.get(j).get(this.room.size()-1-i));
            }
        }
        this.room =  newRoom;
    }
    
    public void print(){
        System.out.println("-------------------------");
        for(int i = 0; i < this.room.size() ; i++){
            for(int j = 0 ; j < this.room.size() ; j++){
                if(j == this.room.size()-1){
                    System.out.println(" "+ this.room.get(i).get(j));
                } else {
                    System.out.print(" "+ this.room.get(i).get(j));
                }
            }
        }
        System.out.println("-------------------------");
    }

    @Override
    public void render() {
        System.out.println("render in room");
    if(this.meshes == null){
        createMeshes();
        }

        for (GameObject gameObject : gameobjects) {
            gameObject.render();
        }
    }

    private void createMeshes() {
        float topX =(float) -1;
        float topY = 1;
        float delta = (float) 2/this.room.size();
        float botX = (float) topX + delta;
        float botY = (float) topY - delta;
        float[] positions;
        float[] colours;
        int[] indices = new int[]{
                0, 1,3,3,2,1
        };
        this.meshes = new ArrayList<>();
        for(float i = 0 ; i < this.room.size(); i++){
            for( float j = 0 ; j < this.room.size(); j++){

                float xOne = topX + j*delta;


                float xTwo = botX + j*delta;
                float yOne = topY - i*delta;

                float yTwo = botY - i*delta;

                int i_int = round(i);

                int j_int = round(j);

                positions = new float[]{

                        // V0
                        -0.5f, 0.5f, 0.5f,
                        // V1
                        -0.5f, -0.5f, 0.5f,
                        // V2
                        0.5f, -0.5f, 0.5f,
                        // V3
                        0.5f, 0.5f, 0.5f
                };


                if((int) this.room.get(i_int).get(j_int) == 0){
                    colours = new float[]{
                            0.0f, 1.0f, 0.0f,
                            0.0f, 0.0f, 1.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 0.0f, 1.0f

                    };



                } else if((int) this.room.get(i_int).get(j_int) == 1) {
                    colours = new float[]{
                            1.0f, 0.0f, 0.0f,
                            0.0f, 0.0f, 1.0f,
                            1.0f, 0.0f, 0.0f,
                            0.0f, 0.0f, 1.0f

                    };


                } else if((int)this.room.get(i_int).get(j_int) == 2){
                    colours = new float[]{
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f

                    };


                } else {
                    colours = new float[]{
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f

                    };
                }
                Mesh mesh = new Mesh(positions, colours, indices);
                this.meshes.add(mesh);
                GameObject obj = new GameObject(mesh);
                System.out.println(xOne + " " + yOne);

                obj.setPosition(xOne,yOne,-1.0f);
                obj.setScale(0.2f);
                gameobjects.add(obj);

            }
        }




    }



    @Override
    public void update() {
        // TODO not sure if this is necessary

    }

    @Override
    public void load(World world) {
        for (GameObject gameObject : gameobjects) {
            gameObject.load(world);
        }

    }

    @Override
    public void unload(World world) {
        for (GameObject gameObject : gameobjects) {
            gameObject.unload(world);
        }
    }
    
}




//OLD RENDER FUNCTION FOR ROOMS

//@Override
//public void render(WindowManager window) {
// for (StaticGameObject staticGameObject : gameobjects) {
//     staticGameObject.render(window);
// }
//
//
// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
// glColor4d(1f, 0f, 0f, 0.3f);
//
// glLoadIdentity();
// float topX =(float) -1;
// float topY = 1;
// float delta = (float) 2/this.room.size();
// float botX = (float) topX + delta;
// float botY = (float) topY - delta;
// for(float i = 0 ; i < this.room.size(); i++){
//     for( float j = 0 ; j < this.room.size(); j++){
//
//         float xOne = topX + j*delta;
//         float xTwo = botX + j*delta;
//         float yOne = topY - i*delta;
//         float yTwo = botY - i*delta;
//         int i_int = round(i);
//         int j_int = round(j);
//         if((int) this.room.get(i_int).get(j_int) == 0){
//
//             glBegin(GL_QUADS);
//
//             glNormal3f(0, 0, 1);
//             glColor3f(0,1,0);
//             glVertex3f(xOne ,yOne, 0); //Draw Back Wall - Top Left
//             glColor3f(0,0,1);
//             glVertex3f(xTwo,yOne, 0); //Draw Back Wall - Top Right
//             glColor3f(0,1,0);
//             glVertex3f(xTwo,yTwo, 0); //Draw Back Wall - Bottom Right
//             glColor3f(0,0,1);
//             glVertex3f(xOne,yTwo, 0); //Draw Back Wall - Bottom Left
//             glEnd();
//         } else if((int) this.room.get(i_int).get(j_int) == 1) {
//             glBegin(GL_QUADS);
//
//             glNormal3f(0, 0, 1);
//             glColor3f(1,0,0);
//             glVertex3f(xOne ,yOne, 0); //Draw Back Wall - Top Left
//             glColor3f(0,0,1);
//             glVertex3f(xTwo,yOne, 0); //Draw Back Wall - Top Right
//             glColor3f(1,0,0);
//             glVertex3f(xTwo,yTwo, 0); //Draw Back Wall - Bottom Right
//             glColor3f(0,0,1);
//             glVertex3f(xOne,yTwo, 0); //Draw Back Wall - Bottom Left
//             glEnd();
//         } else {
//             glBegin(GL_QUADS);
//
//             glNormal3f(0, 0, 1);
//             glColor3f(1,0,0);
//             glVertex3f(xOne ,yOne, 0); //Draw Back Wall - Top Left
//             glColor3f(1,0,0);
//             glVertex3f(xTwo,yOne, 0); //Draw Back Wall - Top Right
//             glColor3f(1,0,0);
//             glVertex3f(xTwo,yTwo, 0); //Draw Back Wall - Bottom Right
//             glColor3f(1,0,0);
//             glVertex3f(xOne,yTwo, 0); //Draw Back Wall - Bottom Left
//             glEnd();
//         }
//     }
// }
//
//}