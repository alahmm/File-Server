package serializationdeserialization.customserialization;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    String userName = "admin";
    transient String password = "password";

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.defaultWriteObject();
        //String encryptPassword = encrypt(password);
        //oos.writeObject(encryptPassword);
    }

    private void readObject(ObjectInputStream ois) throws Exception {
        ois.defaultReadObject();
        //password = decrypt((String) ois.readObject());//initializing transient variable
    }
}
