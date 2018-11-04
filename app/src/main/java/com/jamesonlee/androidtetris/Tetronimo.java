package com.jamesonlee.androidtetris;

public class Tetronimo {
    public int rotation_index;
    public int shape_sel;
    public int[][] shape;
    
    public Tetronimo(int a){ //constructor requires int input
        rotation_index = 0;
        shape_sel = a;
        this.setShape();
    }
    
    public void setShape(){
        switch(shape_sel){
            case 0:     //L shape
                switch(rotation_index){
                    case 0:
                        this.shape= new int[][]{{0,0,0,0},{0,1,0,0},{0,1,0,0},{0,1,1,0}};
                        break;
                    case 1:
                        this.shape= new int[][]{{0,0,0,0},{0,0,1,0},{1,1,1,0}, {0,0,0,0}};
                        break;
                    case 2:
                        this.shape = new int[][]{{0,0,0,0},{1,1,0,0},{0,1,0,0},{0,1,0,0}};
                        break;
                    case 3:
                        this.shape = new int[][] {{0,0,0,0}, {0,0,0,0},{1,1,1,0},{1,0,0,0}};
                        break;
                    default: this.shape = new int[][]{{0},{0}};
                }
                break;
            case 1: //O shape
                this.shape = new int[][]{{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}};
                break;
            case 2: //Z shape
                switch(rotation_index){
                    case 0:
                        this.shape = new int[][]{{0,0,0,0},{0,1,1,0},{0,0,1,1},{0,0,0,0}};
                        break;
                    case 1:
                        this.shape = new int[][]{{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}};
                        break;
                    case 2:
                        this.shape = new int[][]{{0,0,0,0},{0,1,1,0},{0,0,1,1},{0,0,0,0}};
                        break;
                    case 3:
                        this.shape = new int[][]{{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}};
                        break;
                    default: this.shape = new int[][]{{0},{0}};
                }
                break;
            case 3: //I shape
                switch(rotation_index){
                    case 0:
                        this.shape = new int[][]{{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}};
                        break;
                    case 1:
                        this.shape = new int[][]{{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
                        break;
                    case 2:
                        this.shape = new int[][]{{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}};
                        break;
                    case 3:
                        this.shape = new int[][]{{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
                        break;
                    default: this.shape = new int[][]{{0},{0}};
                }
                break;
            case 4: //T shape
                switch(rotation_index){
                    case 0:
                        this.shape = new int[][]{{0,0,0,0},{0,1,1,1},{0,0,1,0},{0,0,0,0}};
                        break;
                    case 1:
                        this.shape = new int[][]{{0,0,1,0},{0,0,1,1},{0,0,1,0},{0,0,0,0}};
                        break;
                    case 2:
                        this.shape = new int[][]{{0,0,1,0},{0,1,1,1},{0,0,0,0},{0,0,0,0}};
                        break;
                    case 3:
                        this.shape = new int[][]{{0,0,1,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}};
                        break;
                    default: this.shape = new int[][]{{0},{0}};
                }
                break;
            default: this.shape = new int[][]{{0},{0}};

        }

    }
    
    public void rotate(){
        rotation_index = (rotation_index+1)%4;
        this.setShape();
    }
    
}