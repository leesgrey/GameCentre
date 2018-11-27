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

public class MasterMindGameActivity extends AppCompatActivity {
    private String currentUser;
    private int size;
    private Spinner singleEntryGuess;
    private int inputCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mastermindgame);

        Intent i = getIntent();
        this.currentUser = i.getStringExtra("currentUser");
        TextView currUser = findViewById(R.id.currentuserText);
        currUser.setText(String.format("Player: %s", currentUser));

        this.size = i.getIntExtra("size", 5);

        setGameVisual();

        this.singleEntryGuess = (Spinner) findViewById(R.id.singleEntryGuess);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.possible_inputs
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        singleEntryGuess.setAdapter(adapter);

        inputListener();
        submitGuessListener();
        undoListener();

        MasterMindManager manager = new MasterMindManager(this.size, 5);



    }

    //TODO: remove code smells and preventing from doing this inefficiently
    void setGameVisual() {
        if (this.size == 3) {
            TextView text = findViewById(R.id.firstEntryFour);
            text.setText("");
            TextView text1 = findViewById(R.id.firstEntryFive);
            text1.setText("");
            TextView text2 = findViewById(R.id.secondEntryFour);
            text2.setText("");
            TextView text3 = findViewById(R.id.secondEntryFive);
            text3.setText("");
            TextView text4 = findViewById(R.id.thirdEntryFour);
            text4.setText("");
            TextView text5 = findViewById(R.id.thirdEntryFive);
            text5.setText("");
            TextView text6 = findViewById(R.id.fourthEntryFour);
            text6.setText("");
            TextView text7 = findViewById(R.id.fourthEntryFive);
            text7.setText("");
            TextView text8 = findViewById(R.id.fifthEntryFour);
            text8.setText("");
            TextView text9 = findViewById(R.id.fifthEntryFive);
            text9.setText("");
        } else if (this.size == 4) {
            TextView text1 = findViewById(R.id.firstEntryFive);
            text1.setText("");
            TextView text3 = findViewById(R.id.secondEntryFive);
            text3.setText("");
            TextView text5 = findViewById(R.id.thirdEntryFive);
            text5.setText("");
            TextView text7 = findViewById(R.id.fourthEntryFive);
            text7.setText("");
            TextView text9 = findViewById(R.id.fifthEntryFive);
            text9.setText("");
        }
    }

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
            TextView guess1 = findViewById(R.id.guess1);
            guess1.setText(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 2) {
            TextView guess2 = findViewById(R.id.guess2);
            guess2.setText(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 3) {
            TextView guess3 = findViewById(R.id.guess3);
            guess3.setText(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 4) {
            TextView guess4 = findViewById(R.id.guess4);
            guess4.setText(text);
            this.inputCounter += 1;
        } else if (this.inputCounter == 5) {
            TextView guess5 = findViewById(R.id.guess5);
            guess5.setText(text);
            this.inputCounter += 1;
        } else{
            return;
        }
    }

    void submitGuessListener(){
        Button submitButton = findViewById(R.id.submitGuessButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: take the guesses shown on the code and implementing it further
            }
        });
    }

    void undoListener(){
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: take the corresponding value and append to the guesses
            }
        });
    }
}
