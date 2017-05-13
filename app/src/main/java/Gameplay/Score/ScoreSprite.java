package Gameplay.Score;

/**
 * the displayed scored values
 *
 * @author Joe Cumins and Thomas Wray
 */
public class ScoreSprite
{
    private int counter;

    /**
     *constructor
     */
    public ScoreSprite()
    {
        counter = 0;

    }

    /**
     * getCounter - gives counter
     *
     * @return the counter
     */
    public int getCounter()
    {
        return counter;
    }

    /**
     * incrementScore - increase the score by 1
     *
     *
     */
    public void incrementScore()
    {
        counter += 1;

    }
}
