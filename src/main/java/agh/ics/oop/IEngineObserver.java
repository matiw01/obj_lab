package agh.ics.oop;

public interface IEngineObserver {
    public void stepMade(Integer epoch, Float grasNumber, Float animalsNumber,Float avgEnergy, Float avgChildrenNum, Float avgLifeLength);
}
