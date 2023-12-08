package ch.zhaw.chessvariants.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coordinates.Coordinate2D
import endconditions.Outcome
import gameTypes.chess.AbstractChess2D
import java.lang.StringBuilder

abstract class ChessViewModel : ViewModel() {
    enum class Player {
        WHITE, BLACK
    }

    enum class GameState(val state: String) {
        WHITE("White"),
        BLACK("Black"),
        DRAW("Draw"),
        ONGOING("");
    }

    lateinit var chess: AbstractChess2D

    val chessBoard = Array(8) { Array(8) { mutableStateOf(' ') } }
    val allowedToMoveTo = Array(8) { Array(8) { mutableStateOf(false) } }
    var currentPlayer = mutableStateOf(Player.WHITE)
    var gameState = mutableStateOf(GameState.ONGOING)
    var fenString = mutableStateOf("")


    /**
     * If a piece is selected -> It is candidate
     * Position of (-1,-1) means, no candidate is set
     * */
    private var candidate = mutableStateOf(intArrayOf(-1, -1))

    fun hasCandidate(): Boolean {
        return candidate.value[0] != -1 && candidate.value[1] != -1
    }

    fun isCandidate(row: Int, col: Int): Boolean {
        return candidate.value.contentEquals(intArrayOf(row, col))
    }

    fun toggleCandidate(row: Int, col: Int) {
        if (candidate.value.contentEquals(intArrayOf(row, col))) {
            clearCandidate()
        } else {
            this.candidate.value = intArrayOf(row, col)
            setPossibleMoves(row, col)
        }
    }

    fun clearCandidate() {
        this.candidate.value = intArrayOf(-1, -1)
        for (values in allowedToMoveTo) {
            for (value in values) {
                value.value = false
            }
        }
    }

    fun moveCandidateTo(row: Int, col: Int) {
        if (hasCandidate() && !isCandidate(row, col)) {
            val moves = chess.getValidMoves()
            val validMoves = moves
                .filter { move ->
                    Coordinate2D(
                        candidate.value[1],
                        candidate.value[0]
                    ).equals(move.displayFrom)
                }
                .filter { move -> Coordinate2D(col, row).equals(move.displayTo) }
            if (validMoves.isEmpty()) {
                clearCandidate()
                return
            }
            chess.playerMakeMove(validMoves[0])
            updateStateFromBackend()
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

    fun undoMove() {
        gameState.value = GameState.ONGOING

        val prevBoard = chess.board.getBoardState()
        chess.undoMove()
        if (chess.board.getBoardState() != prevBoard) {
            chess.prevPlayer()
        }
        updateStateFromBackend()
    }

    private fun updateBoardFromBackend() {
        for (row in chessBoard.indices) {
            for (col in chessBoard.indices) {
                val piece = chess.board.getPiece(Coordinate2D(col, row))

                val isBlack = (piece?.player ?: chess.players[0]) != chess.players[0]
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

    protected fun updateStateFromBackend() {
        updateBoardFromBackend()
        clearCandidate()
        updateFen()

        if (chess.isOver()) {
            if (chess.getOutcome() is Outcome.Win) {
                gameState.value =
                    if (currentPlayer.value == Player.WHITE) GameState.WHITE else GameState.BLACK
            } else {
                gameState.value = GameState.DRAW
            }
            return
        }

        updatePlayer()
    }

    private fun updateFen() {
        fenString.value = generateFen()
    }

    private fun generateFen(): String {
        val fenBuilder = StringBuilder()
        for (row in chessBoard.indices.reversed()) {
            var emptySpaces = 0
            for (field in chessBoard[row].indices) {
                val piece = chessBoard[row][field].value
                if (piece == ' ') {
                    emptySpaces++
                } else {
                    if (emptySpaces != 0){
                        fenBuilder.append(emptySpaces)
                        emptySpaces = 0
                    }
                    fenBuilder.append(piece)
                }
            }
            if (emptySpaces != 0){
                fenBuilder.append(emptySpaces)
            }
            if(row != 0) {
                fenBuilder.append("/")
            }
        }
        return fenBuilder.toString()
    }

    private fun updatePlayer() {
        currentPlayer.value = getCurrentPlayer()
    }

    private fun getCurrentPlayer(): Player {
        return if (chess.getCurrentPlayer() == chess.players[0]) {
            Player.WHITE
        } else {
            Player.BLACK
        }
    }

    private fun setPossibleMoves(row: Int, col: Int) {

        val moves = chess.getValidMoves()
        val validMoves = moves
            .filter { move -> Coordinate2D(col, row).equals(move.displayFrom) }
            .map { move -> move.displayTo }

        for (i in allowedToMoveTo.indices) {
            for (j in allowedToMoveTo.indices) {
                allowedToMoveTo[i][j].value = validMoves.contains(
                    Coordinate2D(j, i)
                )
            }
        }
    }
}