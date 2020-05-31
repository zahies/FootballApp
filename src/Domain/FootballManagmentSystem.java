package Domain;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.PasswordHash;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import DataAccess.SeasonManagmentDAL.ComplaintFormsDAL;
import DataAccess.SeasonManagmentDAL.LeaguesDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UserInformationDAL.PersonalPagesDAL;

import DataAccess.UsersDAL.MembersDAL;
import DataAccess.UsersDAL.RefereesDAL;
import DataAccess.UsersDAL.SystemManagerDAL;
import Domain.Alerts.IAlert;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;



public class FootballManagmentSystem extends TimerTask {

    public void restoreDatabase() throws UserInformationException, SQLException, NoPermissionException, NoConnectionException, UserIsNotThisKindOfMemberException, EmptyPersonalPageException {
        allTeams = new TeamsDAL().selectALl();
        allLeagus = new LeaguesDAL().selectAll();
        allRefs = new RefereesDAL().selectAll();
        allInCharge = new SystemManagerDAL().selectAll();
        personalPages = new PersonalPagesDAL().selectAll();
        allcomplaints = new ComplaintFormsDAL().selectAll();
        allAssests = new AssetsDAL().selectAll();
        members = new MembersDAL().selectAll();
    }
    // HashMap<Integer, Leaugue> leagues = new HashMap<Integer, Leaugue>();
    private List<Leaugue> allLeagus = new ArrayList<>();
    private HashMap<UUID, Team> allTeams = new HashMap<>();
    private List<Referee> allRefs = new ArrayList<>();
    private HashMap<Integer, IAsset> allAssests = new HashMap<>(); // Stadiums , players and coaches? : all assets just for records
    private HashMap<String, LinkedList<Member>> members = new HashMap<>();
    private List<SystemManager> allInCharge = new ArrayList<>();
    private SystemManager firstSystemManager;
    private HashMap<Integer, PersonalInfo> personalPages = new HashMap<>();
    private static FootballManagmentSystem single_instance = new FootballManagmentSystem();
    private List<ComplaintForm> allcomplaints = new ArrayList<>(); // username - complaints
    private Date upComingDateToCheck;
    private boolean restore=true;
    /**
     * constraint 7
     */
    private int indexOfNextDateToCheck;
    Date date1 = new Date("31/03/2020");
    Date date2 = new Date("30/06/2020");
    Date date3 = new Date("30/09/2020");
    Date date4 = new Date("31/12/2020");
    Timer timer = new Timer();


    private FootballManagmentSystem() {
        /**maybe read some text file from pc to see who are the systemManager that registered ?? */

//        File fileNew = new File("log/init.txt");
////        File file = new File(getClass().getClassLoader().getResource("log/init.txt").getFile());
//        if (fileNew == null) return;
//        String userName;
//        String realName;
//        int id;
//        String password;
//
//        try (FileReader reader = new FileReader(fileNew);
//             BufferedReader br = new BufferedReader(reader)) {
//
//            String line;
//            int i = 0;
//            while ((line = br.readLine()) != null) {
//                String[] details = line.split(" ");
//                userName = details[1];
//                realName = details[2];
//                realName += " " + details[3];
//                id = Integer.parseInt(details[4]);
//                if (id == 0) {
//                    throw new UserInformationException();
//                }
//                password = details[5];
//                SystemManager currentSysManager = new SystemManager(userName, realName, id, password);
//                allInCharge.add(currentSysManager);
//                LinkedList<Member> list = new LinkedList<>();
//                list.add(allInCharge.get(i));
//                members.put(allInCharge.get(i).getName(), list);
//                i++;
//            }
//        } catch (UserInformationException ue) {
//            ue.printStackTrace();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        } catch (mightBeSQLInjectionException e) {
//            e.printStackTrace();
//        } catch (DuplicatedPrimaryKeyException e) {
//            e.printStackTrace();
//        }

//            try {
//                restoreDatabase();
//            } catch (UserInformationException e) {
//                e.printStackTrace();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            } catch (NoPermissionException e) {
//                e.printStackTrace();
//            } catch (NoConnectionException e) {
//                e.printStackTrace();
//            } catch (UserIsNotThisKindOfMemberException e) {
//                e.printStackTrace();
//            }

        //firstSystemManager = allInCharge.get(0);
        /** initialize connection with servers */

        /** constraint 7 - balanced budget  */
        indexOfNextDateToCheck++;
        upComingDateToCheck = date1;
        timer.schedule(this, upComingDateToCheck);

    }


