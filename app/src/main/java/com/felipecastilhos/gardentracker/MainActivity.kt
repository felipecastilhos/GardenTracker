package com.felipecastilhos.gardentracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction.AddNewPlant
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navHostController: NavHostController = rememberNavController()
            MainNavGraph(
                modifier = Modifier.fillMaxSize().safeDrawingPadding(),
                navController = navHostController,
                onNavigate = { navHostController.navigate(it.route) }
            )
        }
    }
}
