package ch.zhaw.chessvariants.backend

import boards.Board2D
import endconditions.StandardEndConditions
import gameTypes.chess.AbstractChess2D
import rules.Enpassant
import rules.StandardCastling
import utils.FenUtility

class StandardChess constructor(private val fenString: String) : AbstractChess2D(
    listOf(Enpassant(), StandardCastling()),
    listOf(StandardEndConditions())
) {

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