    public LinkedList<Member> login(String username, String password) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        try {
            password = PasswordHash.getInstance().hash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        LinkedList<Member> logging = members.get(username);
        if (logging == null) {
            throw new UserInformationException();
        }
        boolean correctPW = false;
        for (Member member : logging) {
            if (member.getPassword().equals(password)) {
                correctPW = true;
                for (Member member1 : logging) {
                    member1.setActive(true);
                    new MembersDAL().update(member1);
                }
            }
        }
        if (correctPW) {
            Queue<IAlert> missedAlertsWhileLogOut = new LinkedList<>();
            for (Member member : logging) {
                missedAlertsWhileLogOut.addAll(member.getAlertsList());
            }
            for (IAlert alert : missedAlertsWhileLogOut) {
                logging.get(0).handleAlert(alert);
            }
            return logging;
        } else {
            throw new UserInformationException();
        }
    }

    public boolean logOut(Member mem) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (mem == null) {
            return false;
        }
        LinkedList<Member> memberAccounts = members.get(mem.getName());
        if (memberAccounts == null) {
            return false;
        }
        for (Member member : memberAccounts) {
            member.setActive(false);
            new MembersDAL().update(member);
        }
        return true;
    }

    /**
     * register for simpale members , fan like.
     *
     * @param userName
     * @param pass
     * @param id
     * @return
     */
    public boolean register(String userName, String realname, String pass, int id) throws UserInformationException {
        if (members.get(userName) != null) {
            throw new UserInformationException(); //username is taken;
        } else {
            Member addTo = null;
            try {
                addTo = new Fan(userName, realname, id, pass);
            } catch (mightBeSQLInjectionException e) {
                e.printStackTrace();
            } catch (DuplicatedPrimaryKeyException e) {
                e.printStackTrace();
            } catch (NoPermissionException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (UserIsNotThisKindOfMemberException e) {
                e.printStackTrace();
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
            LinkedList<Member> memberAccounts = new LinkedList<>();
            memberAccounts.add(addTo);
            members.put(userName, memberAccounts);

            SystemLog.getInstance().UpdateLog("New fan member has been added to system - username is: " + userName);
            return true; // added succesfully
        }
    }

    /**
     * teamOwner responsebillity
     */
    public boolean registerTeam(Team team) {
        //////need confirmation from Comissioner
        allTeams.put(team.getId(), team);
        SystemLog.getInstance().UpdateLog("New team" + team.getName() + " has been added to system by owner: " + team.getOwner().getName());/////add TEam name to team and to log!
        return true;
    }

    /**
     * teamOwner responsebillity
     *
     * @param asset
     */
    public void addTeamAssets(IAsset asset) {
//           if(asset instanceof Player||asset instanceof Coach||asset instanceof TeamManager) {
//                members.put(((Member) asset).getName(), (Member) asset);
//
//                SystemLog.getInstance().UpdateLog("New "+asset.getClass().toString().toLowerCase()+" has been added to team: " +asset.getMyTeam());
//            }
        if (!allAssests.containsKey(asset.getAssetID())) {
            allAssests.put(asset.getAssetID(), asset);
        } else {
            allAssests.replace(asset.getAssetID(), asset);
        }
    }

    /**
     * Association responsibillty UC 9.3
     *
     * @param ref
     */
    public void addReferee(Referee ref) {
        if (members.containsKey(ref.getName())) {
            if (!members.get(ref.getName()).contains(ref)) {
                LinkedList<Member> membersAccounts = members.get(ref.getName());
                membersAccounts.add(ref);
                members.replace(ref.getName(), membersAccounts);
                SystemLog.getInstance().UpdateLog("New referee has been added to the league");
            }
        } else {
            LinkedList<Member> membersAccounts = new LinkedList<>();
            membersAccounts.add(ref);
            members.put(ref.getName(), membersAccounts);
        }
        allRefs.add(ref);
    }


    /**
     * constraint 7
     */
    //TODO write in the main for every quarter :
    //                                  Date date = new Date("02/29/2020");
    //                                  Timer timer = new Timer();
    //                                  TimerTask task = new Helper();
    //                                  timer.schedule(task, date);
    @Override
    public void run() {

        for (int i = 0; i < allTeams.size(); i++) {
            allTeams.get(i).getControlBudget().checkIncomeBiggerThanOutcome();
        }

        indexOfNextDateToCheck++;
        if (indexOfNextDateToCheck == 2) {
            timer.schedule(this, date2);
        } else if (indexOfNextDateToCheck == 3) {
            timer.schedule(this, date3);
        } else if (indexOfNextDateToCheck == 4) {
            timer.schedule(this, date4);
        }


    }


    /**
     * Association responsibillty UC 9.3
     *
     * @param ref
     */
    public void delReferee(String ref) throws UserInformationException {
        boolean found = false;
        for (int i = 0; i < allRefs.size(); i++) {
            if (allRefs.get(i).getName().equals(ref)) {
                allRefs.remove(i);
                found = true;
            }
        }
        if (!found) {
            throw new UserInformationException("there is not exist referee with the name " + ref);
        }
    }


    /**
     * System Manager can Delete everyBody!
     *
     * @param m
     */
    public void RemoveMember(List<Member> m) {
        if (members.containsKey(m.get(0).getName())) {
            members.remove(m.get(0).getName());
            SystemLog.getInstance().UpdateLog(m.get(0).getName() + "has been deleted from the system");
        } else {
            //he is not a member
        }
    }

//    public void removeTeam(Team t) {
//        t.setClosed(true);
//        SystemLog.getInstance().UpdateLog("has been deleted from the system"); // again team name????
//        allTeams.remove(t);
//    }

    public void removeAsset(IAsset asset) {
        if (asset instanceof Player || asset instanceof Coach || asset instanceof TeamManager) {
            members.remove(((Member) asset).getName());
            SystemLog.getInstance().UpdateLog(((Member) asset).getName() + " has been deleted from the system"); // again team name????

        }
        allAssests.remove(asset);
    }

    /**
     * adding personal page to hash map
     *
     * @param personalInfo -
     * @return -
     */
    public boolean addPersonalPage(PersonalInfo personalInfo) {
        personalPages.put(personalInfo.getPageID(), personalInfo);
        return true;
    }

    /**
     * removing personal page from hashmap
     *
     * @param personalInfo-
     * @return - true id succeeded
     */
    public boolean removePersonalPage(PersonalInfo personalInfo) {
        if (personalPages.containsKey(personalInfo.getPageID())) {
            personalPages.remove(personalInfo.getPageID());
            return true;
        }
        return false;
    }

    /**
     * this func is a generator for unique PersonalInfo pages IDs
     *
     * @return page ID
     */
    public int generatePageID() {
        int pageID = idGenerator();
        while (personalPages.containsKey(pageID)) {
            pageID = idGenerator();
        }
        return pageID;
    }


    /**
     * this func is a generator for unique Asset IDs
     *
     * @return page ID
     */
    public int generateAssetID() {
        int assetID = idGenerator();
        while (allAssests.containsKey(assetID)) {
            assetID = idGenerator();
        }
        return assetID;
    }

    /**
     * ID generator
     *
     * @return
     */
    public int idGenerator() {
        Random rand = new Random();
        int pageID = 0;
        for (int i = 0; i < 8; i++) {
            int digit = rand.nextInt(10);
            if (digit == 0) {
                pageID = pageID + ((int) (Math.pow(10, i)));
            } else {
                pageID = pageID + (digit) * ((int) (Math.pow(10, i)));
            }
        }
        return pageID;
    }


    public List<Member> getMemberByUserName(String name) {
        return members.get(name);
    }

    /**
     * Assosiation - UC 3.6. the system needs to approve the new name of the member
     *
     * @param mem  - member wishes to change usser name
     * @param name - new name
     * @return - true if new name is available and change succeeded
     */
    public boolean changeUserName(Member mem, String name) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (members.containsKey(name)) {
            throw new UserInformationException();
        }
        LinkedList<Member> memberAccounts = members.get(mem.getName());
        if (memberAccounts == null) {
            throw new UserInformationException();
        }
        String oldName = mem.getName();
        members.remove(oldName);
        for (Member member : memberAccounts) {
            member.setName(name);
            new MembersDAL().update(member);
        }
        members.put(name, memberAccounts);
        return true;
    }

    public boolean changeUserPassword(Member mem, String newPassowrd) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        LinkedList<Member> memberAccounts = members.get(mem.getName());
        if (memberAccounts == null) {
            throw new UserInformationException();
        }
        for (Member member : memberAccounts) {
            member.setPassword(newPassowrd);
            new MembersDAL().update(member);
        }
        members.replace(mem.getName(), memberAccounts);
        return true;
    }

    /**
     * getting existing member and replacing his member with team owner member giving him the func
     *
     * @param newOwner - new owner
     * @param team     - the team he will own
     * @return true if succeeded
     */
    public LinkedList<Member> makeMemberTeamOwner(Member newOwner, Team team) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (members.containsKey(newOwner.getName())) {
            TeamOwner newOwnerAccount = new TeamOwner(newOwner.getName(), newOwner.getReal_Name(), newOwner.getId(), newOwner.getPassword(), team.getId());
            LinkedList<Member> memberAccounts = members.get(newOwner.getName());
            if (memberAccounts != null && (!memberAccounts.contains(newOwnerAccount))) {
                memberAccounts.add(newOwnerAccount);
                members.replace(newOwner.getName(), memberAccounts);
                SystemLog.getInstance().UpdateLog(newOwner.getName() + "has become team owner of : " + team.getName());
                return memberAccounts;
            }
        }
        return null;
    }

    /**
     * not like makeMemberTeamOwner this func create new object and doesnt delete previous member
     * now the member will have two users
     * one with his original user name - for the member he was before
     * the second for his new team manager profile with his original user name + "ManagerUser"
     *
     * @param newManager - member profile
     * @param team       - the team he will manage
     * @param value      - his asset value
     * @return - the new team manager object
     */
    public LinkedList<Member> makeMemberTeamManger(Member newManager, Team team, int value, TeamOwner teamOwnerAssigned) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (members.containsKey(newManager.getName())) {
            LinkedList<Member> memberAccounts = members.get(newManager.getName());
            TeamManager newManagerAccount = new TeamManager(newManager.getName(), newManager.getReal_Name(), newManager.getId(), newManager.getPassword(), value, team, teamOwnerAssigned);
            if (memberAccounts != null && (!memberAccounts.contains(newManagerAccount))) {
                memberAccounts.add(newManagerAccount);
                members.replace(newManager.getName(), memberAccounts);
                SystemLog.getInstance().UpdateLog(newManager.getName() + "has become team manager of : " + team.getName());
                return memberAccounts;
            }
        }
        return null;
    }

    public Team getTeamByID(UUID id) {
        return allTeams.get(id);
    }

    public List<ComplaintForm> getAllcomplaints() {
        return allcomplaints;
    }

    public static FootballManagmentSystem getInstance() {
        if (single_instance == null) {
            single_instance = new FootballManagmentSystem();
        }
        if(single_instance.restore){
            single_instance.restore=false;
            try {
                single_instance.restoreDatabase();
            } catch (UserInformationException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NoPermissionException e) {
                e.printStackTrace();
            } catch (NoConnectionException e) {
                e.printStackTrace();
            } catch (UserIsNotThisKindOfMemberException | EmptyPersonalPageException e) {
                e.printStackTrace();
            }
        }
        return single_instance;
    }

    public List<Leaugue> getAllLeagus() {
        return allLeagus;
    }


    public HashMap<UUID, Team> getAllTeams() {
        return allTeams;
    }

    public HashMap<Integer, IAsset> getAllAssests() {
        return allAssests;
    }

    public HashMap<String, LinkedList<Member>> getMembers() {
        return members;
    }

    public void setSystemManager(SystemManager sm) {
        allInCharge.add(sm);
    }

    public List<Referee> getAllRefs() {
        return allRefs;
    }

    public List<SystemManager> getAllInCharge() {
        return allInCharge;
    }

