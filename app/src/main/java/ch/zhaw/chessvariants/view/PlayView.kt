package ch.zhaw.chessvariants.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PlayMenuScene (navController : NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Row {
        Spacer(Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(2f))

            Text(text = "CHESS\r\n\r\nVariants", fontSize = 40.sp, fontWeight = FontWeight.Medium)

            Spacer(Modifier.weight(3f))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = Color.Gray)
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f)
            ) {
                Spacer(Modifier.weight(4f))

                Button(onClick = { navController.navigate("StandardChessGameScene") },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(80.dp)
                ) {
                    Text(text = "Standard Chess", fontSize = 25.sp)
                }

                Spacer(Modifier.weight(0.5f))

                Button(onClick = { navController.navigate("Chess960GameScene") },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(80.dp)
                ) {
                    Text(text = "Chess960", fontSize = 25.sp)
                }

                Spacer(Modifier.weight(0.5f))

                Button(onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(80.dp)
                ) {
                    Text(text = "🦆 Duck Chess", fontSize = 25.sp)
                }

                Spacer(Modifier.weight(4f))
            }

            Spacer(Modifier.weight(4f))

        }

        Spacer(Modifier.weight(1f))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Nachricht aus Entenhausen.") },
            text = { Text("Die Ente ist noch in Entenhausen.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}