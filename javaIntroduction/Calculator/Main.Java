package com.example.cancul;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        setupButtons();
    }

    private void setupButtons() {
        int[] digitButtons = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        for (int i = 0; i < digitButtons.length; i++) {
            final int digit = i;
            findViewById(digitButtons[i]).setOnClickListener(v -> appendNumber(String.valueOf(digit)));
        }

        findViewById(R.id.btn_add).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btn_subtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btn_multiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btn_divide).setOnClickListener(v -> setOperator("/"));

        findViewById(R.id.btn_equals).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btn_clear).setOnClickListener(v -> clearAll());
        findViewById(R.id.btn_decimal).setOnClickListener(v -> addDecimal());
        findViewById(R.id.btn_backspace).setOnClickListener(v -> backspace());
        findViewById(R.id.btn_plus_minus).setOnClickListener(v -> toggleSign());
    }

    private void appendNumber(String number) {
        if (isNewInput) {
            currentInput = number;
            isNewInput = false;
        } else {
            currentInput += number;
        }
        updateDisplay();
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            if (!operator.isEmpty()) {
                calculateResult();
            }
            firstValue = Double.parseDouble(currentInput);
            operator = op;
            isNewInput = true;
        }
    }

    private void calculateResult() {
        if (!operator.isEmpty() && !currentInput.isEmpty()) {
            double secondValue = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    if (secondValue != 0) {
                        result = firstValue / secondValue;
                    } else {
                        display.setText("Error");
                        clearAll();
                        return;
                    }
                    break;
            }

            if (result == (long) result) {
                currentInput = String.valueOf((long) result);
            } else {
                currentInput = String.valueOf(result);
            }

            operator = "";
            isNewInput = true;
            updateDisplay();
        }
    }

    private void clearAll() {
        currentInput = "";
        operator = "";
        firstValue = 0;
        isNewInput = true;
        display.setText("0");
    }

    private void addDecimal() {
        if (isNewInput) {
            currentInput = "0.";
            isNewInput = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        updateDisplay();
    }

    private void backspace() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.isEmpty()) {
                currentInput = "0";
                isNewInput = true;
            }
            updateDisplay();
        }
    }

    private void toggleSign() {
        if (!currentInput.isEmpty() && !currentInput.equals("0")) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
            updateDisplay();
        }
    }

    private void updateDisplay() {
        if (currentInput.isEmpty()) {
            display.setText("0");
        } else {
            display.setText(currentInput);
        }
    }
}