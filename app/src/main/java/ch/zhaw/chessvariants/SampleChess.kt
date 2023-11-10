package ch.zhaw.chessvariants

import boards.Board2D
import gameTypes.chess.AbstractChess2D
import utils.FenUtility

class SampleChess() : AbstractChess2D(
) {
    private val fen = FenUtility(
        "r1bqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBN1",
        whiteStartingRow = 0,
        whitePromotionRow = 7,
        blackStartingRow = 7,
        blackPromotionRow = 0
    )

    private val b = Board2D(8,8)
    override val board: Board2D
        get() = b
    override val name: String
        get() = "MyChess"

    override fun initBoard() {
        initGame()
    }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        fen.initBoardWithFEN(board, player1, player2)
    }
}