package ch.zhaw.chessvariants.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.zhaw.chessvariants.viewmodel.ChessViewModel
import ch.zhaw.chessvariants.R

@Composable
fun ChessBoard(game: ChessViewModel = viewModel(), modifier: Modifier = Modifier) {
    val size = 8
    Box(
        modifier
            .aspectRatio(1f)
    ) {
        Column {
            for (row in size - 1 downTo 0) {
                Row {
                    for (col in 0 until size) {
                        ChessField(row, col, game, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ChessField(
    row: Int,
    col: Int,
    game: ChessViewModel,
    modifier: Modifier = Modifier
) {
    val colorLight = Color(0xFF888888)
    val colorDark = Color(0xFF555555)
    val fieldColor: Color = if ((row + col) % 2 == 0) colorLight else colorDark
    val allowedToMoveTo: Boolean = game.allowedToMoveTo[row][col].value
    val borderColor = if (game.isCandidate(row, col)) {
        Color.Blue
    } else if (allowedToMoveTo) {
        Color.Green
    } else {
        Color.Transparent
    }

    Box(
        modifier
            .clickable {
                if (game.gameState.value != ChessViewModel.GameState.ONGOING) {
                    return@clickable
                }
                if (game.hasCandidate() && allowedToMoveTo) {
                    game.moveCandidateTo(row, col)
                    return@clickable
                }
                if (game.isOwnPiece(row, col)) {
                    game.toggleCandidate(row, col)
                } else {
                    game.clearCandidate()
                }
            }
            .background(fieldColor)
            .aspectRatio(1f)
            .border(1.dp, borderColor)
    ) {
        val imageIndex = getChessPieceImage(game = game, row = row, col = col)
        if (imageIndex != null) {
            Image(
                painter = painterResource(id = imageIndex),
                contentDescription = "none",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun getChessPieceImage(game: ChessViewModel, row: Int, col: Int): Int? {
    return when (game.chessBoard[row][col].value) {
        'k' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.b_king
            } else {
                R.drawable.b_king_r
            }
        }

        'K' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.w_king
            } else {
                R.drawable.w_king_r
            }
        }

        'q' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.b_queen
            } else {
                R.drawable.b_queen_r
            }
        }

        'Q' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.w_queen
            } else {
                R.drawable.w_queen_r
            }
        }

        'r' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.b_rook
            } else {
                R.drawable.b_rook_r
            }
        }

        'R' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.w_rook
            } else {
                R.drawable.w_rook_r
            }
        }

        'n' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.b_knight
            } else {
                R.drawable.b_knight_r
            }
        }

        'N' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.w_knight
            } else {
                R.drawable.w_knight_r
            }
        }

        'b' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.b_bishop
            } else {
                R.drawable.b_bishop_r
            }
        }

        'B' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.w_bishop
            } else {
                R.drawable.w_bishop_r
            }
        }

        'p' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.b_pawn
            } else {
                R.drawable.b_pawn_r
            }
        }

        'P' -> {
            if (game.currentPlayer.value == ChessViewModel.Player.WHITE) {
                R.drawable.w_pawn
            } else {
                R.drawable.w_pawn_r
            }
        }

        ' ' -> null
        else -> null
    }
}
