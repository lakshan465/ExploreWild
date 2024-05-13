package com.example.demo3;

import javafx.concurrent.Task;

public class LoadingTask extends Task<Integer> {


    @Override
    protected Integer call() throws Exception {
        for (double i = 0; i <=100 ; i++) {
            updateProgress(i,100);
            Thread.sleep(10);
        }




        return 100;
    }
}
