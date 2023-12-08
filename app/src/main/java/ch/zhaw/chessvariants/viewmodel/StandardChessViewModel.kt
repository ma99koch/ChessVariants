package ch.zhaw.chessvariants.viewmodel

import ch.zhaw.chessvariants.backend.StandardChess

class StandardChessViewModel(fen: String) : ChessViewModel() {

    companion object {
        const val DEFAULT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"
    }
    init {
        chess = StandardChess(fen)
        chess.initBoard()
        updateStateFromBackend()
    }

}