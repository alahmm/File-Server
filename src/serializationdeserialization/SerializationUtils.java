package serializationdeserialization;

import java.io.*;
import java.util.Arrays;

class SerializationUtils {
    //private transient String nonSerializedField;//to prevent an onject from beeing serialized
    //private static final long serialVersionUID = 7L;//to make sure if the version number of sender and receiver classes match
    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);//for speeding up the I/O operations
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
    public static Citizen[] initCitizens() {
        Citizen mark = new Citizen();
        mark.setName("Mark Olson");
        mark.setPassport("503143798"); // the passport was set

        Address markAddress = new Address();
        markAddress.setState("Arkansas");
        markAddress.setCity("Conway");
        markAddress.setStreet("1661  Dawson Drive");

        mark.setAddress(markAddress);

        Citizen anna = new Citizen();
        anna.setName("Anna Flores");
        anna.setPassport("605143321"); // the passport was set

        Address annaAddress = new Address();
        annaAddress.setState("Georgia");
        annaAddress.setCity("Atlanta");
        annaAddress.setStreet("4353  Flint Street");

        anna.setAddress(annaAddress);

        return new Citizen[]{ mark, anna };
    }
    public static void main(String[] args) {
        String filename = "citizens.data";
        try {
            SerializationUtils.serialize(initCitizens(), filename);
            Citizen[] citizens = (Citizen[]) SerializationUtils.deserialize(filename);
            System.out.println(Arrays.toString(citizens));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
class Citizen implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Address address;
    private transient String passport;

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
    // getters and setters

    @Override
    public String toString() {
        return "Citizen{" +
                "name='" + name + '\'' +
                ", passport='" + passport + '\'' +
                ", address=" + address +
                '}';
    }
}
class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private String state;
    private String city;
    private String street;

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    // getters and setters

    @Override
    public String toString() {
        return "Address{" +
                "state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}