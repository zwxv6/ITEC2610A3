import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

public class ScorePlatform implements ScoreManager{

    /**
     Read the binary file about name and score of students
     @param pathName provide the route of binary file
     @return name and score list
     */
    public ArrayList<ScoreRecord> readRecords(String pathName) throws IOException{
        String[] name = new String[10];
        int[] score = new int[10];

        ArrayList<ScoreRecord> arr = new ArrayList<>();
        DataInputStream dis = new DataInputStream(new FileInputStream(pathName));

        int a  = 0;
        int i = 0;
        while(a != -1 && i < 10){

            byte[] bytes = new byte[46];
            a = dis.read(bytes);
            name[i] = new String(bytes);
            score[i] = dis.readInt();

            ScoreRecord sr = new ScoreRecord(name[i],score[i]);
            arr.add(sr);

            i++;

        }
        dis.close();
        return arr;
    };

    /**
     Sort the student score list
     @param inputList provide a list of name and score of students
     @return name and score list which is ordered
     */
    public ArrayList<ScoreRecord> mergeSortRecords(ArrayList<ScoreRecord> inputList){
        ArrayList<ScoreRecord> firstArr1 = new ArrayList<>();
        ArrayList<ScoreRecord> secondArr1 = new ArrayList<>();

        if (inputList.size() < 2 ) {
            return inputList;
        }

        int[] first = new int[inputList.size() / 2];
        String[] firstn = new String[inputList.size() / 2];
        int[] second = new int[inputList.size() - first.length];
        String[] secondn = new String[inputList.size() - first.length];

        for (int i = 0; i < first.length; i++){
            first[i] = inputList.get(i).getScore();
            firstn[i] = inputList.get(i).getName();
            ScoreRecord sr = new ScoreRecord(firstn[i],first[i]);
            firstArr1.add(sr);
        }

        for (int i = 0; i < second.length; i++){
            second[i] = inputList.get(first.length + i).getScore();
            secondn[i] = inputList.get(firstn.length + i).getName();
            ScoreRecord sr = new ScoreRecord(secondn[i],second[i]);
            secondArr1.add(sr);
        }

        mergeSortRecords(firstArr1);
        mergeSortRecords(secondArr1);

        merge(firstArr1,secondArr1,inputList);

        for(int i = 0; i < inputList.size(); i++){
            if(i == 0 ) {
                System.out.print("Intermediate result: [ " + inputList.get(i).toString() + ",");
            }else if(i <= inputList.size() - 2){
                System.out.print(inputList.get(i).toString() + ",");
            }else{
                System.out.println(inputList.get(i).toString() + " ]");
            }
        }

        return inputList;
    };

    /**
     Sort the list according score
     @param inputList provide a list of name and score of students
     @param first list of score and name for the first half
     @param second list of score and name for the second half
     */
    public static void merge(ArrayList<ScoreRecord> first, ArrayList<ScoreRecord> second, ArrayList<ScoreRecord> inputList) {
        int iFirst = 0;
        int iSecond = 0;
        int j = 0;

        while (iFirst < first.size() && iSecond < second.size())
            {
            if (first.get(iFirst).getScore() > second.get(iSecond).getScore())
                {
                    String name;
                    int score;
                    score = first.get(iFirst).getScore();
                    name = first.get(iFirst).getName();
                    ScoreRecord sr = new ScoreRecord(name,score);
                    inputList.set(j,sr);
                iFirst++;
                }
            else {
                String name;
                int score;
                score = second.get(iSecond).getScore();
                name = second.get(iSecond).getName();
                ScoreRecord sr = new ScoreRecord(name,score);
                inputList.set(j,sr);
                iSecond++;
                }
            j++;
            }

        while (iFirst < first.size())
             {
                 String name;
                 int score;
                 score = first.get(iFirst).getScore();
                 name = first.get(iFirst).getName();
                 ScoreRecord sr = new ScoreRecord(name,score);
                 inputList.set(j,sr);
                 iFirst++; j++;
             }

         while (iSecond < second.size())
             {
                 String name;
                 int score;
                 score = second.get(iSecond).getScore();
                 name = second.get(iSecond).getName();
                 ScoreRecord sr = new ScoreRecord(name,score);
                 inputList.set(j,sr);
                 iSecond++; j++;
             }

    }

    /**
     Save the ordered student score list
     @param pathName binary file storage location where user wants to store
     @param sortedList ordered list of score and name of students
     */
    public void saveScores(String pathName, ArrayList<ScoreRecord> sortedList) throws IOException{
        RandomAccessFile f = new RandomAccessFile(pathName,"rw");
        for(int i = 0; i < 10 ; i++){
            f.seek(i*50);
            byte[] bytes = sortedList.get(i).getName().getBytes();
            f.write(bytes,0,46);
            f.writeInt(sortedList.get(i).getScore());
        }
        f.close();
    };

    /**
     Searching score and name of student in search range
     @param pathName route of binary file
     @param minScore the minimum value of the score range
     @param maxScore the maximum value of the score range
     @return list of students within searching range
     */
    public ArrayList<ScoreRecord> scoreSearch(String pathName, int minScore, int maxScore) throws IOException{
        RandomAccessFile f = new RandomAccessFile(pathName,"rw");
        int[] score = new int[10];
        String[] name = new String[10];
        ArrayList<ScoreRecord> arr1 = new ArrayList<>();

        int a = 0;
        for(int i = 0 ; i<10 ; i++){
            f.seek(i * 50);
            byte[] bytes = new byte[46];
            a = f.read(bytes);
            name[i] = new String(bytes);
            score[i] = f.readInt();
        }

        for(int max = 0, min = score.length-1; max < min; max++, min-- ){
            int temp = score[max];
            String temp1 = name[max];

            score[max] = score[min];
            name[max] = name[min];

            score[min] = temp;
            name[min] = temp1;
        }

        int index = Arrays.binarySearch(score,minScore);
        if(index < 0){
            index = -(index) - 1;
        }else if(index == -1){
            return arr1;
        }

        for( int i = index ; i >= 0 && score[i] <= maxScore; i++ ) {
            ScoreRecord sr = new ScoreRecord(name[i],score[i]);
            arr1.add(sr);
        }

        for(int i = 0; i < arr1.size(); i++ ){
            System.out.println("Search visit: " + arr1.get(i).toString());
        }
        return arr1;
    };
}
