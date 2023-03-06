package homeTry2;

import homeTry2.User.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //new InteractionApi2().getAllUsers();

        //new InteractionApi2().getUserById(1);

        //new InteractionApi2().getUserByUsername("Leopoldo_Corkery");

        User coolKid = new InteractionApi2().createUser("Boba Fett", "boba@fett.com");
        coolKid.setName("Cool Kid");
        coolKid.setAddress(new Address("Cool street", "Suite 99", "Cool-Kids-City", "1337", new Geo("111", "222")));
        coolKid.setCompany(new Company("Awesome Inc.", "leeets ggooooo!", "just-doing-cool-things"));

        //new InteractionApi2().uploadUser(coolKid);

        //new InteractionApi2().updateUser(coolKid,5);

        //new InteractionApi2().deleteUser(5);

        //new InteractionApi2().printSaveComments(7);

        new InteractionApi2().printOpenTasks(7);
    }
}
