package agh.ics.oop.gui;

import agh.ics.oop.IGenotypeObserver;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class DominantGenotypeVisualizer implements IGenotypeObserver {
    private List<Integer> dominantGentype;
    private Label label = new Label("Press start for genotype to be calcualted");

    public DominantGenotypeVisualizer(){
        this.dominantGentype = new ArrayList<>();
    }

    public Label createLabel(){return this.label;}

    @Override
    public void dominantGenotypeUpdate(List<Integer> newDominantGenotype) {
        this.dominantGentype = newDominantGenotype;
        Platform.runLater(() -> {
            this.label.setText(newDominantGenotype.toString());
        });
    }

}
