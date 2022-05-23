package hadj.tn.test.Model;

import java.util.HashSet;
import java.util.Set;

public class User {


    private int id;
    private  String username;
    private  String email;
    private  String phone;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private String region;
    private String gpsCoord;
    private Boolean enabled=false;
    private String image;
    private String genre;
    private int age;
    private String poids;
    private String taille;
    private String typeSang;
    private int nbDemandeSang=0;
    private int nbDonSang=0;
    private int nbPoints=0;


    public User(int id) {
        this.id = id;
    }

    public String getGpsCoord() {
        return gpsCoord;
    }

    public void setGpsCoord(String gpsCoord) {
        this.gpsCoord = gpsCoord;
    }

    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWeight() {
        return poids;
    }

    public void setWeight(String weight) {
        this.poids = weight;
    }

    public String getHeight() {
        return taille;
    }

    public void setHeight(String height) {
        this.taille = height;
    }

    public String getTypeSang() {
        return typeSang;
    }

    public void setTypeSang(String bloodType) { typeSang = bloodType; }

    public int getNbBloodRequest() {
        return nbDemandeSang;
    }

    public void setNbBloodRequest(int nbBloodRequest) {
        this.nbDemandeSang = nbBloodRequest;
    }

    public int getNbBloodDonation() {
        return nbDonSang;
    }

    public void setNbBloodDonation(int nbBloodDonation) {
        this.nbDonSang = nbBloodDonation;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public User(String email) {
        this.email = email;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public User() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getAppUserRole() {
        return roles;
    }
    public void setAppUserRole(Set<Role> appUserRole) {
        this.roles = appUserRole;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}