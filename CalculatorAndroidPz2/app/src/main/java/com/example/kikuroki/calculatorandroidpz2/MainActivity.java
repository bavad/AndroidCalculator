package com.example.kikuroki.calculatorandroidpz2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    private int currentResult = 0;
    private char currentOperation = ' ';
    private int currentNumber = 0;
    private int currentNumberIsPositive = 1;
    private boolean curNumberIsNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickNumberBtn(View v)
    {
        Button numberButton = (Button)v;
        TextView calculatorText = findViewById(R.id.calcText);
        int temp = currentNumber;

        try
        {
            int buttonNumber = Integer.parseInt(numberButton.getText().toString());
            temp = currentNumber * 10 + buttonNumber * currentNumberIsPositive;

            if(currentNumberIsPositive == 1 && temp < currentNumber)
            {
               Toast.makeText(this, "The number is very large", Toast.LENGTH_LONG).show();
            }
            else if(currentNumberIsPositive == -1 && temp > currentNumber)
            {
                Toast.makeText(this, "The number is very small", Toast.LENGTH_LONG).show();
            }
            else
            {
                currentNumber = temp;
                calculatorText.setText(Integer.toString(currentNumber));
                curNumberIsNumber = true;
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(this, "The number is very large", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickArithmeticBtn(View v)
    {
        Button operationButton = (Button)v;
        char operation = operationButton.getText().charAt(0);

        if(currentOperation == ' ')
        {
            if(curNumberIsNumber)
            {
                currentOperation = operation;
                currentResult = currentNumber;
                prepareToNextNumber();
            }
            else if(operation == '-')
            {
                TextView calculatorText = findViewById(R.id.calcText);
                if(calculatorText.getText() == "-")
                {
                    calculatorText.setText("");
                }
                else
                {
                    calculatorText.setText("-");
                }

                currentNumberIsPositive *= -1;
            }
        }
        else
        {
            if(curNumberIsNumber)
            {
                try
                {
                    currentResult = execOperation();
                    currentOperation = operationButton.getText().charAt(0);
                    prepareToNextNumber();
                }
                catch (Exception ex)
                {
                    if (currentNumber == 0)
                    {
                        Toast.makeText(this, "You can't divide by 0", Toast.LENGTH_LONG).show();
                        currentOperation = operationButton.getText().charAt(0);
                        prepareToNextNumber();
                    }
                    else
                    {
                        Toast.makeText(this, "The number is very large", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else if(operation == '-')
            {
                TextView calculatorText = findViewById(R.id.calcText);
                if(calculatorText.getText() == "-")
                {
                    calculatorText.setText("");
                }
                else
                {
                    calculatorText.setText("-");
                }

                currentNumberIsPositive *= -1;
            }
        }
    }

    public void onClickEqualsBtn(View v)
    {
        if(currentOperation != ' ' && curNumberIsNumber)
        {
            try
            {
                currentNumber = execOperation();
                TextView сalculatorText = findViewById(R.id.calcText);
                сalculatorText.setText(Integer.toString(currentNumber));
                if(currentNumber < 0){
                    currentNumberIsPositive = -1;
                }
                else
                {
                    currentNumberIsPositive = 1;
                }

                currentResult = 0;
                currentOperation = ' ';
            }
            catch (Exception ex)
            {
                if (currentNumber == 0)
                {
                    Toast.makeText(this, "You can't divide by 0", Toast.LENGTH_LONG).show();
                    TextView сalculatorText = findViewById(R.id.calcText);
                    сalculatorText.setText(Integer.toString(currentNumber));
                    currentResult = 0;
                    currentOperation = ' ';
                }
                else
                {
                    Toast.makeText(this, "The number is very large", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void OnClickClearBtn(View v)
    {
        prepareToNextNumber();
        currentOperation = ' ';
        currentResult = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("currentResult", currentResult);
        savedInstanceState.putChar("currentOperation",currentOperation);
        savedInstanceState.putInt("currentNumber", currentNumber);
        savedInstanceState.putInt("currentNumberIsPositive", currentNumberIsPositive);
        savedInstanceState.putBoolean("currentNumberIsNumber",curNumberIsNumber);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        currentResult = savedInstanceState.getInt("currentResult");
        currentOperation = savedInstanceState.getChar("currentOperation");
        currentNumber = savedInstanceState.getInt("currentNumber");
        currentNumberIsPositive = savedInstanceState.getInt("currentNumberIsPositive");
        curNumberIsNumber = savedInstanceState.getBoolean("currentNumberIsNumber");
        TextView сalculatorText = findViewById(R.id.calcText);
        if(curNumberIsNumber)
        {
            сalculatorText.setText(Integer.toString(currentNumber));
        }
        else if (currentNumberIsPositive == -1)
        {
            сalculatorText.setText("-");
        }
    }

    private void prepareToNextNumber()
    {
        currentNumber = 0;
        currentNumberIsPositive = 1;
        curNumberIsNumber = false;
        TextView calculatorText = findViewById(R.id.calcText);
        calculatorText.setText("");
    }

    private int execOperation()
    {
        if(currentOperation == '+')
        {
            return currentResult + currentNumber;
        }
        if(currentOperation == '-')
        {
            return currentResult - currentNumber;
        }
        if(currentOperation == '*')
        {
            return currentResult * currentNumber;
        }
        if(currentOperation == '/')
        {
            return currentResult / currentNumber;
        }

        return currentResult;
    }
}
