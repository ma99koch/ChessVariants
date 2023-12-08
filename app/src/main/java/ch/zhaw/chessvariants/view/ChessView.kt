package ch.zhaw.chessvariants.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ch.zhaw.chessvariants.R
import ch.zhaw.chessvariants.viewmodel.ChessViewModel

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
        ControlButtons(navController = navController, game = game, showSettingDialog = showSettingDialog)
        
        Spacer(Modifier.weight(1f))
        
        // Draw FEN-State

        FenField(game)

        Spacer(Modifier.weight(2f))

    }

    // Draw Pop-Ups
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
    showSettingDialog: MutableState<Boolean>
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
                    showSettingDialog.value = true
                }
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun FenField(game: ChessViewModel) {
    // Context for the clipboard manager
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row (
        modifier = Modifier.fillMaxWidth(0.95f),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(Modifier.weight(0.5f))

        Box (
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth() // Change to .width(350.dp) if a white border is required
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                    contentDescription = "Copy FEN",
                    Modifier.clickable { clipboardManager.setText(AnnotatedString(game.fenString.value)) }
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = game.fenString.value,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }

        Spacer(Modifier.weight(0.5f))
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