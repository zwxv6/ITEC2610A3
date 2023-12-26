/**
* A score record with a participant name and score.
*/
public class ScoreRecord {
   private String name;
   private int score;

   /**
    * Constructs a score record.
    * @param name  the name of the participant
    * @param score  the score of the participant
    */
   public ScoreRecord(String name, int score) {
       this.name = name;
       this.score = score;
   }

   /**
    * Gets the name of the participant
    * @return  the name
    */
   public String getName() {
       return name;
   }

   /**
    * Gets the score of this record
    * @return  the score
    */
   public int getScore() {
       return score;
   }

   /**
    * Returns a string representation of (name, score) pair
    * @return  the string representation of this record in the format of (name, score)
    */
   public String toString() {
       return "(" + name + ", " + score + ")";
   }
}
