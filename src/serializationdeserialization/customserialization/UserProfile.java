package serializationdeserialization.customserialization;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class UserProfile implements Serializable {
    private static final long serialVersionUID = 26292552485L;

    private String login;
    private String email;
    private transient String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    // implement readObject and writeObject properly

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.defaultWriteObject();
        String encryptPassword = encrypt(password);
        oos.writeObject(encryptPassword);
    }

    public String encrypt(String inputText) {
        char[] listOfChars = new char[inputText.length()];
        for (int i = 0; i < inputText.length(); i++) {
            int step = inputText.charAt(i) + 1;
            listOfChars[i] = (char) step;
        }
        return String.valueOf(listOfChars);
    }
    public static String decrypt(String inputText) {
        char[] listOfChars = new char[inputText.length()];
        for (int i = 0; i < inputText.length(); i++) {
            int step = inputText.charAt(i) - 1;
            listOfChars[i] = (char) step;
        }
        return String.valueOf(listOfChars);
    }

    private void readObject(ObjectInputStream ois) throws Exception {
        ois.defaultReadObject();
        password = decrypt((String) ois.readObject());//initializing transient variable
    }
}

