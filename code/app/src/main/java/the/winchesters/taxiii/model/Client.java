package the.winchesters.taxiii.model;

import static the.winchesters.taxiii.model.Role.CLIENT;


public class Client extends User{
    public Client(String username, String firstName, String lastName, String phoneNumber) {
        super(CLIENT,username, firstName, lastName, phoneNumber);
    }

}
