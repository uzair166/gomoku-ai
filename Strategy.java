import java.util.ArrayList;
import java.util.HashMap;

public class Strategy {

    public static void main(String[] args){
        HashMap<String, String> hm = new HashMap<>();
        hm.put("uk", "United Kingdom");
        hm.put("fr", "France");

        System.out.println(hm.get("United Kingdom"));
    }

    public static void mainn(String[] args) {

        double temp = 9.8;
        Double temp2 = 10.2;

        temp = temp + temp2;

        temp2 = temp2 + temp;

        int[][] mat3 = new int[10][];
        for (int i=0; i<mat3.length; i++){
            mat3[i] = new int[i+1];
            for (int j=0; j<=i; j++){
                mat3[i][j] = j;
            }
        }


        for (int i=0; i<mat3.length; i++){
            for (int j=0; j<mat3[i].length; j++){
                System.out.print(mat3[i][j]);
            }
            System.out.println();
        }







        System.out.println(Double.POSITIVE_INFINITY-1);
        int[][] mat = { {1 , 2 , 3},
                        {6 , 7 , 8},
                        {11, 12, 13}};

        int m = mat.length;
        int n = mat[0].length;
        int b = 0;
        System.out.println(m + " " + n);


        ArrayList<ArrayList<Integer>> diagonals = new ArrayList<>();
        for (int i = 1 - m; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                if ((i + j) >= 0 && (i + j) < n) list.add(mat[j][i + j]);
            }
            diagonals.add(list);
        }

        int[][] mat2 = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = n - 1, k = 0; j >= 0; j--, k++) {
                mat2[k][i] = mat[j][i];
            }
        }


        ArrayList<ArrayList<Integer>> diagonals2 = new ArrayList<>();
        for (int i = 1 - m; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                if ((i + j) >= 0 && (i + j) < n) list.add(mat2[j][i + j]);
            }
            diagonals.add(list);
        }

        System.out.println(diagonals);
    }
}






//
//         0  1  2  3  #  #
//         #  4  5  6  7  #
//         #  #  8  9 10 11