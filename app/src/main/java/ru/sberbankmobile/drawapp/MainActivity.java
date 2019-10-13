package ru.sberbankmobile.drawapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentColor = getResources().getColor(R.color.colorAccent);
        final DrawView drawView = findViewById(R.id.draw_view);
        initButtons(drawView);
    }

    private void initButtons(final DrawView drawView) {
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonPath = findViewById(R.id.button_path_view);
        Button buttonBox = findViewById(R.id.button_box_view);
        Button buttonLine = findViewById(R.id.button_line_view);
        Button buttonColor = findViewById(R.id.button_set_color);

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

        buttonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog(drawView);
            }
        });
    }

    private void showColorPickerDialog(final DrawView drawView) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(currentColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.onColorSelected) + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        drawView.setColor(selectedColor);
                        currentColor = selectedColor;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
