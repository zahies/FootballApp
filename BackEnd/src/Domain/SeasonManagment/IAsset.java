package Domain.SeasonManagment;

public interface IAsset {

    public int value = 0;

    public int getAssetID();

    public void edit(int value);

    public Team getMyTeam();

    public int getValue();


}
