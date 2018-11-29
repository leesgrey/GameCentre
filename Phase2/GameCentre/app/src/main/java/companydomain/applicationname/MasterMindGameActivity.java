package companydomain.applicationname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MasterMindGameActivity extends AppCompatActivity {
    private MasterMindManager manager;
    private String currentUser;
    private int size;
    private Spinner singleEntryGuess;
    private int inputCounter = 1;
    private int[] answer;
    private TextView text1, text2, text3, text4, text5, text6, text7, text8, text9, text10,
            guess1, guess2, guess3, guess4, guess5, correct1, correct2, correct3, correct4, score;

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
                changeText(text);
            }
        });
    }

    void changeText(String text){
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

    void submitGuessListener(){
        Button submitButton = findViewById(R.id.submitGuessButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer();
            }
        });
    }

    void submitAnswer(){
        this.manager.makeGuess(this.answer);
        resetText();
        displayResult();
    }

    void displayResult(){
        MasterMindCombination[] lastNGuesses = this.manager.getLastNGuesses(2);
        this.score.setText(String.valueOf(this.manager.getScore()));
        if (this.size == 3){
            displayThree(lastNGuesses);
        } else if (this.size == 4){
            displayFour(lastNGuesses);
        } else{
            displayFive(lastNGuesses);
        }
    }

    void displayThree(MasterMindCombination[] lastNGuesses){
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

    void displayFour(MasterMindCombination[] lastNGuesses){
        int[] secondLast = lastNGuesses[1].getCode();
        int[] secondCorrect = lastNGuesses[1].getCorrectness();
        this.text1.setText(String.valueOf(secondLast[0]));
        this.text2.setText(String.valueOf(secondLast[1]));
        this.text3.setText(String.valueOf(secondLast[2]));
        this.text4.setText(String.valueOf(secondLast[3]));
        this.correct1.setText(String.valueOf(secondCorrect[0]));
        this.correct2.setText(String.valueOf(secondCorrect[1]));
        int[] last = lastNGuesses[0].getCode();
        int[] lastCorrect = lastNGuesses[0].getCorrectness();
        this.text6.setText(String.valueOf(last[0]));
        this.text7.setText(String.valueOf(last[1]));
        this.text8.setText(String.valueOf(last[2]));
        this.text9.setText(String.valueOf(last[3]));
        this.correct3.setText(String.valueOf(lastCorrect[0]));
        this.correct4.setText(String.valueOf(lastCorrect[1]));
    }

    void displayFive(MasterMindCombination[] lastNGuesses){
        int[] secondLast = lastNGuesses[1].getCode();
        int[] secondCorrect = lastNGuesses[1].getCorrectness();
        this.text1.setText(String.valueOf(secondLast[0]));
        this.text2.setText(String.valueOf(secondLast[1]));
        this.text3.setText(String.valueOf(secondLast[2]));
        this.text4.setText(String.valueOf(secondLast[3]));
        this.text5.setText(String.valueOf(secondLast[3]));
        this.correct1.setText(String.valueOf(secondCorrect[0]));
        this.correct2.setText(String.valueOf(secondCorrect[1]));
        int[] last = lastNGuesses[0].getCode();
        int[] lastCorrect = lastNGuesses[0].getCorrectness();
        this.text6.setText(String.valueOf(last[0]));
        this.text7.setText(String.valueOf(last[1]));
        this.text8.setText(String.valueOf(last[2]));
        this.text9.setText(String.valueOf(last[3]));
        this.text10.setText(String.valueOf(last[4]));
        this.correct3.setText(String.valueOf(lastCorrect[0]));
        this.correct4.setText(String.valueOf(lastCorrect[1]));

    }

    void undoListener(){
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoMove();
            }
        });
    }

    void undoMove(){
        this.manager.undoMove();
        displayResult();
        this.score.setText(String.valueOf(this.manager.getScore()));
    }

    void clearListener(){
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetText();
            }
        });
    }

    void resetText(){
        this.guess1.setText(""); this.guess2.setText(""); this.guess3.setText("");
        this.guess4.setText(""); this.guess5.setText("");
        this.inputCounter = 1;
        this.answer = new int[this.size];
    }
}
