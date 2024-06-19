package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.IMCCountTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMCCountTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    IMCCountLayout(
                        
                    )
                }
            }
        }
    }
}

@Composable
fun IMCCountLayout() {
    var heightInput by remember {
        mutableStateOf("")
    }
    var weightInput by remember {
        mutableStateOf("")
    }
    val height = heightInput.toDoubleOrNull() ?: 0.0
    val weight = weightInput.toDoubleOrNull() ?: 0.0
    val imc = calcIMC(height,weight)
    val imcInfo = imcInformation(imc)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.imc_calculator),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )


        EditNumberField(
            label = R.string.height,
            leadingIcon = R.drawable.height,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = heightInput,
            onValueChanged = { heightInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
        )
        EditNumberField(
            label =R.string.Weight,
            leadingIcon = R.drawable.weight,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = weightInput,
            onValueChanged = { weightInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
        )

        Text(
            text = stringResource(R.string.imc),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = "$imc Kg/m^2",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = imcInfo,
            style = MaterialTheme.typography.displaySmall
        )


    }
}

fun imcInformation(imc: String): String {
    val imcNumber = imc.toDouble()
    return when(imcNumber){
        in 0.0..< 18.5 -> "Abaixo do Peso"
        in 18.5..<24.9 -> "Peso Normal"
        in 24.9..<29.9 -> "Exesso de Peso"
        else -> "Obesidade"
    }

}

fun calcIMC(height: Double = 0.0, weight: Double = 0.0): String {
    val heightCorr = height/100
    val imcCalc = weight/(heightCorr*heightCorr)
    return java.text.NumberFormat.getNumberInstance().format(imcCalc)

}




@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}



@Preview(showBackground = true)
@Composable
fun IMCCountPreview() {
    IMCCountTheme {
        IMCCountLayout()
    }
}