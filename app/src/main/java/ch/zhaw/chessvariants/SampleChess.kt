package ch.zhaw.chessvariants

import boards.Board2D
import gameTypes.chess.AbstractChess2D
import utils.FenUtility

class SampleChess() : AbstractChess2D(
) {

    private var fenString = "rbbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R"

    private fun createFen(fen: String): FenUtility {
        return FenUtility(
            fen,
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

    fun initGame(fen: String) {
        fenString = fen
        initGame()
    }

    override fun initGame() {
        val playerWhite = players[0]
        val playerBlack = players[1]
        createFen(fenString).initBoardWithFEN(board, playerWhite, playerBlack)
    }
}