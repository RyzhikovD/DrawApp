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

import ru.sberbankmobile.drawapp.views.DrawView;

public class MainActivity extends AppCompatActivity {

    private int mCurrentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawView drawView = findViewById(R.id.draw_view);

        mCurrentColor = getResources().getColor(R.color.colorAccent);
        drawView.setColor(mCurrentColor);

        initButtons(drawView);
    }

    private void initButtons(final DrawView drawView) {
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonPath = findViewById(R.id.button_path_view);
        Button buttonBox = findViewById(R.id.button_box_view);
        Button buttonLine = findViewById(R.id.button_line_view);
        final Button buttonColor = findViewById(R.id.button_set_color);

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
                showColorPickerDialog(drawView, buttonColor);
            }
        });
    }

    private void showColorPickerDialog(final DrawView drawView, final Button button) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle(getResources().getString(R.string.choose_color))
                .initialColor(mCurrentColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        String color = getResources().getString(R.string.onColorSelected) + Integer.toHexString(selectedColor);
                        Toast.makeText(getApplicationContext(), color, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        drawView.setColor(selectedColor);
                        button.setTextColor(selectedColor);
                        mCurrentColor = selectedColor;
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
