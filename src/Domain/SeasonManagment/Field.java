package Domain.SeasonManagment;

import Domain.FootballManagmentSystem;

public class Field implements IAsset {

    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private Team myTeam;
    private int assetID;

    public Field() {
        this.assetID = system.generateAssetID();
        system.addTeamAssets(this);
    }

    @Override
    public int getAssetID() {
        return assetID;
    }

    @Override
    public void edit(int value) {

    }

    public void setMyTeam(Team myTeam) {
        this.myTeam = myTeam;
    }

    @Override
    public Team getMyTeam() {
        return myTeam;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
