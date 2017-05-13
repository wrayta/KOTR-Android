package Gameplay.Life;

import android.widget.ImageView;

import com.example.thomas.kotr_android.R;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Gameplay.Pattern.PatternKeySubject;
import Gameplay.Pattern.PatternKeyWrongObserver;

/**
 * Setup for a LifeSprite object
 * @author  Thomas Wray & Joe Cumins
 * @version 1
 */
public class LifeSprite implements PatternKeyWrongObserver, LifeSubject
{
    private List<LifeObserver> observers;
    private int counter;
//    private TransformableContent currentContent;
    private boolean isLivesAtZero;

    /**
     * constructor
     *
     */
    public LifeSprite()
    {
        counter = 0;

        isLivesAtZero = false;

        observers = new LinkedList<LifeObserver>();
    }

    /**
     * handleIncorrectAction - this method is given from the PatternKey
     * the information as to whether the user clicked the correct knight (based
     * on the current shield pattern).
     *
     * @param source
     */
    public void handleIncorrectAction(PatternKeySubject source)
    {

        if(counter == 2)
        {
            isLivesAtZero = true;
        }

        if(counter < 2)
        {
            counter++;
        }

        notifyObserver();

    }

    /**
     * addObserver - adds a LivesObserver to the group
     */
    public void addObserver(LifeObserver observer)
    {
        observers.add(observer);
    }

    /**
     * removeObserver - removed a LivesObserver from the group
     */
    public void removeObserver(LifeObserver observer)
    {
        observers.remove(observer);
    }

    /**
     * notifyObserver - informs that observers of the Lives class the
     * info that need to know
     */
    public void notifyObserver()
    {
        Iterator<LifeObserver> iterator;
        LifeObserver 				    observer;

        iterator = observers.iterator();

        while(iterator.hasNext())
        {
            observer = iterator.next();
            observer.handleOutOfLives(this);

        }

    }


    /**
     * getOutOfLivesCheck - returns boolean if there are 0 lives (true/false)
     */
    public boolean getOutOfLivesCheck()
    {
        return isLivesAtZero;
    }

}
