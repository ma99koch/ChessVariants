package ch.zhaw.chessvariants

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coordinates.Coordinate2D

class ChessViewModel : ViewModel() {
    enum class Player {
        WHITE, BLACK
    }

    private var sampleChess: SampleChess

    val chessBoard = Array(8) { Array(8) { mutableStateOf(' ') } }
    val allowedToMoveTo = Array(8) { Array(8) { mutableStateOf(false) } }
    var currentPlayer = mutableStateOf(Player.WHITE)

    //    val FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    val FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq"


    /**
     * If a piece is selected -> It is candidate
     * Position of (-1,-1) means, no candidate is set
     * */
    private var candidate = mutableStateOf(intArrayOf(-1, -1))

    init {
        sampleChess = SampleChess()
        sampleChess.initGame(FEN)
        updateStateFromBackend()
    }

    fun hasCandidate(): Boolean {
        return candidate.value[0] != -1 && candidate.value[1] != -1
    }

    fun isCandidate(row: Int, col: Int): Boolean {
        return candidate.value.contentEquals(intArrayOf(row, col))
    }

    fun toggleCandidate(row: Int, col: Int) {
        if (candidate.value.contentEquals(intArrayOf(row, col))){
            clearCandidate()
        } else {
            this.candidate.value = intArrayOf(row, col)
            setPossibleMoves(row, col)
        }
    }

    private fun updateBoardFromBackend () {
        for (row in chessBoard.indices) {
            for (col in chessBoard.indices) {
                val piece = sampleChess.board.getPiece(Coordinate2D(col, row))

                val isBlack = (piece?.player ?: sampleChess.players[0]) != sampleChess.players[0]
                val charSmall = piece
                    ?.getSymbol()
                    ?.toCharArray()?.get(0) ?: ' '
                chessBoard[row][col].value = if (isBlack) {
                    charSmall.lowercaseChar()
                } else {
                    charSmall
                }
            }
        }
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
            val moves = sampleChess.getValidMoves()
            val validMoves = moves
                .filter { move -> Coordinate2D(candidate.value[1], candidate.value[0]).equals(move.displayFrom) }
                .filter { move -> Coordinate2D(col, row).equals(move.displayTo) }
            if (validMoves.isEmpty()){
                clearCandidate()
                return
            }
            sampleChess.playerMakeMove(validMoves[0])
            updateStateFromBackend()
            clearCandidate()
        }
    }

    fun isOwnPiece(row: Int, col: Int): Boolean {
        val blackPieces = setOf('k', 'q', 'r', 'n', 'b', 'p')
        val whitePieces = setOf('K', 'Q', 'R', 'N', 'B', 'P')
        return if (currentPlayer.value == Player.BLACK) {
            blackPieces.contains(chessBoard[row][col].value)
        } else {
            whitePieces.contains(chessBoard[row][col].value)
        }
    }

    private fun updateStateFromBackend () {
        updateBoardFromBackend()
        updatePlayer()
    }

    private fun updatePlayer() {
        currentPlayer.value = if (sampleChess.getCurrentPlayer() == sampleChess.players[0]) {
            Player.WHITE
        } else {
            Player.BLACK
        }
    }

    private fun setPossibleMoves(row: Int, col: Int) {

        val moves = sampleChess.getValidMoves()
        val validMoves = moves
            .filter { move -> Coordinate2D(col, row).equals(move.displayFrom) }
            .map { move -> move.displayTo }
        Log.i("Chess", validMoves.toString())

        for (i in allowedToMoveTo.indices) {
            for (j in allowedToMoveTo.indices) {
                allowedToMoveTo[i][j].value = validMoves.contains(
                    Coordinate2D(j, i)
                )
            }
        }
    }
}