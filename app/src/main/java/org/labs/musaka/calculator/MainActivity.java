package org.labs.musaka.calculator;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//to do's
//calkMe(View view) this should enter the inputs at the position of the cursor


public class MainActivity extends AppCompatActivity {
    private EditText calcDisplay;
    private StringBuilder calcText;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcDisplay = findViewById(R.id.calc_display);
        calcDisplay.setShowSoftInputOnFocus(false);
        calcText = new StringBuilder("");
    }

    public void calkMe(View view) {
        //this should enter the inputs at the position of the cursor
        int cursorPos = calcDisplay.getSelectionStart();
        Button button = (Button) view;
        if (calcText.equals("0")) {
            calcText.replace(0, calcText.length(), "");
        }
        calcText = calcText.insert(cursorPos, button.getText());
        calcDisplay.setText(calcText);
        calcDisplay.setSelection(cursorPos + 1);

        //Toast.makeText(this,CalculationEvaluation.result,Toast.LENGTH_LONG).show();
    }



    public void makeCorrection(View view) {
        int cursorPos = calcDisplay.getSelectionStart();
        if (cursorPos > 0 && cursorPos <= calcText.length()) {
            calcText.deleteCharAt(cursorPos - 1);
            calcDisplay.setText(calcText);
            calcDisplay.setSelection(cursorPos - 1);
        } else {
            calcDisplay.setText(calcText);
        }
    }

    public void calcResult(View view) {
        String TempcalcText = CalculationEvaluation.evalStatement(calcText.toString());
        if (TempcalcText.equals(CalculationEvaluation.erroneousCalculation) || TempcalcText.isEmpty()) {
            calcText.replace(0, calcText.length(), "");
            calcDisplay.setText(CalculationEvaluation.erroneousCalculation);
        } else if (TempcalcText.equals("null")) {
            calcText.replace(0, calcText.length(), "");
            calcDisplay.setText(CalculationEvaluation.erroneousCalculation);
        } else if (!TempcalcText.isEmpty()) {
            calcText.replace(0, calcText.length(), TempcalcText);
            calcDisplay.setText(TempcalcText);
        }
        calcDisplay.setSelection(calcText.length());
    }
}
