package Domain.SeasonManagment;

public class DefaultIScorePolicy implements IScorePolicy {


    @Override
    public int winVal() {
        return 3;
    }

    @Override
    public int looseVal() {
        return 0;
    }

    @Override
    public int drowVal() {
        return 1;
    }
}
