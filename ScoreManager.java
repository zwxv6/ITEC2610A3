import java.io.IOException;
import java.util.ArrayList;

public interface ScoreManager {

    /**
     Read the binary file about name and score of students
     @param pathName provide the route of binary file
     @return name and score list
     */
    public ArrayList<ScoreRecord> readRecords(String pathName) throws IOException;


    /**
     Sort the student score list
     @param inputList provide a list of name and score of students
     @return name and score list which is ordered
     */
    public ArrayList<ScoreRecord> mergeSortRecords(ArrayList<ScoreRecord> inputList);


    /**
     Save the ordered student score list
     @param pathName binary file storage location where user wants to store
     @param sortedList ordered list of score and name of students
     */
    public void saveScores(String pathName, ArrayList<ScoreRecord> sortedList) throws IOException;

    /**
     Searching score and name of student in search range
     @param pathName route of binary file
     @param minScore the minimum value of the score range
     @param maxScore the maximum value of the score range
     @return list of students within searching range
     */
    public ArrayList<ScoreRecord> scoreSearch(String pathName, int minScore, int maxScore) throws IOException;

}
