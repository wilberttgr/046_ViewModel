package com.example.cobadata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cobadata.Data.DataForm
import com.example.cobadata.Data.DataSource.jenis
import com.example.cobadata.ui.theme.CobaDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CobaDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilanLayout()
                }
            }
        }
    }
}

@Composable
fun TampilanLayout(
    modifier: Modifier =Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
    Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(20.dp)
        ){
            TampilanForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilanForm(cobaViewModel: CobaViewModel = viewModel()){
    var textNama by remember{ mutableStateOf("") }
    var textTlp by remember{ mutableStateOf("") }
    var textEml by remember{ mutableStateOf("") }
    var textAlt by remember{ mutableStateOf("") }

    val context = LocalContext.current
    val dataclass : DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataclass = uiState
    Row {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
        Text(text = "Register", fontSize = 20.sp)
    }


    OutlinedTextField(
        value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Username")},
        onValueChange ={
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            textTlp = it
        }
    )
    OutlinedTextField(
        value = textEml,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email")},
        onValueChange ={
            textEml = it
        }
    )


    Text(text = "Jenis Kelamin")
    SelectJK(options = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = {
            cobaViewModel.setJenisK(it)
        })
    Text(text = "Status")
    SelectJK(options = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = {
            cobaViewModel.setJenisK(it)
        })
    OutlinedTextField(
        value = textAlt,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Alamat")},
        onValueChange ={
            textAlt = it
        }
    )
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.BacaData(textNama,textTlp,textEml,textAlt, dataclass.sex)
        }
    ) {
        Text(
            text =stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    TextHasil(
        namanya = cobaViewModel.namaUSR,
        telponya =cobaViewModel.noTlp ,
        emailnya = cobaViewModel.emailUSR,
        jenisnya = cobaViewModel.jenisKl,
        alamatnya = cobaViewModel.alamat
    )
}

@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}

) {
    var selectedValue by remember { mutableStateOf(" ") }

    Row(modifier = Modifier.padding(16.dp)){
        options.forEach{ item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue ==item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(selected = selectedValue == item, onClick = {
                    selectedValue = item
                    onSelectionChanged(item)
                }
                )
                Text(item)
            }
        }
    }
}

@Composable
fun TextHasil(namanya:String,emailnya:String, telponya:String, jenisnya:String, alamatnya:String){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Telepon : " + telponya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Alamat : " + alamatnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Email : " + emailnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CobaDataTheme {
        TampilanLayout()
    }
}