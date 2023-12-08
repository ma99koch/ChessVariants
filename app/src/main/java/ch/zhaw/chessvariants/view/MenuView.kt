package ch.zhaw.chessvariants.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.zhaw.chessvariants.ui.theme.ChessVariantsTheme
import ch.zhaw.chessvariants.viewmodel.Chess960ViewModel
import ch.zhaw.chessvariants.viewmodel.StandardChessViewModel

class MenuView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessVariantsTheme {
                Start()
            }
        }
    }
}

@Composable
fun Start() {

    val navController = rememberNavController()
    var standardChessViewModel: StandardChessViewModel? = null
    var chess960ViewModel: Chess960ViewModel? = null

    NavHost(navController = navController, startDestination = "StartMenuScene") {
        composable("StartMenuScene") { StartMenuScene(navController = navController) }
        composable("StandardChessGameScene") {
            standardChessViewModel = standardChessViewModel ?: StandardChessViewModel("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
            GameScene(
                game = standardChessViewModel!!,
                navController = navController
            )
        }
        composable("Chess960GameScene") {
            chess960ViewModel = chess960ViewModel ?: Chess960ViewModel()
            GameScene(
                game = chess960ViewModel!!,
                navController = navController
            )
        }
        composable("PlayMenuScene") {
            PlayMenuScene(navController = navController)
            standardChessViewModel = null
            chess960ViewModel = null
        }
    }
}

@Composable
fun StartMenuScene (navController : NavController) {

    val showDialog = remember { mutableStateOf(false) }

    Row {
        Spacer(Modifier.weight(1f))

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val activity = (LocalContext.current as? Activity)

            Spacer(Modifier.weight(2f))

            Text(text = "CHESS\r\n\r\nVariants", fontSize = 40.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)

            Spacer(Modifier.weight(3f))

            DefaultButton(
                onClick = { navController.navigate("PlayMenuScene") },
                buttonModifier = Modifier.fillMaxWidth(0.8f).height(80.dp),
                buttonText = "PLAY",
                textSize = 30.sp
            )

            Spacer(Modifier.weight(3f))

            DefaultButton(
                onClick = { showDialog.value = true },
                buttonModifier = Modifier.fillMaxWidth(0.8f).height(60.dp),
                buttonText = "SETTINGS",
                textSize = 20.sp
            )

            Spacer(Modifier.weight(0.5f))

            DefaultButton(
                onClick = {
                    try {
                        activity?.finishAndRemoveTask()
                    } catch (e: Exception) {
                        Log.i("Chess", "Failed to end application")
                    }
                },
                buttonModifier = Modifier.fillMaxWidth(0.8f).height(60.dp),
                buttonText = "QUIT",
                textSize = 20.sp
            )

            Spacer(Modifier.weight(4f))

        }

        Spacer(Modifier.weight(1f))
    }

    ShowDialog(showDialog = showDialog, "Settings", "Die Settings folgen in der kommenden Version.")
}

@Composable
fun ShowDialog(showDialog: MutableState<Boolean>, title: String, text: String) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(title) },
            text = { Text(text) },
            confirmButton = {
                DefaultButton(
                    onClick = { showDialog.value = false },
                    buttonText = "OK",
                    textSize = 12.sp
                )
            }
        )
    }
}

@Composable
fun DefaultButton(onClick: () -> Unit, buttonModifier : Modifier = Modifier, buttonText: String, textSize: TextUnit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
        shape = RoundedCornerShape(20),
        modifier = buttonModifier
    ) {
        Text(text = buttonText, fontSize = textSize)
    }
}