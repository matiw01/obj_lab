package agh.ics.oop;

public interface IEngineObserver {
    public void stepMade(Integer epoch, Integer grasNumber, Integer animalsNumber,float avgEnergy, float avgChildrenNum, float avgLifeLength);
}
