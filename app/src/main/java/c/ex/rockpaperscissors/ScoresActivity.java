package c.ex.rockpaperscissors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ScoresActivity extends AppCompatActivity {

    public static final String MY_PREFERENCE_FILE = "...";
    public static final int PREFERENCE_MODE_PRIVATE = 0;
    public String nameScore[];
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    TextView name1, name2, name3, score1, score2, score3;
    String nameAndScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);


        preferences = getSharedPreferences(MY_PREFERENCE_FILE, PREFERENCE_MODE_PRIVATE);
        editor = preferences.edit();

        // I save user name and score in one variable. I split it into two parts before showing user name and score in separate TextViews
        nameAndScore = MainActivity.getUserName() + "_" + GameActivity.getUserScore();

        // If any score is already saved then it will be showed in the table
        if (preferences.contains("user1")) useData("user1", name1, score1);
        if (preferences.contains("user2")) useData("user2", name2, score2);
        if (preferences.contains("user3")) useData("user3", name3, score3);

        int score1InTable = Integer.parseInt(score1.getText().toString());
        int score2InTable = Integer.parseInt(score2.getText().toString());
        int score3InTable = Integer.parseInt(score3.getText().toString());

        // Saving the score if the score is greater than zero
        if (GameActivity.getUserScore() > 0) {

            // Saving the score if the field is empty or user's score is greater than the score in this field
            if (name1.getText().toString().matches("User name") || GameActivity.getUserScore() > score1InTable) {

                // Use it to replace previous scores below the new score
                if (preferences.contains("user2")) {

                    saveData("user3", getPreferences("user2"));
                    useData("user3", name3, score3);
                    saveData("user2", getPreferences("user1"));
                    useData("user2", name2, score2);

                } else if (preferences.contains("user1")) {
                    saveData("user2", getPreferences("user1"));
                    useData("user2", name2, score2);
                }

                saveData("user1", nameAndScore);
                useData("user1", name1, score1);


            } else if (name2.getText().toString().matches("User name") || GameActivity.getUserScore() > score2InTable) {

                if (preferences.contains("user3")) {
                    saveData("user3", getPreferences("user2"));
                    useData("user3", name3, score3);
                }

                saveData("user2", nameAndScore);
                useData("user2", name2, score2);


            } else if (name3.getText().toString().matches("User name") || GameActivity.getUserScore() > score3InTable) {

                saveData("user3", nameAndScore);
                useData("user3", name3, score3);

            }
        }
    }

    public String getPreferences(String preferenceName) {

        preferences = getSharedPreferences(MY_PREFERENCE_FILE, PREFERENCE_MODE_PRIVATE);
        String user = preferences.getString(preferenceName, "null");

        return user;
    }


    public void saveData(String preferenceName, String data) {
        editor.putString(preferenceName, data);
        editor.apply();
    }


    // Showing variables in TextViews
    public void useData(String preferenceName, TextView name, TextView score) {

        preferences = getSharedPreferences(MY_PREFERENCE_FILE, PREFERENCE_MODE_PRIVATE);
        String user = preferences.getString(preferenceName, "null");

        nameScore = user.split("_");

        name.setText(nameScore[0]);
        score.setText(nameScore[1]);

    }
    

    public void backToMenu(View v) {

        startActivity(new Intent(ScoresActivity.this, GameActivity.class));
    }
}
