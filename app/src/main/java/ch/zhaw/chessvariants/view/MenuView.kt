package ch.zhaw.chessvariants.view

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StartMenuScene (navController : NavController) {

    var showDialog by remember { mutableStateOf(false) }

    Row {
        Spacer(Modifier.weight(1f))

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val activity = (LocalContext.current as? Activity)

            Spacer(Modifier.weight(2f))

            Text(text = "CHESS\r\n\r\nVariants", fontSize = 40.sp, fontWeight = FontWeight.Medium)

            Spacer(Modifier.weight(3f))

            Button(onClick = { navController.navigate("PlayMenuScene") },
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp)
            ) {
                Text(text = "PLAY", fontSize = 30.sp)
            }

            Spacer(Modifier.weight(0.5f))

            Button(onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(40.dp)
            ) {
                Text(text = "Settings", fontSize = 15.sp)
            }

            Spacer(Modifier.weight(3f))

            Button(
                onClick = {
                    try {
                        activity?.finishAndRemoveTask()
                    } catch (e: Exception) {
                        Log.i("Chess", "Failed to end application")
                    }
                },
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text(text = "QUIT", fontSize = 20.sp)
            }

            Spacer(Modifier.weight(4f))

        }

        Spacer(Modifier.weight(1f))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Settings") },
            text = { Text("Die Settings folgen in der kommenden Version.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

}