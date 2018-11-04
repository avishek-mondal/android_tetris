package com.jamesonlee.androidtetris;

import com.jamesonlee.androidtetris.Tetronimo;
import com.jamesonlee.androidtetris.gameEngine;

import java.util.Random;
import java.lang.Math;



public class gameEngineFunctions {
	Tetronimo curPiece;
	int currX;
	int currY;
	int boardHeightMax = 20;	//is the board height
	int boardWidthMax = 14;		//is the board width
	int boardHeightMin = 4;
	int boardWidthMin = 4;
	int [][] gameBoard; 	//is the board saying if something has landed
	//boolean tryMove;
	boolean hasLanded;

	/*
	int boardHeightMax = 20;
	int boardWidthMax = 14;
	int boardHeightMin = 4;
	int boardWidthMin = 4;
	boolean tryMove;
	*/	

	public void tryToRotate(Tetronimo curPiece, int potentialX, int potentialY){
		Tetronimo newPiece = curPiece;
		newPiece.rotate();
		if(tryToMove(newPiece, potentialX, potentialY) == true){
			curPiece = newPiece;
		}
	}

	public void moveRotate() {
		tryMove = tryToRotate(currPiece, currX, currY);
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
					if(pieceRow+potentialY>=boardHeightMax||pieceColumn+potentialX<boardWidthMin||pieceColumn+potentialX>boardWidthMax){
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

	public void generateNewPiece(){
		Random r = new Random();
		int x = Math.abs(r.nextInt())%5;
		Tetronimo newPiece = new Tetronimo(x);
		currPiece = newPiece;
		currY = 0;
		currX = 7;
	}

	public void moveRight(){
		tryToMove(currPiece,currX+1,currY);

	}
	public void moveLeft(){
		tryToMove(currPiece,currX-1,currY);
	}
	
	public boolean moveDown(){
		boolean tryMove = tryToMove(currPiece,currX,currY+1);
		if(tryMove == true){
			return false;
		}
		if (tryMove == false){			
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
	
	public void checkAndClearLines(){
		boolean isFilled = false;
		for (int i = boardHeightMin; i<boardHeightMax;i++){
			isFilled = true;
			for(int j = boardWidthMin; j<boardWidthMax;j++){
				if(gameBoard[i][j] == 0){
					isFilled = false;
					break;
				}
			}
			if(isFilled == true){
				shiftRow(i);
			}
		}
		
	}
	
}