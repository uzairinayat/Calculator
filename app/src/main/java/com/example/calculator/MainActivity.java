package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private StringBuilder operand = new StringBuilder();
    private boolean isResultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        tvDisplay = findViewById(R.id.tvDisplay);

        // Set OnClickListener for number buttons
        findViewById(R.id.btn0).setOnClickListener(v -> onNumberClick("0"));
        findViewById(R.id.btn1).setOnClickListener(v -> onNumberClick("1"));
        findViewById(R.id.btn2).setOnClickListener(v -> onNumberClick("2"));
        findViewById(R.id.btn3).setOnClickListener(v -> onNumberClick("3"));
        findViewById(R.id.btn4).setOnClickListener(v -> onNumberClick("4"));
        findViewById(R.id.btn5).setOnClickListener(v -> onNumberClick("5"));
        findViewById(R.id.btn6).setOnClickListener(v -> onNumberClick("6"));
        findViewById(R.id.btn7).setOnClickListener(v -> onNumberClick("7"));
        findViewById(R.id.btn8).setOnClickListener(v -> onNumberClick("8"));
        findViewById(R.id.btn9).setOnClickListener(v -> onNumberClick("9"));
        findViewById(R.id.btnDecimal).setOnClickListener(v -> onDecimalClick());

        // Set OnClickListener for operator buttons
        findViewById(R.id.btnAdd).setOnClickListener(v -> onOperatorClick("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> onOperatorClick("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> onOperatorClick("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> onOperatorClick("/"));

        // Set OnClickListener for equals button
        findViewById(R.id.btnEquals).setOnClickListener(v -> onEqualsClick());

        // Set OnClickListener for Clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> clearDisplay());

        // Set OnClickListener for back button
        findViewById(R.id.btnBack).setOnClickListener(v -> onBackClick());
    }

    // Method to handle number button clicks
    private void onNumberClick(String number) {
        if (isResultShown) {
            clearDisplay();
            isResultShown = false;
        }
        operand.append(number);
        updateDisplayAndResult();
    }

    // Method to handle decimal button click
    private void onDecimalClick() {
        if (isResultShown) {
            clearDisplay();
            isResultShown = false;
        }
        if (operand.length() == 0 || operand.charAt(operand.length() - 1) == ' ') {
            operand.append("0.");
        } else if (!operand.toString().contains(".")) {
            operand.append(".");
        }
        updateDisplayAndResult();
    }

    // Method to handle operator button clicks
    private void onOperatorClick(String op) {
        if (operand.length() != 0 && operand.charAt(operand.length() - 1) != ' ') {
            operand.append(" ").append(op).append(" ");
            tvDisplay.setText(operand.toString());
        }
    }

    // Method to handle equals button click
    private void onEqualsClick() {
        if (operand.length() != 0 && operand.toString().contains(" ")) {
            double result = evaluateExpression(operand.toString());
            operand.setLength(0);
            operand.append(result);
            tvDisplay.setText(operand.toString());
            isResultShown = true;
        }
    }

    // Method to evaluate the expression
    private double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double num = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += num;
                    break;
                case "-":
                    result -= num;
                    break;
                case "*":
                    result *= num;
                    break;
                case "/":
                    if (num != 0) {
                        result /= num;
                    } else {
                        return Double.NaN; // Handle division by zero
                    }
                    break;
            }
        }

        return result;
    }

    // Method to handle back button click
    private void onBackClick() {
            operand.deleteCharAt(operand.length() - 1);
            updateDisplayAndResult();
        }


    // Method to clear the display
    private void clearDisplay() {
        operand.setLength(0);
        tvDisplay.setText("0");
    }

    // Method to update display and result dynamically
    private void updateDisplayAndResult() {
        tvDisplay.setText(operand.toString());
        if (operand.toString().contains(" ")) {
            double result = evaluateExpression(operand.toString());
            tvDisplay.setText(operand.toString() + "\n" + result);
        }
    }
}
