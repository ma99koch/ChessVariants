package ch.zhaw.chessvariants.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.zhaw.chessvariants.R
import ch.zhaw.chessvariants.ui.theme.ChessVariantsTheme
import ch.zhaw.chessvariants.viewmodel.Chess960ViewModel
import ch.zhaw.chessvariants.viewmodel.ChessViewModel
import ch.zhaw.chessvariants.viewmodel.StandardChessViewModel

class ChessView : ComponentActivity() {
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
            standardChessViewModel = standardChessViewModel ?: StandardChessViewModel()
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
fun GameScene(game: ChessViewModel = viewModel(), navController: NavController) {

    val showGameEndedDialog = remember { mutableStateOf(false) }
    val showSettingDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = getGameText(game),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )

        // Draw ChessBoard
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(Modifier.width(10.dp))
            ChessBoard(game = game, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(10.dp))
        }

        // Draw Control-Tools
        ControlButtons(navController = navController, game = game, showDialog = showSettingDialog)
    }

    if (game.gameState.value != ChessViewModel.GameState.ONGOING) {
        showGameEndedDialog.value = true
    }
    ShowDialog(
        showDialog = showGameEndedDialog,
        title = getGameText(game),
        text = "The game is over. Thanks for playing!"
    )

    ShowDialog(
        showDialog = showSettingDialog,
        title = "Settings",
        text = "No settings to set"
    )

}

@Composable
private fun ControlButtons(
    iconSize: Dp = 40.dp,
    navController: NavController,
    game: ChessViewModel,
    showDialog: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Go to Home",
            modifier = Modifier
                .size(iconSize)
                .clickable {
                    navController.navigate("StartMenuScene")
                }
        )
        Spacer(Modifier.weight(1f))

        Image(
            painterResource(id = R.drawable.baseline_outlined_flag_24),
            contentDescription = "Give up",
            modifier = Modifier
                .size(iconSize)
                .clickable {
                    if (game.gameState.value != ChessViewModel.GameState.ONGOING) {
                        return@clickable
                    }
                    if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                        game.gameState.value = ChessViewModel.GameState.BLACK
                    } else {
                        game.gameState.value = ChessViewModel.GameState.WHITE
                    }
                }
        )
        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Undo Move",
            modifier = Modifier
                .size(iconSize)
                .clickable {
                    game.undoMove()
                }
        )
        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Go to Setting",
            modifier = Modifier
                .size(iconSize)
                .clickable {
                    showDialog.value = true
                }
        )
        Spacer(Modifier.weight(1f))
    }
}

private fun getGameText(game: ChessViewModel): String {
    return when (game.gameState.value) {
        ChessViewModel.GameState.WHITE, ChessViewModel.GameState.BLACK -> {
            "${game.gameState.value.state} won"
        }

        ChessViewModel.GameState.DRAW -> {
            "Game is draw"
        }

        ChessViewModel.GameState.ONGOING -> {
            "${
                if (game.currentPlayer.value == ChessViewModel.Player.WHITE) "White" else {
                    "Black"
                }
            } to move"
        }
    }
}