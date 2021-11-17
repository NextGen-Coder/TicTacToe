package co.in.nextgencoder.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class OneSidedActivity extends AppCompatActivity {

    private TextView[][] ticTacToeBoard = new TextView[3][3];

    private TextView player1NameTV, player2NameTV;
    private Button restartBtn;

    private String player1Name;
    private String player2Name;

    private final String PLAYER_1 = "X", PLAYER_2 = "O";
    private String turn = PLAYER_1;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_sided);

        player1NameTV = findViewById( R.id.player1NameTV);
        player2NameTV = findViewById( R.id.player2NameTV);
        restartBtn = findViewById( R.id.restartBtn);

        dialog = new Dialog( this);

        initializeBoard();
        initializePlayerNames();

        for( int i=0; i<3; i++) {
            for( int j=0; j<3; j++) {
                final int tempI = i;
                final int tempJ = j;

                ticTacToeBoard[i][j].setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        afterSelectingBox( tempI, tempJ);
                    }
                });
            }
        }

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn = "X";
                clearBoard();
            }
        });
    }

    private void afterSelectingBox(int i, int j) {

        if( ticTacToeBoard[i][j].getText().toString().equals( "")) {
            ticTacToeBoard[i][j].setText( turn);

            checkForResult();

            if( turn.equals( PLAYER_1)) {
                turn = PLAYER_2;
            } else {
                turn = PLAYER_1;
            }
        }
    }

    private void checkForResult() {
        boolean isWinnerDecided = false;

        // Horizontal
        if( ticTacToeBoard[0][0].getText().toString().equals( ticTacToeBoard[0][1].getText().toString())  &&
                ticTacToeBoard[0][0].getText().toString().equals( ticTacToeBoard[0][2].getText().toString()) &&
                !ticTacToeBoard[0][0].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        if( ticTacToeBoard[1][0].getText().toString().equals( ticTacToeBoard[1][1].getText().toString())  &&
                ticTacToeBoard[1][0].getText().toString().equals( ticTacToeBoard[1][2].getText().toString()) &&
                !ticTacToeBoard[1][0].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        if( ticTacToeBoard[2][0].getText().toString().equals( ticTacToeBoard[2][1].getText().toString())  &&
                ticTacToeBoard[2][0].getText().toString().equals( ticTacToeBoard[2][2].getText().toString()) &&
                !ticTacToeBoard[2][0].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        // Vertical
        if( ticTacToeBoard[0][0].getText().toString().equals( ticTacToeBoard[1][0].getText().toString())  &&
                ticTacToeBoard[0][0].getText().toString().equals( ticTacToeBoard[2][0].getText().toString()) &&
                !ticTacToeBoard[0][0].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        if( ticTacToeBoard[0][1].getText().toString().equals( ticTacToeBoard[1][1].getText().toString())  &&
                ticTacToeBoard[0][1].getText().toString().equals( ticTacToeBoard[2][1].getText().toString()) &&
                !ticTacToeBoard[0][1].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        if( ticTacToeBoard[0][2].getText().toString().equals( ticTacToeBoard[1][2].getText().toString())  &&
                ticTacToeBoard[0][2].getText().toString().equals( ticTacToeBoard[2][2].getText().toString()) &&
                !ticTacToeBoard[0][2].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        // Diagonal
        if( ticTacToeBoard[0][0].getText().toString().equals( ticTacToeBoard[1][1].getText().toString())  &&
                ticTacToeBoard[0][0].getText().toString().equals( ticTacToeBoard[2][2].getText().toString()) &&
                !ticTacToeBoard[0][0].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        if( ticTacToeBoard[0][2].getText().toString().equals( ticTacToeBoard[1][1].getText().toString())  &&
                ticTacToeBoard[0][2].getText().toString().equals( ticTacToeBoard[2][0].getText().toString()) &&
                !ticTacToeBoard[0][2].getText().toString().equals("")) {
            isWinnerDecided = true;
        }

        if( isWinnerDecided) {
            afterWin();
        }

        boolean areAllFilled = true;
        for( int i=0; i<3; i++) {
            for( int j=0; j<3; j++) {
                if( ticTacToeBoard[i][j].getText().toString().equals( "")) {
                    areAllFilled = false;
                }
            }
        }

        if( areAllFilled) {
            afterDraw();
        }
    }

    private void afterDraw() {
        clearBoard();
        dialog.setContentView( R.layout.dialog_result_one_sided);
        TextView winnerNameTV = dialog.findViewById( R.id.winnerName);
        VideoView videoView = dialog.findViewById( R.id.videoView);
        Button playAgainBtn = dialog.findViewById( R.id.playAgainBtn);

        String videoUrl = "android.resource://"+getPackageName()+"/"+R.raw.one_sided_draw;
        Uri uri = Uri.parse( videoUrl);
        videoView.setVideoURI( uri);

        videoView.start();
        winnerNameTV.setText( R.string.draw_message);

        playAgainBtn.setOnClickListener( v -> {
            clearBoard();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void afterWin() {
        dialog.setContentView( R.layout.dialog_result_one_sided);
        TextView winnerNameTV = dialog.findViewById( R.id.winnerName);
        VideoView videoView = dialog.findViewById( R.id.videoView);
        Button playAgainBtn = dialog.findViewById( R.id.playAgainBtn);

        String videoUrl = "android.resource://"+getPackageName()+"/"+R.raw.result_win;
        Uri uri = Uri.parse( videoUrl);
        videoView.setVideoURI( uri);

        videoView.start();

        if( turn.equals( "X")) {
            winnerNameTV.setText( player1Name);
        } else {
            winnerNameTV.setText( player2Name);
        }

        playAgainBtn.setOnClickListener( v -> {
            clearBoard();
            dialog.dismiss();
        });

        dialog.show();
        clearBoard();
    }

    private void clearBoard() {
        for( int i=0; i<3; i++) {
            for( int j=0; j<3; j++) {
                ticTacToeBoard[i][j].setText( "");
            }
        }
        turn = PLAYER_1;
    }

    private void initializePlayerNames() {
        player1Name = getIntent().getStringExtra( "player1Name");
        player2Name = getIntent().getStringExtra( "player2Name");
        player1NameTV.setText( player1Name);
        player2NameTV.setText( player2Name);
    }

    private void initializeBoard() {
        ticTacToeBoard[0][0] = findViewById( R.id.btn00);
        ticTacToeBoard[0][1] = findViewById( R.id.btn01);
        ticTacToeBoard[0][2] = findViewById( R.id.btn02);
        ticTacToeBoard[1][0] = findViewById( R.id.btn10);
        ticTacToeBoard[1][1] = findViewById( R.id.btn11);
        ticTacToeBoard[1][2] = findViewById( R.id.btn12);
        ticTacToeBoard[2][0] = findViewById( R.id.btn20);
        ticTacToeBoard[2][1] = findViewById( R.id.btn21);
        ticTacToeBoard[2][2] = findViewById( R.id.btn22);
    }

    public void clearBoard(View view) {
        clearBoard();
    }
}