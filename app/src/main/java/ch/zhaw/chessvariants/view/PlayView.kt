package ch.zhaw.chessvariants.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ch.zhaw.chessvariants.R
import ch.zhaw.chessvariants.viewmodel.StandardChessViewModel

@Composable
fun PlayMenuScene(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val fen = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.weight(2f))

        Text(
            text = "Variants\n\nTo Play",
            fontSize = 40.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        DefaultButton(
            onClick = {
                var fenString =
                    if (fen.value != "") fen.value else StandardChessViewModel.DEFAULT_FEN
                fenString = fenString.replace('/', '-')
                navController.navigate("StandardChessGameScene/${fenString}")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp),
            buttonText = "Standard Chess",
            textSize = 25.sp
        )

        FenInput(fen)

        Spacer(Modifier.weight(1.5f))

        DefaultButton(
            onClick = { navController.navigate("Chess960GameScene") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp),
            buttonText = "Chess960",
            textSize = 25.sp
        )

        Spacer(Modifier.weight(1.5f))

        DefaultButton(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp),
            buttonText = "🦆 Duck Chess",
            textSize = 25.sp
        )

        Spacer(Modifier.weight(2f))

        }

    ShowDialog(
        showDialog = showDialog,
        "Nachricht aus Entenhausen.",
        "Die Ente ist noch in Entenhausen."
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FenInput(fen: MutableState<String>) {
    val showInfo = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .background(Color.LightGray)
    ) {
        TextField(
            value = fen.value,
            onValueChange = { fen.value = it },
            label = {
                Text("FEN for Standard Chess")
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                textColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.LightGray)
        )
        Image(
            painterResource(id = R.drawable.baseline_help_outline_24),
            contentDescription = "help-icon",
            modifier = Modifier
                .clickable {
                    showInfo.value = true
                }
                .fillMaxWidth()
        )

        ShowDialog(
            showDialog = showInfo,
            title = "What is FEN?",
            text = "FEN (Forsyth-Edwards Notation) is a standardized alphanumeric notation used to represent the complete state of a chess game."
        )
    }
}