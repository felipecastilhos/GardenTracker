package com.felipecastilhos.gardentracker.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.felipecastilhos.gardentracker.R
import com.felipecastilhos.gardentracker.Route

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onNavigate: (Route) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.home))
    }
}

@Composable
fun MyPlantsScreen(modifier: Modifier = Modifier, onNavigate: (Route) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.plants))
    }
}

@Composable
fun ToolsScreen(modifier: Modifier = Modifier, onNavigate: (Route) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.tools))
    }
}