package com.example.emojiwriter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String[] wordsList;

    EditText textInput;
    TextView outTextView;

    View.OnClickListener countListener = v -> {
        EditText countEditText = findViewById(R.id.input_count);
        int count = Integer.parseInt(countEditText.getText().toString());
        StringBuilder outStringBuilder = new StringBuilder();

        for (int i = 0; i < count; i++) {
            outStringBuilder.append(getRandomChar());
        }

        String outString = outStringBuilder.toString();
        outTextView.setText(outString);
    };

    View.OnClickListener gridListener = v -> {
        EditText rowsEditText = findViewById(R.id.input_rows);
        EditText columnsEditText = findViewById(R.id.input_columns);
        int rows = Integer.parseInt(rowsEditText.getText().toString());
        int columns = Integer.parseInt(columnsEditText.getText().toString());
        StringBuilder outStringBuilder = new StringBuilder();

        String separator = "\n";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                outStringBuilder.append(getRandomChar());
            }
            outStringBuilder.append(separator);
        }
        String outString = outStringBuilder.toString();
        outTextView.setText(outString);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout presets = findViewById(R.id.presets);
        RadioGroup radioGroup = findViewById(R.id.radio);
        RadioButton countRadioButton = radioGroup.findViewById(R.id.radio_count);
        RadioButton gridRadioButton = radioGroup.findViewById(R.id.radio_grid);
        RelativeLayout countLayout = findViewById(R.id.layout_count);
        LinearLayout gridLayout = findViewById(R.id.layout_grid);
        Button copyButton = findViewById(R.id.copy);
        outTextView = findViewById(R.id.out_text_view);
        textInput = findViewById(R.id.input_text);

        outTextView.setOnLongClickListener(v -> {
            setClipboard(((TextView) v).getText().toString());
            return true;
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            copyButton.setVisibility(View.VISIBLE);
            if (checkedId == countRadioButton.getId()) {
                countLayout.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.GONE);
                copyButton.setOnClickListener(countListener);
            } else if (checkedId == gridRadioButton.getId()) {
                gridLayout.setVisibility(View.VISIBLE);
                countLayout.setVisibility(View.GONE);
                copyButton.setOnClickListener(gridListener);
            }
        });
    }

    private String getRandomChar() {
        wordsList = textInput.getText().toString().split("\\s+");
        Random r = new Random();
        int index = r.nextInt(wordsList.length);
        return wordsList[index];
    }

    private void setClipboard(String s) {
        ClipboardManager clipManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", s);
        clipManager.setPrimaryClip(clipData);
        Toast.makeText(this, "copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}