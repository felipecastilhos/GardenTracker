package com.felipecastilhos.gardentracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MyGardenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val message = viewModel.viewState.collectAsState().value

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Greetings(message ?: "")
                Spacer(modifier = Modifier.height(15.dp))
                Button(onClick = { viewModel.updateMessage("Updated")}) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Composable
fun Greetings(message: String) = Text(text = message)

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() = Greetings("My Garden")