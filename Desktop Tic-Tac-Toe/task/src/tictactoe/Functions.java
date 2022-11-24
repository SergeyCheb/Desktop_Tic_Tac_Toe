package tictactoe;

    enum Filler {
        X, O, NOBODY
    };

    enum Player {
        Human, Computer
    }

public class Functions {
    //This function checks if anybody wins on the current move.
    static Filler whoWin(MyJButton[][] arrMyButtons) {
        for (int i = 0; i < 3; ++i) {
            Filler result = isLine(arrMyButtons[i][0], arrMyButtons[i][1], arrMyButtons[i][2]);
            if (!result.equals(Filler.NOBODY)) {
                return result;
            }
            result = isLine(arrMyButtons[0][i], arrMyButtons[1][i], arrMyButtons[2][i]);
            if (!result.equals(Filler.NOBODY)) {
                return result;
            }
            result = isLine(arrMyButtons[0][0], arrMyButtons[1][1], arrMyButtons[2][2]);
            if (!result.equals(Filler.NOBODY)) {
                return result;
            }
            result = isLine(arrMyButtons[0][2], arrMyButtons[1][1], arrMyButtons[2][0]);
            if (!result.equals(Filler.NOBODY)) {
                return result;
            }
        }
        return Filler.NOBODY;
    }

    //This function checks if empty cells is remained in the play field.
    static boolean isEmptyCells(MyJButton[][] arrMyButtons) {
        for (MyJButton[] i : arrMyButtons) {
            for (MyJButton j : i) {
                if (j.getText().equals(" ")) {
                    return true;
                }
            }
        }
        return false;
    }

    //This function checks if its arguments have same text fields. If it has not, it returns
    //label NOBODY otherwise either X or O respectively.
    private static Filler isLine(MyJButton i1, MyJButton i2, MyJButton i3) {
        String a = i1.getText();
        String b = i2.getText();
        String c = i3.getText();
        if (a.equals(b) && b.equals(c)) {
            if (a.equals("X")) {
                return Filler.X;
            } else if (a.equals("O")) {
                return Filler.O;
            } else {
                return Filler.NOBODY;
            }
        } else {
            return Filler.NOBODY;
        }
    }
}
