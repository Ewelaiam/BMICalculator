package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightInput: EditText = findViewById(R.id.weightInput)
        val heightInput: EditText = findViewById(R.id.heightInput)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val bmiText: TextView = findViewById(R.id.bmiText)
        val diagnosisText: TextView = findViewById(R.id.diagnosisText)
        val imageView: ImageView = findViewById(R.id.resultImage)


        calculateButton.setOnClickListener {
            val weightText = weightInput.text.toString()
            val heightText = heightInput.text.toString()

            if (weightText.isEmpty()) {
                bmiText.text = "Please enter the weight."
                return@setOnClickListener
            }

            if (heightText.isEmpty()) {
                bmiText.text = "Please enter the height."
                return@setOnClickListener
            }

            val weight = weightText.toDoubleOrNull()
            val height = heightText.toDoubleOrNull()

            if (weight == null || height == null || height == 0.0) {
                bmiText.text = "Invalid input. Please enter positive numeric values."
                return@setOnClickListener
            }

            if (height > 3.0){
                bmiText.text = "Invalid input. Please enter height in M."
                return@setOnClickListener
            }

            val bmi = weight / (height * height)

            bmiText.text = "BMI: %.2f".format(bmi)

            val diagnosis = when {
                    bmi < 18.5 -> {
                        diagnosisText.setTextColor(ContextCompat.getColor(this, R.color.blue))
                        "Underweight"
                    }
                    bmi in 18.5..24.9 -> {
                        diagnosisText.setTextColor(ContextCompat.getColor(this, R.color.green))
                        "Healthy"
                    }
                    bmi in 25.0..29.9 -> {
                        diagnosisText.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                        "Overweight"
                    }
                    bmi in 30.0..34.9 -> {
                        diagnosisText.setTextColor(ContextCompat.getColor(this, R.color.orange))
                        "Obesity"
                    }
                    else -> {
                        diagnosisText.setTextColor(ContextCompat.getColor(this, R.color.red))
                        "Severe Obesity"
                    }
            }

            diagnosisText.text = "Diagnosis: %s".format(diagnosis)

            when (diagnosis) {
                "Underweight" -> imageView.setImageResource(R.drawable.underweight)
                "Healthy" -> imageView.setImageResource(R.drawable.healthy)
                "Overweight" -> imageView.setImageResource(R.drawable.overweight)
                "Obesity" -> imageView.setImageResource(R.drawable.obesity)
                else -> imageView.setImageResource(R.drawable.severe_obesity)
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        Greeting("Android")
    }
}