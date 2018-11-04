package com.jamesonlee.androidtetris;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;



public class gameEngine extends  Thread {
    public int[][] gameBoard;

    public Tetronimo currPiece;

    public int currX;
    public int currY;
    public int currScore;

    Handler handler = null;


    boolean pieceInPlay;
    boolean gameOver;
    int score;
    long lastMovedDown;

    Handler commandsHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.arg1) {
                case 0: //Left
                    moveLeft();
                    updateBoard();
                    break;
                case 1:
                    moveRight();
                    updateBoard();
                    break;
                case 2:
                    moveDown();
                    updateBoard();
                    break;
                case 3:
                    moveRotate();
                    updateBoard();
                    break;

                case 4:
                    break;
            }

        }
    };


    public Handler getCommandsHandler() {
        return commandsHandler;
    }


    public void setup() {
        //GLOBAL GLOBALS
        gameBoard = new int[24][18];
        //GLOBALS
        gameOver = false;;
        score = 0;
        //SEMI-GLOBALS
        pieceInPlay = false;

        //set score to 0
        currScore = 0;
    }

    @Override
    public void run () {
        lastMovedDown = System.currentTimeMillis();
        while(!gameOver) {
            //Game is running
            if(!pieceInPlay) {
                generateNewPiece();
                updateBoard();
                pieceInPlay = true;
            }
            boolean hasLanded = false;
            if ((System.currentTimeMillis()-lastMovedDown) > 1500) {
                //hasLanded = shiftDown();
                hasLanded = moveDown();
                lastMovedDown = System.currentTimeMillis();
                updateBoard();
            }
            if(hasLanded) {
                /*ArrayList<Integer> line = checkLine(); // returns empty arraylist if nothing can be removed, returns list of y values to be removed;
                if (line.size() > 0) {
                    removeLines(line);
                    score = score + line.size();
                    updateBoard();
                }
                gameOver = checkGameOver();*/
                checkAndClearLines();
                pieceInPlay = false;

                checkEndGame();
            } //else do nothing and wait
        }
//        Message msg = new Message();
//        msg.arg2 = 1;
//        handler.sendMessage(msg);
        gameOverAndSendScore();
    }

    public void gameOverAndSendScore(){
        Message msg = new Message();
        Bundle dataToUI = new Bundle();
        dataToUI.putInt("gameOverFlag", 1);
        dataToUI.putInt("currScore", currScore);
        msg.setData(dataToUI);
        handler.sendMessage(msg);
    }

    public void updateBoard() {
        Message msg = new Message();
        Bundle dataToUI = new Bundle();
        dataToUI.putSerializable("board", gameBoard);
        dataToUI.putInt("piecetype", currPiece.shape_sel);
        dataToUI.putInt("piecerotation", currPiece.rotation_index);
        dataToUI.putInt("piecex", currX);
        dataToUI.putInt("piecey", currY);
        msg.setData(dataToUI);
        handler.sendMessage(msg);
    }


    public void setHandler(Handler h) {
        handler = h;
    }


    int boardHeightMax = 19;
    int boardWidthMax = 13;
    int boardHeightMin = 4;
    int boardWidthMin = 4;
    boolean tryMove;


    public void tryToRotate(Tetronimo currPiece, int potentialX, int potentialY){
        int tmp = currPiece.shape_sel;
        Tetronimo newPiece = new Tetronimo(tmp);
        newPiece.rotation_index = currPiece.rotation_index;
        newPiece.rotate();
        if(tryToMove(newPiece, potentialX, potentialY) == true){
            currPiece.rotate();
        }
    }



    public boolean tryToMove(Tetronimo newPiece, int potentialX, int potentialY){
        for(int pieceRow = 0; pieceRow<newPiece.shape.length; pieceRow++){
            for(int pieceColumn = 0; pieceColumn<newPiece.shape[0].length; pieceColumn++){
                if(newPiece.shape[pieceRow][pieceColumn]!=0){
                    //there is already something there!
                    if(gameBoard[pieceRow+potentialY][pieceColumn+potentialX]!=0){
                        return false;
                    }
                    //check bounds 3 conditions
                    if(pieceRow+potentialY>boardHeightMax||
                       pieceColumn+potentialX<boardWidthMin||
                       pieceColumn+potentialX>boardWidthMax){
                        return false;
                    }
                }

            }
        }
        //can move! update currentX and current Y!
        currX = potentialX;
        currY = potentialY;
        return true;
    }

    public boolean moveDown(){
        boolean tryMove = tryToMove(currPiece,currX,currY+1);
        if(tryMove == true){
            return false;
        } else {
            for(int pieceRow = 0; pieceRow<currPiece.shape.length; pieceRow++){
                for(int pieceColumn = 0; pieceColumn<currPiece.shape[0].length; pieceColumn++){
                    if(currPiece.shape[pieceRow][pieceColumn]!=0){
                        gameBoard[pieceRow+currY][pieceColumn+currX] = currPiece.shape[pieceRow][pieceColumn];
                    }
                }
            }
            return true;
        }
    }

    public void generateNewPiece(){
        Random r = new Random();
        int x = Math.abs(r.nextInt())%5;
        Tetronimo newPiece = new Tetronimo(x);
        currPiece = newPiece;
        currY = 2;
        currX = 7;
    }

    public void moveRotate() {
        tryToRotate(currPiece, currX, currY);
    }

    public void moveRight(){
        tryToMove(currPiece,currX+1,currY);

    }
    public void moveLeft(){
        tryToMove(currPiece,currX-1,currY);
    }


    public void shiftRow(int row){
        for(int i = row; i>boardHeightMin;i--){
            for(int j = boardWidthMin;j<boardWidthMax;j++){
                gameBoard[i][j] = gameBoard[i-1][j];
            }
        }
        for(int i = boardWidthMin;i<boardWidthMax;i++){
            gameBoard[boardHeightMin][i] = 0;
        }
    }

    public boolean checkEndGame(){
        for(int i = boardWidthMin; i<boardWidthMax;i++){
            if(gameBoard[boardHeightMin][i] !=0) {
                Log.d("end game notice", "end game! ");
                gameOver = true;
                return true;
            }
        }
        return false;
    }

    public void checkAndClearLines(){
        boolean isFilled = false;
        for (int i = boardHeightMin; i<boardHeightMax+1;i++){
            isFilled = true;
            for(int j = boardWidthMin; j<boardWidthMax+1;j++){
                if(gameBoard[i][j] == 0){
                    isFilled = false;
                    break;
                }
            }
            if(isFilled == true){
                shiftRow(i);
                currScore++;
                Log.d("Currect score", currScore+"");
            }
        }

    }
}
