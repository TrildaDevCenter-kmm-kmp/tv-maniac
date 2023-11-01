package com.thomaskioko.tvmaniac.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thomaskioko.tvmaniac.compose.theme.TvManiacTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TvManiacChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = true,
    enabled: Boolean = true,
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                )
            }
        },
        enabled = enabled,
        leadingIcon = null,
        border = null,
        shape = RoundedCornerShape(4.dp),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = when {
                selected -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)
                else -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
            },
            selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f),
            selectedLabelColor = MaterialTheme.colorScheme.secondary,
        ),
    )
}

@ThemePreviews
@Composable
fun ChipItemSelectedPreview() {
    TvManiacTheme {
        Surface {
            TvManiacChip(
                selected = true,
                text = "Season 1",
                onClick = {},
            )
        }
    }
}
