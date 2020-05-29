package com.imooc.thread.volatileTest;

public class Game {


    public static void main(String[] args) {

        GoalNotifier goalNotifier = new GoalNotifier();
        Thread thread = new Thread(goalNotifier);
        thread.start();

        // after 3s
        try {
        Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }

        goalNotifier.setGoal(true);


    }
}
