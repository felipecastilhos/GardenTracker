package com.felipecastilhos.gardentracker.uikit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipecastilhos.gardentracker.R


@Composable
fun ItemCard(
    itemName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    itemDescription: String? = null,
    enabled: Boolean = true,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = ColorTokens.BackgroundColors.CloudWhite.color,
        contentColor = ColorTokens.AccentColors.BeeBlack.color,
        disabledContentColor = ColorTokens.AccentColors.BeeBlack.color,
        disabledContainerColor = ColorTokens.Disabled.DisabledCharcoalGray.color,
    ),
    actionButtons: List<@Composable () -> Unit>? = null,
    optionButton: @Composable (() -> Unit)? = null,
    itemImage: @Composable (() -> Unit)? = null,
) {
    BaseCard(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                itemImage?.invoke()
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = itemName)
                    itemDescription?.let {
                        Text(text = it)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun BaseCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = ColorTokens.BackgroundColors.CloudWhite.color,
        contentColor = ColorTokens.AccentColors.BeeBlack.color,
        disabledContentColor = ColorTokens.AccentColors.BeeBlack.color,
        disabledContainerColor = ColorTokens.Disabled.DisabledCharcoalGray.color,
    ),
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        enabled = enabled,
    ) {
        content()
    }
}

@Preview
@Composable
fun ItemCardPreview() =
    ItemCard(
        itemName = "Clúsia-Rosa",
        itemDescription = "Clusia rosea",
        onClick = { /*TODO*/ },
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
            contentDescription = ""
        )
    }

@Preview
@Composable
fun BaseCardPreview() =
    BaseCard(
        onClick = {},
        modifier = Modifier.size(450.dp, 150.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Batata")
        }
    }

@Preview
@Composable
fun BaseCardDisabledPreview() =
    BaseCard(
        onClick = {},
        enabled = false, modifier = Modifier.size(450.dp, 150.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Batata")
        }
    }
