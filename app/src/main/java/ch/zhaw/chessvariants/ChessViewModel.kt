package ch.zhaw.chessvariants

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ChessViewModel : ViewModel() {
    enum class Player {
        WHITE, BLACK
    }

    val chessBoard = Array(8) { Array(8) { mutableStateOf(' ') } }
    val allowedToMoveTo = Array(8) { Array(8) { mutableStateOf(false) } }
    var currentPlayer = mutableStateOf(Player.WHITE)

    val FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR sd d"


    /**
     * If a piece is selected -> It is candidate
     * Position of (-1,-1) means, no candidate is set
     * */
    private var candidate = mutableStateOf(intArrayOf(-1, -1))

    init {
        initializeBoard()
    }

    private fun initializeBoard() {
        val sb = StringBuilder()
        for (char in FEN) {
            if(char == ' '){
                break
            }
            if(char == '/'){
                continue
            }
            if (char.isDigit()) {
                for (i in 0 until char.digitToInt()) {
                    sb.append(' ')
                }
                continue
            }
            sb.append(char)
        }
        Log.d("ChessLog", "Following is the String: $sb")
        for (row in chessBoard.indices){
            for (col in chessBoard.indices){
                chessBoard[row][col].value = sb[row * chessBoard.size + col]
            }
        }
    }

    fun hasCandidate(): Boolean {
        return candidate.value[0] != -1 && candidate.value[1] != -1
    }

    fun isCandidate(row: Int, col: Int): Boolean {
        return candidate.value.contentEquals(intArrayOf(row, col))
    }

    fun setCandidate(row: Int, col: Int) {
        this.candidate.value = intArrayOf(row, col)
        setPossibleMoves(row, col)
    }

    fun clearCandidate() {
        this.candidate.value = intArrayOf(-1, -1)
        for (values in allowedToMoveTo) {
            for (value in values) {
                value.value = false;
            }
        }
    }

    fun moveCandidateTo(row: Int, col: Int) {
        if (hasCandidate() && !isCandidate(row, col)) {
            chessBoard[row][col].value = chessBoard[candidate.value[0]][candidate.value[1]].value
            chessBoard[candidate.value[0]][candidate.value[1]].value = ' '
            clearCandidate()
            nextPlayer()
        }
    }

    fun isOwnPiece(row: Int, col: Int): Boolean {
        var isOwn = false
        val blackPieces = setOf('k', 'q', 'r', 'n', 'b', 'p')
        val whitePieces = setOf('K', 'Q', 'R', 'N', 'B', 'P')
        return if (currentPlayer.value == Player.BLACK) {
            blackPieces.contains(chessBoard[row][col].value)
        } else {
            whitePieces.contains(chessBoard[row][col].value)
        }
    }

    private fun nextPlayer() {
        currentPlayer.value = if (currentPlayer.value == Player.WHITE) {
            Player.BLACK
        } else {
            Player.WHITE
        }
    }

    private fun setPossibleMoves(row: Int, col: Int) {
        val allowedMoves = getPossibleMoves(row, col)
        if (allowedMoves != null) {
            for (i in allowedMoves.indices) {
                for (j in allowedMoves.indices) {
                    allowedToMoveTo[i][j].value = allowedMoves[i][j]
                }
            }
        }
    }

    private fun getPossibleMoves(row: Int, col: Int): Array<Array<Boolean>>? {
        return when (chessBoard[row][col].value) {
            'r', 'R' -> getRookMoves(row, col)
            'b', 'B' -> getBishopMoves(row, col)
            else -> null
        }
    }

    /**
     * Dummy implementation, to be done by backend
     */
    private fun getRookMoves(row: Int, col: Int): Array<Array<Boolean>> {
        val allowedMoves = Array(8) { Array(8) { false } }
        for (i in allowedToMoveTo.indices) {
            for (j in allowedToMoveTo.indices) {
                if ((i == row && j == col)) {
                    continue
                }
                if ((i == row || j == col)) {
                    allowedMoves[i][j] = true
                }
            }
        }
        return allowedMoves
    }

    /**
     * Dummy implementation, to be done by backend
     */
    private fun getBishopMoves(row: Int, col: Int): Array<Array<Boolean>> {
        val allowedMoves = Array(8) { Array(8) { false } }
        for (i in allowedToMoveTo.indices) {
            for (j in allowedToMoveTo.indices) {
                if ((i == row && j == col)) {
                    continue
                }
                if (i - j == row - col || i + j == col + row) {
                    allowedMoves[i][j] = true
                }
            }
        }
        return allowedMoves
    }

}