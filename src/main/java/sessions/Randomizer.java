package sessions;

import java.util.Random;

/**
 * Created by kate on 26.04.14.
 */
public class Randomizer {
    private String something;
    private Random random;
    private String randomString;
    private char randomChar[];
    private char letter;
    private StringBuilder stringBuilder;

    public void Randomizer () {
        this.random = new Random();
        this.randomString = "abcdefghijklmnopqrstuvwxyz";
        this.randomChar  =randomString.toCharArray();
        this.stringBuilder = new StringBuilder();
    }

   public String getRandomSomething() {
       for(int i=0; i<10;++i) {
           letter = randomChar[random.nextInt(randomChar.length)];
           stringBuilder.append(letter);
       }
       return something = stringBuilder.toString();
   }
}
