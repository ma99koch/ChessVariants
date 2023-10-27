package ch.zhaw.chessvariants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ChessBoard(modifier: Modifier = Modifier) {
    val size = 8
    Box(
        modifier
            .aspectRatio(1f)) {
        Column {
            for (col in 0 until size) {
                Row {
                    for (row in 0 until size) {
                        ChessField(row, col, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ChessField(row: Int, col: Int, modifier: Modifier = Modifier) {
    val colorLight = Color(0xFF888888)
    val colorDark = Color(0xFF333333)
    val fieldColor: Color = if ((row + col) % 2 == 0) colorLight else colorDark
    Box(
        modifier
            .clickable {
                /*TODO*/
            }
            .background(fieldColor)
            .aspectRatio(1f)
    )
}