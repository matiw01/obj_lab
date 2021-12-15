package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IEngineObserver;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabelMaintainer implements IEngineObserver {
    TableView table;
    Animal followedAnimal;

    public TabelMaintainer() {
        this.table = new TableView();

        TableColumn<Animal, String> positonColumn = new TableColumn<>("Position");
        positonColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Animal, String> lifeLengthColumn = new TableColumn<>("Life length");
        lifeLengthColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Animal, String> numberOfChildrenColumn = new TableColumn<>("Children");
        numberOfChildrenColumn.setCellValueFactory(new PropertyValueFactory<>("childrenNumber"));

        TableColumn<Animal, String> descendantsNumberColumn = new TableColumn<>("Descendants");
        descendantsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("descendantsNumber"));

        TableColumn<Animal, String> genotypeColumn = new TableColumn<>("Genotype");
        genotypeColumn.setCellValueFactory(new PropertyValueFactory<>("genotype"));

        TableColumn<Animal, String> dateOfDeathColum = new TableColumn<>("Death");
        dateOfDeathColum.setCellValueFactory(new PropertyValueFactory<>("deathEpoch"));

        table.getColumns().add(positonColumn);
        table.getColumns().add(lifeLengthColumn);
        table.getColumns().add(numberOfChildrenColumn);
        table.getColumns().add(descendantsNumberColumn);
        table.getColumns().add(genotypeColumn);
        table.getColumns().add(dateOfDeathColum);


        table.setMaxWidth(720);
        table.setMaxHeight(100);
    }

    public TableView createTable() {
        return this.table;
    }

    public void setFollowedAnimal(Animal newAnimal) {
        if (this.followedAnimal != null) {
            this.followedAnimal.changeFollowingStatus();
        }
        table.getItems().remove(this.followedAnimal);
        this.followedAnimal = newAnimal;
        this.followedAnimal.changeFollowingStatus();
        table.getItems().add(newAnimal);
        updateTable();
    }

    public void removeFollowedAnimal() {
        table.getItems().remove(this.followedAnimal);
        updateTable();
        this.followedAnimal.changeFollowingStatus();
    }

    public void updateTable() {
        this.table.refresh();
    }

    @Override
    public void stepMade(Integer epoch, Integer grasNumber, Integer animalsNumber, float avgEnergy, float avgChildrenNum, float avgLifeLength) {updateTable();}
}

