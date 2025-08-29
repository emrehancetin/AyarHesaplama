package com.emrehancetin.ayarhesaplama

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emrehancetin.ayarhesaplama.ViewModel.MainViewModel
import com.emrehancetin.ayarhesaplama.ui.theme.AyarHesaplamaTheme
import com.emrehancetin.ayarhesaplama.ui.theme.background
import com.emrehancetin.ayarhesaplama.domain.CalcResult
import com.emrehancetin.ayarhesaplama.domain.calculateHasGrAndTotal
import com.emrehancetin.ayarhesaplama.domain.Constants
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.emrehancetin.ayarhesaplama.ui.theme.Dimens
import com.emrehancetin.ayarhesaplama.ui.theme.AppColors


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AyarHesaplamaTheme {
                val vm: MainViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        MainPage(vm)
                        HesaplaButton(vm)
                    }
                }
            }
        }
    }
}

@Composable
fun MainPage(vm: MainViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = background),
        contentAlignment = Alignment.TopCenter
    ) {


        val takozGrErr = vm.uiState.takozGrError != null
        val takozAyarErr = vm.uiState.takozAyarError != null



        Column (horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = stringResource(R.string.app_name),

                color= Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(30.dp),


                )
            TakozGrRow(
                value = vm.uiState.takozGrInput,
                onValueChange = vm::onTakozGrChange,
                isError = takozGrErr
            )
            TakozAyarRow(
                value = vm.uiState.takozAyarInput,
                onValueChange = vm::onTakozAyarChange,
                isError = takozAyarErr
            )
            HasGrRow(
                value = vm.uiState.hasGr,
                isSuccess = vm.uiState.isHasGrSuccess)
            HasAyarRow()
            TotalGrRow(value = vm.uiState.totalGr)
            TotalAyarRow()
            HiddenSignature()



        }
    }

}

@Composable
fun TakozGrRow(value: String, onValueChange: (String) -> Unit, isError: Boolean){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = stringResource(R.string.label_takoz_gr), modifier = Modifier
            .padding(end = 8.dp)
            .width(Dimens.LabelWidth))
        TextField(
                   value = value,
            placeholder = { Text(stringResource(R.string.ph_takoz_gr)) },
            onValueChange= onValueChange,
            isError = isError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),

           singleLine = true,
            shape = RoundedCornerShape(Dimens.Corner),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppColors.Accent,
                unfocusedContainerColor = AppColors.AccentLight,
                disabledContainerColor = Color(0xFF3F4149),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )


    }
}

@Composable
fun TakozAyarRow(value: String, onValueChange: (String) -> Unit,isError: Boolean){

    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {


        Text(text = stringResource(R.string.label_takoz_ayar), modifier = Modifier
            .padding(end = 8.dp)
            .width(Dimens.LabelWidth))
        TextField(
            value = value,
            placeholder = {  Text(stringResource(R.string.ph_takoz_ayar))},
            onValueChange = onValueChange,
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            shape = RoundedCornerShape(Dimens.Corner),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppColors.Accent,
                unfocusedContainerColor = AppColors.AccentLight,
                disabledContainerColor = Color(0xFF3F4149),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),//
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

    }
}

@Composable
fun HasGrRow(value: Double, isSuccess: Boolean){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = stringResource(R.string.label_has_gr), modifier = Modifier
            .padding(end = 8.dp)
            .width(Dimens.LabelWidth))
        Surface(
            shape = RoundedCornerShape(Dimens.Corner),
            color = if (isSuccess) AppColors.Success else AppColors.Accent,
            contentColor = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = value.toString(),
                modifier = Modifier.padding(12.dp)
            )
        }

    }
}

@Composable
fun HasAyarRow(){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = stringResource(R.string.label_has_ayar), modifier = Modifier
            .padding(end = 8.dp)
            .width(Dimens.LabelWidth))
        Surface(
            shape = RoundedCornerShape(Dimens.Corner),
            color = Color(0xFF4889F5),
            contentColor = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(text = Constants.HAS_AYAR_PERMILLE.toInt().toString(), modifier = Modifier.padding(12.dp))
        }

    }
}

@Composable
fun TotalGrRow(value: Double){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = stringResource(R.string.label_total_gr), modifier = Modifier
            .padding(end = 8.dp)
            .width(Dimens.LabelWidth))
        Surface(
            shape = RoundedCornerShape(Dimens.Corner),
            color = Color(0xFF4889F5),
            contentColor = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = value.toString(),
                modifier = Modifier.padding(12.dp)
            )
        }

    }
}

@Composable
fun TotalAyarRow(){
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = stringResource(R.string.label_total_ayar), modifier = Modifier
            .padding(end = 8.dp)
            .width(Dimens.LabelWidth))
        Surface(
            shape = RoundedCornerShape(Dimens.Corner),
            color = Color(0xFF4889F5),
            contentColor = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(text = Constants.TARGET_AYAR_PERMILLE.toInt().toString(), modifier = Modifier.padding(12.dp))
        }

    }
}


@Composable
fun HesaplaButton(vm: MainViewModel){
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize().padding(
        bottom = Dimens.ScreenPadding
    ).navigationBarsPadding()
        .imePadding(),

        contentAlignment = Alignment.BottomCenter ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Row (Modifier.padding(15.dp)){


                Button(
                    onClick = {
                        val hasError = vm.uiState.takozGrError != null || vm.uiState.takozAyarError != null
                        val inputsBlank = vm.uiState.takozGrInput.isBlank() || vm.uiState.takozAyarInput.isBlank()
                        if (hasError || inputsBlank) {
                            Toast.makeText(context, context.getString(R.string.err_number), Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val takozGr = vm.uiState.takozGrInput.replace(',', '.').toDoubleOrNull()
                        val takozAyarPermille = vm.uiState.takozAyarInput.replace(',', '.').toDoubleOrNull()
                        if (takozGr == null || takozAyarPermille == null) {
                            Toast.makeText(context, context.getString(R.string.err_number), Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        when (val res = calculateHasGrAndTotal(takozGr, takozAyarPermille)) {
                            is CalcResult.Success -> {
                                vm.setResults(res.hasGr, res.totalGr)
                                Toast.makeText(context, "İşlem Başarılı!", Toast.LENGTH_SHORT).show()
                            }
                            is CalcResult.Error -> {
                                vm.resetSuccessFlag()
                                Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(2.dp, Color.Black))
                {
                    Text("HESAPLA",
                        fontSize = 24.sp)
                }
            }
        }

    }

}


@Composable
fun HiddenSignature() {
    val context = LocalContext.current
    val interaction = remember { MutableInteractionSource() }


    Text(
        text = "EC",
        fontSize = 8.sp,
        color = Color.Transparent,
        modifier = Modifier.padding(2.dp)
    )


        Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .combinedClickable(
                interactionSource = interaction,
                indication = null,
                onClick = {},
                onDoubleClick = {
                    Toast.makeText(context, "👨‍💻 Developed By EmrehanCetin", Toast.LENGTH_SHORT).show()
                }
            )
    )
}


@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true, name = "Main Preview")
@Composable
fun GeneralPreview() {
    AyarHesaplamaTheme {
        val vm = MainViewModel().apply {
            onTakozGrChange("1000")
            onTakozAyarChange("916")
        }
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                MainPage(vm)
                HesaplaButton(vm)
            }
        }
    }
}