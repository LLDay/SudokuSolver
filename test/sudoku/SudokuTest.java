package sudoku;

import static org.junit.jupiter.api.Assertions.*;
import static sudoku.SudokuState.MANY_SOLVES;
import static sudoku.SudokuState.SOLVED;
import static sudoku.SudokuState.UNSOLVABLE;

import org.junit.jupiter.api.Test;

class SudokuTest {

    @Test
    void creation() {
        Sudoku sud1 = new Sudoku();
        Sudoku sud2 = new Sudoku(strSud1);
        Sudoku sud3 = new Sudoku(sud2);

        assertEquals(sud1.toString(), strEmpty);
        assertEquals(sud2.toString(), sud3.toString());

        assertThrows(IllegalArgumentException.class, () -> new Sudoku(""));
        assertThrows(IllegalArgumentException.class, () -> new Sudoku(strSud1.replaceAll("1", "a")));
        assertThrows(IllegalArgumentException.class, () -> new Sudoku(strSud1 + "1"));
    }

    @Test
    void solving() {
        Sudoku s1 = new Sudoku(strSud1).getSolve();
        Sudoku s2 = new Sudoku(strSud2).getSolve();
        Sudoku s3 = new Sudoku(strSud3).getSolve();

        assertEquals(s1.getState(), SOLVED);
        assertEquals(s2.getState(), SOLVED);
        assertEquals(s3.getState(), SOLVED);

        assertEquals(s1.toString(), strSud1Answer);
        assertEquals(s2.toString(), strSud2Answer);
        assertEquals(s3.toString(), strSud3Answer);
    }

    @Test
    void manySolves() {
        Sudoku sud1 = new Sudoku(strEmpty).getSolve();
        Sudoku sud2 = new Sudoku(strSud4).getSolve();
		
		/*
		Sud2 has 2 different solutions:
		 
		137482659		137482659
		459671328		459671328
		862593741		862593741
		296154837	!=	796154832
		348267915		348267915
		715938462	!=	215938467
		923745186		923745186
		681329574		681329574
		574816293		574816293 
		
		This was discovered during a debug
		It is a random sudoku puzzle from the Internet with an answer
		How many sudoku puzzles have a several solutions..
		*/

        assertEquals(sud1.getState(), MANY_SOLVES);
        assertEquals(sud2.getState(), MANY_SOLVES);
    }

    @Test
    void noSolves() {
        String errSud1 = strSud1.replaceAll("530070000", "536070000");
        String errSud2 = strSud2.replaceAll("000380200", "000387200");
        String errSud3 = strSud3.replaceAll("090000400", "090020400");

        Sudoku sud1 = new Sudoku(errSud1).getSolve();
        Sudoku sud2 = new Sudoku(errSud2).getSolve();
        Sudoku sud3 = new Sudoku(errSud3).getSolve();

        assertEquals(sud1.getState(), UNSOLVABLE);
        assertEquals(sud2.getState(), UNSOLVABLE);
        assertEquals(sud3.getState(), UNSOLVABLE);
    }


    private String strEmpty =
            "000000000\n"
                    + "000000000\n"
                    + "000000000\n"
                    + "000000000\n"
                    + "000000000\n"
                    + "000000000\n"
                    + "000000000\n"
                    + "000000000\n"
                    + "000000000";


    private String strSud1 =
            "530070000"
                    + "600195000"
                    + "098000060"
                    + "800060003"
                    + "400803001"
                    + "700020006"
                    + "060000280"
                    + "000419005"
                    + "000080079";

    private String strSud1Answer =
            "534678912\n"
                    + "672195348\n"
                    + "198342567\n"
                    + "859761423\n"
                    + "426853791\n"
                    + "713924856\n"
                    + "961537284\n"
                    + "287419635\n"
                    + "345286179";

    private String strSud2 =
            "000003000"
                    + "008190367"
                    + "069002001"
                    + "670000480"
                    + "035000000"
                    + "000380200"
                    + "040051000"
                    + "910000520"
                    + "000040096";

    private String strSud2Answer =
            "751863942\n"
                    + "428195367\n"
                    + "369472851\n"
                    + "672519483\n"
                    + "835724619\n"
                    + "194386275\n"
                    + "246951738\n"
                    + "917638524\n"
                    + "583247196";

    //The world's hardest sudoku
    private String strSud3 =
            "800000000"
                    + "003600000"
                    + "070090200"
                    + "050007000"
                    + "000045700"
                    + "000100030"
                    + "001000068"
                    + "008500010"
                    + "090000400";

    private String strSud3Answer =
            "812753649\n"
                    + "943682175\n"
                    + "675491283\n"
                    + "154237896\n"
                    + "369845721\n"
                    + "287169534\n"
                    + "521974368\n"
                    + "438526917\n"
                    + "796318452";

    private String strSud4 =
            "100080009"
                    + "050601020"
                    + "000503000"
                    + "096104830"
                    + "300060005"
                    + "015908460"
                    + "000705000"
                    + "080309070"
                    + "500010003";
}
