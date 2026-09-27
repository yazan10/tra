package yaz.phone.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * شريط أقسام أفقي قابل للتمرير (تحت البحث في كل صفحة).
 */
@Composable
fun SectionFilterStrip(
    sections: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(sections) { section ->
            FilterChip(
                selected = selected == section,
                onClick = { onSelect(section) },
                label = { Text(section, style = MaterialTheme.typography.labelMedium) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = accentColor,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}
