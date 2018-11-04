package com.jamesonlee.androidtetris;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import com.jamesonlee.androidtetris.drawingTools;

import java.util.NoSuchElementException;

public class mainGame extends AppCompatActivity {
    //Global Variables, because I'm a motherfucking rogue
    Button leftButton;
    Button rightButton;
    Button downButton;
    Button rotateButton;

    drawingTools d;
    drawingTools d_piece;

    ImageView gameBoard;

    Bitmap b;
    Canvas c;

    int[][] landedBoard = new int[24][18];
    int currX;
    int currY;
    Tetronimo currPiece;

    int blockSize;

    boolean initialised = false;

    //DisplayMetrics displayMetrics = new DisplayMetrics();
    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    Handler handlerUIMessages = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            if(msg.getData().getInt("gameOverFlag") > 0){
                Intent goToNameAndHS    = new Intent(mainGame.this, sendNameAndHighScore.class);
                goToNameAndHS.putExtras(msg.getData());
                mainGame.this.startActivity(goToNameAndHS);

            } else{
                Bundle boardData = msg.getData();
                landedBoard = (int[][]) boardData.getSerializable("board");
                currX = boardData.getInt("piecex");
                currY = boardData.getInt("piecey");
                currPiece = new Tetronimo(boardData.getInt("piecetype"));
                currPiece.rotation_index = boardData.getInt("piecerotation");
                currPiece.setShape();
                updateBoard();
            }
        }
    };

    gameEngine foo;

    Handler commandHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Setup layout
        setLayoutElements();
        setButtonSize();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!initialised) { //ensures code only runs once
            initialised = true;
            //Setup canvas
            setupCanvas();

            foo = new gameEngine();
            foo.setup();
            foo.setHandler(handlerUIMessages);
            commandHandler = foo.getCommandsHandler();

            leftButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.arg1 = 0;
                    commandHandler.sendMessage(msg);
                }
            });
            rightButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.arg1 = 1;
                    commandHandler.sendMessage(msg);
                }
            });
            downButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.arg1 = 2;
                    commandHandler.sendMessage(msg);
                }
            });
            rotateButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.arg1 = 3;
                    commandHandler.sendMessage(msg);
                }
            });
            foo.start();
            //foo.run();

        }
    }

    private void setupCanvas() {
        int canvasWidth = gameBoard.getMeasuredWidth();
        int canvasHeight = gameBoard.getMeasuredHeight();
        int blockSizeX = (int)Math.floor((double)(canvasWidth/10));
        int blockSizeY = (int)Math.floor((double)(canvasHeight/16));
        if (blockSizeX > blockSizeY) {
            blockSize = blockSizeY;
        } else {
            blockSize = blockSizeX;
        }
        b = Bitmap.createBitmap(blockSize*10, blockSize*16, Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        d = new drawingTools();
        d.setPaint(255,0,0,255);
        d.setBorder(255,128,128,128);
        d.setFill(255,0,0,0);
        d.setCanvas(c);
        d.setBlockSize(blockSize);
        d.fill();

        d_piece = new drawingTools();
        d_piece.setPaint(255,255,0,0);
        d_piece.setBorder(255,128,128,128);
        d_piece.setFill(255,0,0,0);
        d_piece.setCanvas(c);
        d_piece.setBlockSize(blockSize);
        d_piece.fill();

        gameBoard.setImageBitmap(b);
    }

    public void updateBoard() {
        int[][] localBoard = new int[16][10];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                localBoard[i][j] = landedBoard[i+4][j+4];
            }
        }
        //Clear board
        d.fill();

        //Draw landed board
        for (int i = 4; i < 20; i++) {
            for (int j = 4; j < 14; j++){
                if (landedBoard[i][j] == 1) {
                    d.drawBlock(j-4,i-4);
                }
            }
        }

        //Draw piece
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currPiece.shape[i][j] == 1) {
                    d_piece.drawBlock(currX + j - 4,currY + i - 4);
                }
            }
        }

        gameBoard.setImageBitmap(b);
    }

    private void setButtonSize() {
        int padding = 4; //dp
        int numberOfButtons = 4;
        padding = (int) (padding*(getResources().getDisplayMetrics().density) + 0.5f); //convert to dp
        int buttonWidth = screenWidth/numberOfButtons;
        leftButton.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth,buttonWidth));
        leftButton.setPadding(padding, padding, padding, padding);
        rightButton.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth,buttonWidth));
        rightButton.setPadding(padding, padding, padding, padding);
        downButton.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth,buttonWidth));
        downButton.setPadding(padding, padding, padding, padding);
        rotateButton.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth,buttonWidth));
        rotateButton.setPadding(padding, padding, padding, padding);
    }

    private void setLayoutElements() {
        leftButton = (Button) findViewById(R.id.buttonLeft);
        rightButton = (Button) findViewById(R.id.buttonRight);
        downButton = (Button) findViewById(R.id.buttonDown);
        rotateButton = (Button) findViewById(R.id.buttonRotate);
        gameBoard = (ImageView) findViewById(R.id.imgView);
    }

    private Bitmap scaleImage(Bitmap original, int requiredWidth) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = original;

        /*try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
            bitmap = Ion.with(view).getBitmap();
        }*/

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        //int bounding = dpToPx(requiredWidth);
        int bounding = requiredWidth;
        Log.i("Test", "original width = " + Integer.toString(width));
        Log.i("Test", "original height = " + Integer.toString(height));
        Log.i("Test", "bounding = " + Integer.toString(bounding));

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + Float.toString(xScale));
        Log.i("Test", "yScale = " + Float.toString(yScale));
        Log.i("Test", "scale = " + Float.toString(scale));

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        //BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        //Log.i("Test", "scaled width = " + Integer.toString(width));
        //Log.i("Test", "scaled height = " + Integer.toString(height));

        // Apply the scaled bitmap
        //view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //params.width = width;
        //params.height = height;
        //view.setLayoutParams(params);

        //Log.i("Test", "done");
        return scaledBitmap;
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
