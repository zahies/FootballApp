package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.ScorePoliciesDAL;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;
import java.util.UUID;

public class DefaultIScorePolicy implements IScorePolicy {


    UUID id;

    int win=3;
    int lose=0;
    int draw=1;

    public DefaultIScorePolicy(UUID id, int win, int lose, int draw) {
        this.id = id;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public DefaultIScorePolicy() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        id = UUID.randomUUID();
        new ScorePoliciesDAL().insert(this);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int winVal() {
        return win;
    }

    @Override
    public int looseVal() {

        return lose;
    }

    @Override
    public int drowVal() {
        return draw;
    }
}
