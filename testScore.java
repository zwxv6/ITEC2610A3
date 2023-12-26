import java.io.IOException;
import java.util.ArrayList;

public class testScore {
    public static void main(String[] args) throws IOException {
        ScorePlatform sp = new ScorePlatform();
        ArrayList<ScoreRecord> ar = sp.readRecords("D:\\Java\\testrecords.bin");
        System.out.println(ar);
        sp.mergeSortRecords(ar);
        sp.saveScores("D:\\Java\\testrecords1.bin",ar);
        ArrayList<ScoreRecord> ar1 = sp.readRecords("D:\\Java\\testrecords1.bin");
        System.out.println(ar1);
        sp.scoreSearch("D:\\Java\\testrecords1.bin",70,80);
    }


}
