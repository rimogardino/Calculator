package org.labs.musaka.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView calcDisplay;
    private String calcText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcDisplay = (TextView) findViewById(R.id.calc_display);
        calcText = "";
    }

    public void calkMe(View view) {
        Button button = (Button) view;
        if (calcText.equals("0")) {
            calcText = "";
        }

        calcText = calcText + button.getText();
        calcDisplay.setText(calcText);

        //Toast.makeText(this,CalculationEvaluation.result,Toast.LENGTH_LONG).show();
    }



    public void makeCorrection(View view) {
        if (calcText.length() > 1) {
            calcText = calcText.substring(0,calcText.length() - 1);
        }
        else {
            calcText = "0";
        }
        calcDisplay.setText(calcText);

    }

    public void calcResult(View view) {
        String TempcalcText = CalculationEvaluation.evalStatement(calcText);
        if (!TempcalcText.isEmpty()) {
            calcText = TempcalcText;
            calcDisplay.setText(TempcalcText);
        }
        Toast.makeText(this,TempcalcText,Toast.LENGTH_LONG).show();
    }
}
