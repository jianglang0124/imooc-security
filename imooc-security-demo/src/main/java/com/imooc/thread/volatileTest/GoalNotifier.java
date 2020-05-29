package com.imooc.thread.volatileTest;


public class GoalNotifier implements  Runnable{

    private  volatile boolean goal = false;

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    @Override
    public void run() {

        while (true){
            if(isGoal()){
                System.out.println("Goal!!!!!");

                // tell the referee the ball is in
                // reset goal flag
                setGoal(false);
            }
        }

    }
}
