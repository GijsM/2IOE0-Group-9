package app.Game.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.Game.AI.AStar;
import app.Game.Object.GameObject;
import app.engine.Mesh;
import app.graphics.*;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import app.Game.Object.Tree;
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
    private RawModel treeModel;
    private Mesh treeMesh;


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
    Loader loader = new Loader();

    public Room(GameMap map) {
    	this.room = standardroom(20);

    	this.treeModel = ObjectLoader.loadObjModel("Tree",loader);
        loader.loadTexture("tree2");      
  
//        for (int p = 0 ; p < treeModel.colors.length ; p++){
//            treeModel.colors[p++] = 0.0f;
//            treeModel.colors[p] = 0.5f;
//        }

        this.treeMesh = new Mesh(treeModel.positions,treeModel.colors, treeModel.indices);

        createGameObjects();
        this.setMap(map);
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
                exitDoorwayDirection = this.ran.nextInt(5);
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
        int random = this.ran.nextInt(10) + 5;
        int chunkSize = Math.round(width/2f);
        int chunkPos = this.ran.nextInt((width - 2) * (width - 2 - chunkSize));
        int counter = 0;

        for(int i = 0 ; i < width ; i++){
            for(int j = 0; j < width ; j++){
                if((int) room.get(i).get(j) == 1){
                    counter++;
                    if(counter >= chunkPos && ((i >= 1 && i <= width - chunkSize - 1) && (j >= 1 && j <= width - chunkSize - 1))){
                        int gap = ran.nextInt(chunkSize / 3) + 2;
                        int gapCounter = 0;
                        for(int row = i; row < i + chunkSize; row++) {
                            for (int col = j; col < j + chunkSize; col++) {
                                if(row == i || col == j || row == i + chunkSize - 1 || col == j + chunkSize - 1){
                                    gapCounter++;
                                    if(!(row + 1 >= width || col + 1 >= width || (int) room.get(row).get(col) == 5 || (int) room.get(row).get(col) == -4)){
                                        if(gapCounter < gap){
                                            room.get(row).set(col, 4);
                                            AStar stoneCheck = new AStar(ToInt(room), doorWayLocation[1], doorWayLocation[0], false, this);
                                            if (stoneCheck.findPathTo(exitDoorWayLocation[1], exitDoorWayLocation[0]) == null) {
                                                room.get(row).set(col, 1);
                                            }
                                        } else {
                                            gap += ran.nextInt(chunkSize / 3) + 2;
                                        }
                                    }
                                }
                            }
                        }
                        chunkPos = 10000;
                    } else if(counter >= random){
                        random += this.ran.nextInt(5) + 5;
                        room.get(i).set(j, 4);
                        AStar stoneCheck = new AStar(ToInt(room), doorWayLocation[1], doorWayLocation[0], false, this);
                        if (stoneCheck.findPathTo(exitDoorWayLocation[1], exitDoorWayLocation[0]) == null) {
                            room.get(i).set(j, 1);
                        }
                    }
                }
            }
        }

        int item = this.ran.nextInt(round((width - 2) * (width - 2) / 3)) + (width - 2) * (width - 2) / 3;
        counter = 0;
        boolean done = false;
        int range = round(width / 3);
        int maxRange = round(2 * width / 3);
        for(int i = 0 ; i < width ; i++){
            for(int j = 0; j < width ; j++){
                if((int) room.get(i).get(j) == 1){
                    counter++;
                    if(counter >= item){
                        if(i >= range && j >= range && i <= maxRange && j <= maxRange){
                            AStar stoneCheck = new AStar(ToInt(room), doorWayLocation[1], doorWayLocation[0], false, this);
                            if (!(stoneCheck.findPathTo(j, i) == null)) {
                                room.get(i).set(j, -6);
                                done = true;
                                break;
                            }
                        }
                    }
                } else if((int) room.get(i).get(j) == 4){
                    counter++;
                }
            }
            if(done){
                break;
            }
        }
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
                    System.out.println(" "+ this.room.get(j).get(i));
                } else {
                    System.out.print(" "+ this.room.get(j).get(i));
                }
            }
        }
        System.out.println("-------------------------");
    }

    public int[][] ToInt(ArrayList<ArrayList> room){
        int[][] aStarRoom = new int[room.size()][room.size()];

        for(int i = 0; i < room.size() ; i++){
            for(int j = 0 ; j < room.size() ; j++){
                int currentTile = (int) room.get(i).get(j);
                // If a tile is blocked put -2 in the array for A*
                if(currentTile == 0){
                    aStarRoom[i][j] = 0;
                    // If a tile contains a rock put -3 in the array
                } else if(currentTile == 4){
                    aStarRoom[i][j] = 4;
                    // If a tile is traversable put a 0 in the array
                } else if(currentTile == 5){
                    aStarRoom[i][j] = 5;
                    // If a tile is traversable put a 0 in the array
                } else if(currentTile == -4){
                    aStarRoom[i][j] = 3;
                } else if(currentTile == -6){
                    aStarRoom[i][j] = 2;
                }else{
                    aStarRoom[i][j] = 9;
                }
            }
        }
        return aStarRoom;
    }

    @Override
    public void render() {
        System.out.println("render in room");
        for (GameObject gameObject : gameobjects) {
            gameObject.render();
        }
    }

    private void createGameObjects() {
        float topX =(float) -1;
        float topY = 1;
        float delta = (float) 2/this.room.size();
        float botX = (float) topX + delta;
        float botY = (float) topY - delta;
        float[] positions;
        float[] colours;
        int[] indices = new int[]{
                0, 1,3,3,2,1
//        		2,1,3,3,1,0
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
                        -0.5f, 0.5f, -0.5f,
                        // V2
                        0.5f, 0.5f, -0.5f,
                        // V3
                        0.5f, 0.5f, 0.5f
                };


                if((int) this.room.get(i_int).get(j_int) == 0){
                    colours = new float[]{
                    		0.188f, 0.462f, 0.098f,
                    		0.188f, 0.462f, 0.098f,
                    		0.188f, 0.462f, 0.098f,
                    		0.188f, 0.462f, 0.098f,
                    };



                } else if((int) this.room.get(i_int).get(j_int) == 1) {
                    colours = new float[]{
                            0.419f, 0.301f, 0.098f,
                            0.419f, 0.301f, 0.098f,
                            0.419f, 0.301f, 0.098f,
                            0.419f, 0.301f, 0.098f

                    };


                } else if((int)this.room.get(i_int).get(j_int) == 2){
                    colours = new float[]{
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f

                    };


                } else if((int)this.room.get(i_int).get(j_int) == 4){
                    colours = new float[]{
                            0.188f, 0.462f, 0.098f,
                            0.188f, 0.462f, 0.098f,
                            0.188f, 0.462f, 0.098f,
                            0.188f, 0.462f, 0.098f

                    };


                } else if((int)this.room.get(i_int).get(j_int) == -6){
                    colours = new float[]{
                            0f, 0f, 0f,
                            0f, 0f, 0f,
                            0f, 0f, 0f,
                            0f, 0f, 0f

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
                Tree obj = new Tree(mesh);

                obj.setPosition(xOne,-2.05f,yOne);
                obj.setScale(delta);
                gameobjects.add(obj);

                // make the tree objects
                if((int)this.room.get(i_int).get(j_int) %4  == 0  ){

                    Tree objTree = new Tree(treeMesh);

                    objTree.setPosition(xOne,-2.0f,yOne);
                    objTree.setScale(delta/10f);
                    int ranRotation = this.ran.nextInt(90);
                    objTree.setRotation(0,ranRotation,0);

                    gameobjects.add(objTree);
                }

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