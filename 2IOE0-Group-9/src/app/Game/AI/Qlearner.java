package app.Game.AI;

import java.util.Random;

/*
TODO:
 //getQTableSize()              (potentially unnecessary)
 getStates()          (potentially unnecessary)
 //selectAction(state)
 //selectRandomAction()
 //setLearningRate(learningrate)
 updateQTable(state, action ,newState (potentially unnecessary) , double reward);
 */

public class Qlearner {
    float[][] qTable;
    float learningRate;
    int numberOfStates = 11;
    int state;
    int action;
    int newState;


    public Qlearner(){
        initTable();
        learningRate = (float) 1.0; // this needs to tested and tuned.


    }

    public Qlearner(float learningRate){
        initTable();
        this.learningRate = learningRate;
    }

    private void initTable(){
        this.qTable = new float[numberOfStates][2];
        for(int i = 0 ; i < numberOfStates ; i++){
            qTable[i][0] = (float) 1.0;
            if(i < 5){
                qTable[i][1] = (float) 0.0;
            } else {
                qTable[i][1] = (float) 2.0;
            }
        }
    }

    public int[] getBestActions(){
        int[] bestActions = new int[numberOfStates];
        for(int i = 0; i < this.qTable.length ; i++){
            if(qTable[i][0] > qTable[i][1]){
                bestActions[i] = 0;
            } else {
                bestActions[i] = 1;
            }
        }
        return bestActions;

    }
    public int getNumberOfActions(){
        return qTable[0].length;
    }

    public int getNumberOfStates(){
        return this.qTable.length;
    }

    public float getLearningRate(){
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }
    
    public int getQTableSize() {
    	return(qTable.length * qTable[0].length);
    } 
    
    public Object selectAction(Object state) {
    	return ((Qlearner) state).getBestActions();
    }
    
    Random random = new Random();
    
    public Object selectRandomAction() {
    	float randomN = new Random(getNumberOfActions()).nextFloat();
		return randomN;
    }
    
    public void updateQTable(Object state, Object action , Object newState , double reward) {
    	
        for(int i = 0 ; i < numberOfStates ; i++){
            qTable[i][0] = (float) ((double) selectAction(state) * reward);
            if(i < 5){
                qTable[i][1] = (float) ((double) selectAction(state) * reward);
            } else {
                qTable[i][1] = (float) ((double) selectRandomAction() * reward);
            }
        }
    }
    
}
