package homeTry2.User;

public class Company {
    private String name;
    private String catchPhrase;
    private String bs;

    public Company() {
        this.name = "company name is not specified";
        this.catchPhrase = "catchPhrase is not specified";
        this.bs = "bs is not specified";
    }

    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
}
