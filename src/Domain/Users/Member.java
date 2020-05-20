package Domain.Users;

import Domain.Alerts.IAlert;
import Domain.FootballManagmentSystem;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Member extends GeneralUser {
    protected String name; /// USER NAME
    private int id; // ?!?!?!
    private String password;
    private String real_Name;
    private Queue<IAlert> alertsList;
    private boolean isActive;
    private boolean alertViaMail;
    private String mailAddress;


    public Member( String name, int id, String password, String real_name) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.real_Name = real_name;
        isActive = false;
        alertViaMail = false;
        alertsList = new LinkedList<>();
        mailAddress = "";
    }

    public Member() {
        alertsList = new LinkedList<>();
    }

    public String getReal_Name() {
        return real_Name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setAlertViaMail(boolean alertViaMail) {
        this.alertViaMail = alertViaMail;
    }

    /**
     * this func address the requirement to get alerts when offline. when offline alerts will be added to the queue that will be shown to user once online.
     *
     * @param newAlert
     */
    public void handleAlert(IAlert newAlert) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        if (alertViaMail) {
            try {
                system.sendInvitationByMail(this.mailAddress, "You have A new Alert in Football App", newAlert.toString());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return;
        }
        if (isActive) {
            System.out.println(newAlert);
        } else {
            alertsList.add(newAlert);
        }
    }

    public void logOut() {
        FootballManagmentSystem.getInstance().logOut(this);
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
    /*getSet*/

    public Queue<IAlert> getAlertsList() {
        //alertsList.clear(); maybe
        return alertsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return name.equals(member.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
}
