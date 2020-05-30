package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.FieldsDAL;
import Domain.FootballManagmentSystem;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;

public class Field implements IAsset {

    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private Team myTeam;
    private int assetID;

    public Field() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        this.assetID = system.generateAssetID();
        system.addTeamAssets(this);
        new FieldsDAL().insert(this);
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