/*
    /**
     * This method checks if the user is a commissioner and the leaugue is legal/already exists,
     * and adds it to the HashMap league
     * @param league
     */
//    public void defineLeaugue(Leaugue league){
//        //todo check if user is a legal commissioner
//        if (legalLeague(league)){
//            leagues.put(league. league);
//        }
//
//    }
/*

    /**
     * This method checks if the league is legal
     * @param league
     * @return true if legal, false if it doesn't
     */

    private boolean legalLeague(Leaugue league) {
        //fixme create the method

        return true;
    }

    public void addMember(Member m) throws UserInformationException {
        if (members.get(m.getName()) != null) {
            throw new UserInformationException(); //username is taken;
        }
        LinkedList<Member> memberAccounts = new LinkedList<>();
        memberAccounts.add(m);
        members.put(m.getName(), memberAccounts);

    }

    public Member getMemberInstanceByKind(String userName, String instance) throws UserIsNotThisKindOfMemberException {
        List<Member> memberAccounts = getMemberByUserName(userName);
        if (memberAccounts == null) {
            throw new UserIsNotThisKindOfMemberException();
        }
        for (Member member : memberAccounts) {
            switch (instance) {
                case ("Coach"):
                    if (member instanceof Coach) {
                        return member;
                    }
                    break;
                case ("Commissioner"):
                    if (member instanceof Commissioner) {
                        return member;
                    }
                    break;
                case ("Fan"):
                    if (member instanceof Fan) {
                        return member;
                    }
                    break;
                case ("Player"):
                    if (member instanceof Player) {
                        return member;
                    }
                    break;
                case ("Referee"):
                    if (member instanceof Referee) {
                        return member;
                    }
                    break;
                case ("System Manager"):
                    if (member instanceof SystemManager) {
                        return member;
                    }
                    break;
                case ("Team Manager"):
                    if (member instanceof TeamManager) {
                        return member;
                    }
                    break;
                case ("Team Owner"):
                    if (member instanceof TeamOwner) {
                        return member;
                    }
                    break;
            }
        }
        throw new UserIsNotThisKindOfMemberException();
    }

    public void removeMemberSpecificAccount(Member member1, String instance) throws UserIsNotThisKindOfMemberException {
        if (members.containsKey(member1.getName())) {
            List<Member> memberAccounts = getMemberByUserName(member1.getName());
            for (Member member : memberAccounts) {
                switch (instance) {
                    case ("Coach"):
                        if (member instanceof Coach) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("Commissioner"):
                        if (member instanceof Commissioner) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("Fan"):
                        if (member instanceof Fan) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("Player"):
                        if (member instanceof Player) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("Referee"):
                        if (member instanceof Referee) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("System Manager"):
                        if (member instanceof SystemManager) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("Team Manager"):
                        if (member instanceof TeamManager) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                    case ("Team Owner"):
                        if (member instanceof TeamOwner) {
                            memberAccounts.remove(member);
                            return;
                        }
                        break;
                }
            }
            throw new UserIsNotThisKindOfMemberException();
        }
    }

    public void addLeague(Leaugue leaugue) throws LeagueIDAlreadyExist {

        allLeagus.add(leaugue);
    }

    public void addTeam(Team team) {
        allTeams.put(team.getId(), team);
    }

    public void addComplaint(ComplaintForm complaintForm) {
        allcomplaints.add(complaintForm);
    }

    public void removeComplaint(ComplaintForm complaintForm) {
        allcomplaints.remove(complaintForm);
    }


/*
    public void sendInvitationByMail(String emailRecipient, String subject, String content) throws UnknownHostException, UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip);
        String recipient = emailRecipient;
        String sender = "FootballApp@gmail.com";
        String host = "127.0.0.1";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
*/

    public void sendInvitationByMail(String recipent, String subject, String content) {
        System.out.println("prepare");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
       // properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "footballappteam@gmail.com";
        String password = "bestTeam123";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session,myAccountEmail, recipent, subject, content);
        try {
            Transport t = session.getTransport("smtp");
            t.connect(myAccountEmail, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            System.out.println("Message sent!");
        }
        catch(MessagingException ms){
            ms.printStackTrace();
        }

    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipent, String subject, String content) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipent));
            message.setSubject(subject);
            message.setText(content);
            return message;
        }
        catch(MessagingException ex){
         //   Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }


    public LinkedList<PersonalInfo> getPersonalPages() {
        LinkedList<PersonalInfo> pi = new LinkedList<PersonalInfo>();
        pi.addAll(personalPages.values());
        return (pi);
    }

}



