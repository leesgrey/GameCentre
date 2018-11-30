package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MasterMindGameActivity extends AppCompatActivity {
    /**
     * The manager for the MasterMindGame
     */
    private MasterMindManager manager;

    /**
     * String containing the current user
     */
    private String currentUser;

    /**
     * The size of the game passed from the previous frame
     */
    private int size;

    /**
     * Variable keeping track of the input for the answers
     */
    private int inputCounter = 1;

    /**
     * Contains the users answers
     */
    private int[] answer;

    /**
     * The GUI components that need to be kept track for the game to be updated properly
     */
    private Spinner singleEntryGuess;
    private TextView text1, text2, text3, text4, text5, text6, text7, text8, text9, text10,
            guess1, guess2, guess3, guess4, guess5, correct1, correct2, correct3, correct4, score;
    private ScoreBoard scoreBoard;

    /**
     *
     * Code that sets up the MasterMindGame UI. a
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mastermindgame);

        //Receiving the values passed from the previous frame
        Intent i = getIntent();
        this.currentUser = i.getStringExtra("currentUser");
        TextView currUser = findViewById(R.id.currentuserText);
        currUser.setText(String.format("Player: %s", currentUser));
        this.size = i.getIntExtra("size", 5);

        //Setting the spinner
        this.singleEntryGuess = (Spinner) findViewById(R.id.singleEntryGuess);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.possible_inputs
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        singleEntryGuess.setAdapter(adapter);

        //Setting the GUI for the game
        setFields();
        setGameVisual();
        inputListener();
        submitGuessListener();
        undoListener();
        clearListener();

        //Generating the game itself
        this.manager = new MasterMindManager(this.size, 5);
        this.answer = new int[this.size];
        this.scoreBoard = ScoreBoard.loadScoreBoard("masterMind", this);
    }

    /**
     * Quick setting of the fields so that the display can be updated easily
     */
    void setFields() {
        this.text1 = findViewById(R.id.firstEntryOne);
        this.text2 = findViewById(R.id.firstEntryTwo);
        this.text3 = findViewById(R.id.firstEntryThree);
        this.text4 = findViewById(R.id.firstEntryFour);
        this.text5 = findViewById(R.id.firstEntryFive);
        this.text6 = findViewById(R.id.secondEntryOne);
        this.text7 = findViewById(R.id.secondEntryTwo);
        this.text8 = findViewById(R.id.secondEntryThree);
        this.text9 = findViewById(R.id.secondEntryFour);
        this.text10 = findViewById(R.id.secondEntryFive);
        this.guess1 = findViewById(R.id.guess1);
        this.guess2 = findViewById(R.id.guess2);
        this.guess3 = findViewById(R.id.guess3);
        this.guess4 = findViewById(R.id.guess4);
        this.guess5 = findViewById(R.id.guess5);
        this.correct1 = findViewById(R.id.firstCorrectOne);
        this.correct2 = findViewById(R.id.firstCorrectTwo);
        this.correct3 = findViewById(R.id.secondCorrectOne);
        this.correct4 = findViewById(R.id.secondCorrectTwo);
        this.score = findViewById(R.id.score);
    }

    /***
     * Method that sets the visual of the game depending on the game size indicated from the menu
     */
    void setGameVisual() {
        if (this.size == 3) {
            this.text4.setText("");
            this.text5.setText("");
            this.text9.setText("");
            this.text10.setText("");
        } else if (this.size == 4) {
            this.text5.setText("");
            this.text10.setText("");
        }
    }

    /**
     * Listener that keeps track of the input
     */
    void inputListener(){
        Button inputButton = findViewById(R.id.inputEntryButton);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = singleEntryGuess.getSelectedItem().toString();
                changeAnswer(text);
            }
        });
    }

    /**
     * Updates the temporary answer fields by the user so that answer can be edited and finalised
     * @param text
     */
    void changeAnswer(String text){
        if (this.inputCounter == 1){
            this.guess1.setText(text); this.answer[this.inputCounter - 1] = Integer.parseInt(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 2) {
            this.guess2.setText(text); this.answer[this.inputCounter - 1] = Integer.parseInt(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 3) {
            this.guess3.setText(text); this.answer[this.inputCounter - 1] = Integer.parseInt(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 4 && this.size > 3) {
            this.guess4.setText(text); this.answer[this.inputCounter - 1] = Integer.parseInt(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 5 && this.size > 4) {
            this.guess5.setText(text); this.answer[this.inputCounter - 1] = Integer.parseInt(text);
            this.inputCounter += 1;
        }
    }

    /**
     * submitButton listener that keep tracks of submission of the answer for the game
     */
    void submitGuessListener(){
        Button submitButton = findViewById(R.id.submitGuessButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer();
            }
        });
    }

    /**
     * Submitting the answer to the masterMindManager backend and interacting with the game
     */
    void submitAnswer(){
        if(this.manager.makeGuess(this.answer)){
            displayResult();
            endGame();
        } else{
            resetText();
            displayResult();
        }
    }

    /**
     * endGame method that writes to the scoreboard class and calls the scoreboard gui method
     */
    void endGame(){
        Score score = new Score(currentUser,
                this.manager.getScore(), this.size);
        this.scoreBoard.addScore(score);
        this.scoreBoard.writeScoreBoard(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switchToScoreboard();
            }
        }, 2000);
    }

    /**
     * Method that switches to the scoreboard and passes on several values to the game
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("currentUser", currentUser);
        tmp.putExtra("previousGame", "masterMind");
        ArrayList<String> difficulties = new ArrayList<>();
        difficulties.add("3");
        difficulties.add("4");
        difficulties.add("5");
        tmp.putStringArrayListExtra("difficulties", difficulties);
        startActivity(tmp);
    }

    /**
     * Updating the result of the user's moves with the GUI
     */
    void displayResult(){
        MasterMindCombination[] lastNGuesses = this.manager.getLastNGuesses(2);
        this.score.setText(String.valueOf(this.manager.getScore()));
        System.out.println(this.manager.getAnswerCode()[0]);
        System.out.println(this.manager.getAnswerCode()[1]);
        System.out.println(this.manager.getAnswerCode()[2]);
        if (this.size == 3){
            displayThree(lastNGuesses);
        } else if (this.size == 4){
            displayFour(lastNGuesses);
        } else{
            displayFive(lastNGuesses);
        }
    }

    /**
     * displaying the results of user input for size 3 games
     * @param lastNGuesses
     */
    void displayThree(MasterMindCombination[] lastNGuesses){
        standardDisplay(lastNGuesses);
    }

    /**
     * Displaying the results of user input for size 4 games
     * @param lastNGuesses
     */
    void displayFour(MasterMindCombination[] lastNGuesses){
        standardDisplay(lastNGuesses);
        int[] secondLast = lastNGuesses[1].getCode();
        this.text4.setText(String.valueOf(secondLast[3]));
        int[] last = lastNGuesses[0].getCode();
        this.text9.setText(String.valueOf(last[3]));
    }

    /**
     * Displaying the results of user input for size 5 games
     */
    void displayFive(MasterMindCombination[] lastNGuesses){
        standardDisplay(lastNGuesses);
        int[] secondLast = lastNGuesses[1].getCode();
        this.text4.setText(String.valueOf(secondLast[3]));
        this.text5.setText(String.valueOf(secondLast[3]));
        int[] last = lastNGuesses[0].getCode();
        this.text9.setText(String.valueOf(last[3]));
        this.text10.setText(String.valueOf(last[4]));
    }

    /**
     * Helper method that helps the displayThree, displayFour, displayFive methods to prevent code
     * duplication
     * @param lastNGuesses
     */
    void standardDisplay(MasterMindCombination[] lastNGuesses){
        int[] secondLast = lastNGuesses[1].getCode();
        int[] secondCorrect = lastNGuesses[1].getCorrectness();
        this.text1.setText(String.valueOf(secondLast[0]));
        this.text2.setText(String.valueOf(secondLast[1]));
        this.text3.setText(String.valueOf(secondLast[2]));
        this.correct1.setText(String.valueOf(secondCorrect[0]));
        this.correct2.setText(String.valueOf(secondCorrect[1]));
        int[] last = lastNGuesses[0].getCode();
        int[] lastCorrect = lastNGuesses[0].getCorrectness();
        this.text6.setText(String.valueOf(last[0]));
        this.text7.setText(String.valueOf(last[1]));
        this.text8.setText(String.valueOf(last[2]));
        this.correct3.setText(String.valueOf(lastCorrect[0]));
        this.correct4.setText(String.valueOf(lastCorrect[1]));
    }

    /**
     * Listener button that undoes a move when the undo button is pressed
     */
    void undoListener(){
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoMove();
            }
        });
    }

    /**
     * undoMove logic behind the undoListener
     */
    void undoMove(){
        this.manager.undoMove();
        displayResult();
        this.score.setText(String.valueOf(this.manager.getScore()));
    }

    /**
     * Clears the user inputs for a potential answer
     */
    void clearListener(){
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetText();
            }
        });
    }

    /**
     * Resets the text of the inputs so that the user can choose a new set of answers
     */
    void resetText(){
        this.guess1.setText(""); this.guess2.setText(""); this.guess3.setText("");
        this.guess4.setText(""); this.guess5.setText("");
        this.inputCounter = 1;
        this.answer = new int[this.size];
    }
}
