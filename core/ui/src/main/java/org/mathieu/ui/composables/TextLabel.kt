package org.mathieu.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * TextLabel composable
 * Display a label and a text with an underline
 *
 * @param label : String
 * @param text : String
 */
@Composable
fun TextLabel(label : String ,text: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx() // Thickness of the underline
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            },
    ) {
        Text(
            text = "$label :",
            modifier = Modifier
                .padding(8.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Left,
            )
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Left,
        )
    }


}

@Preview
@Composable
fun TextLabelPreview() {
    TextLabel("Label", "Text")
}