package ru.sberbankmobile.drawapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawView drawView = findViewById(R.id.draw_view);
        initButtons(drawView);
    }

    private void initButtons(final DrawView drawView) {
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonPath = findViewById(R.id.button_path_view);
        Button buttonBox = findViewById(R.id.button_box_view);
        Button buttonLine = findViewById(R.id.button_line_view);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.clear();
            }
        });

        buttonPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.selectPathView();
            }
        });

        buttonBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.selectBoxView();
            }
        });

        buttonLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.selectLineView();
            }
        });
    }
}
