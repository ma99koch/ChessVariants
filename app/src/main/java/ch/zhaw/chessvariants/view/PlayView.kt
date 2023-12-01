package ch.zhaw.chessvariants.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val showDialog = remember { mutableStateOf(false) }

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

                DefaultButton(
                    onClick = { navController.navigate("StandardChessGameScene") },
                    buttonModifier = Modifier.fillMaxWidth(0.8f).height(80.dp),
                    buttonText = "Standard Chess",
                    textSize = 25.sp
                )

                Spacer(Modifier.weight(0.5f))

                DefaultButton(
                    onClick = { navController.navigate("Chess960GameScene") },
                    buttonModifier = Modifier.fillMaxWidth(0.8f).height(80.dp),
                    buttonText = "Chess960",
                    textSize = 25.sp
                )

                Spacer(Modifier.weight(0.5f))

                DefaultButton(
                    onClick = { showDialog.value = true },
                    buttonModifier = Modifier.fillMaxWidth(0.8f).height(80.dp),
                    buttonText = "🦆 Duck Chess",
                    textSize = 25.sp
                )

                Spacer(Modifier.weight(4f))
            }

            Spacer(Modifier.weight(4f))

        }

        Spacer(Modifier.weight(1f))
    }

    ShowDialog(showDialog = showDialog, "Nachricht aus Entenhausen.", "Die Ente ist noch in Entenhausen.")

}