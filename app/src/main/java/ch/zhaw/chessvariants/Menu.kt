package ch.zhaw.chessvariants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun StartMenuScene (navController : NavController) {

    Column {

        Spacer(modifier = Modifier.weight(3f))

        Button(onClick = { navController.navigate("GameScene") }) {
            Text(text = "Play")
        }

        Spacer(Modifier.weight(1f))

        Button(onClick = { /* TODO close App */ }) {
            Text(text = "Quit")
        }

        Spacer(Modifier.weight(3f))

    }

}