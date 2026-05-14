package airline;

public class Passenger extends User {
    private String passengerId;
    private String cnic;
    private String passportId;
    private String address;
    private String gender;
    private String contactNumber;
    private String picturePath; // NEW: stores path to passenger photo

    public Passenger(String name, String userName, String passengerId,
                     String cnic, String passportId, String address,
                     String gender, String contactNumber) {
        super(name, userName, passengerId);
        this.passengerId   = passengerId;
        this.cnic          = cnic;
        this.passportId    = passportId;
        this.address       = address;
        this.gender        = gender;
        this.contactNumber = contactNumber;
        this.picturePath   = "";
    }

    public String getPassengerId()   { return passengerId; }
    public String getCnic()          { return cnic; }
    public String getPassportId()    { return passportId; }
    public String getAddress()       { return address; }
    public String getGender()        { return gender; }
    public String getContactNumber() { return contactNumber; }
    public String getPicturePath()   { return picturePath; }
    public void   setPicturePath(String p) { this.picturePath = p; }
}
