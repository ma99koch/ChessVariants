package ch.zhaw.chessvariants.backend

import boards.Board2D
import gameTypes.chess.AbstractChess2D
import rules.Enpassant
import rules.StandardCastling
import utils.FenUtility

class StandardChess : AbstractChess2D(
    listOf(Enpassant(), StandardCastling()),
    listOf()
) {

    private val fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"

    private fun createFen(): FenUtility {
        return FenUtility(
            fenString,
            whiteStartingRow = 1,
            whitePromotionRow = 7,
            blackStartingRow = 6,
            blackPromotionRow = 0
        )
    }

    private val b = Board2D(8, 8)
    override val board: Board2D
        get() = b
    override val name: String
        get() = "MyChess"

    override fun initBoard() {
        initGame()
    }

    override fun initGame() {
        val playerWhite = players[0]
        val playerBlack = players[1]
        createFen().initBoardWithFEN(board, playerWhite, playerBlack)
    }
}