package ch.zhaw.chessvariants.view

import android.os.Bundle
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
import ch.zhaw.chessvariants.viewmodel.Chess960ViewModel
import ch.zhaw.chessvariants.viewmodel.ChessViewModel
import ch.zhaw.chessvariants.R
import ch.zhaw.chessvariants.viewmodel.StandardChessViewModel
import ch.zhaw.chessvariants.ui.theme.ChessVariantsTheme

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

    NavHost(navController = navController, startDestination = "StartMenuScene") {
        composable("StartMenuScene") { StartMenuScene(navController = navController) }
        composable("StandardChessGameScene") { GameScene(game = StandardChessViewModel(), navController = navController) }
        composable("Chess960GameScene") { GameScene(game = Chess960ViewModel(), navController = navController) }
    }
}

@Composable
fun GameScene(game: ChessViewModel = viewModel(), navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "${if(game.currentPlayer.value == ChessViewModel.Player.WHITE) "White" else {"Black"}} to Move",
            textAlign = TextAlign.Center,
            fontSize = 30.sp)

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
        ControlButtons(navController = navController, game = game)
    }
}

@Composable
private fun ControlButtons(iconSize: Dp = 40.dp, navController: NavController, game: ChessViewModel){
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
            contentDescription = "Go to Home",
            modifier = Modifier
                .size(iconSize)
                .clickable {

                }
        )
        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go to Home",
            modifier = Modifier
                .size(iconSize)
                .clickable {
                    game.undoMove()
                }
        )
        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Go to Home",
            modifier = Modifier
                .size(iconSize)
                .clickable {

                }
        )
        Spacer(Modifier.weight(1f))
    }
}