import java.io.*;
public class UserUtils {
    private static final String USER_DETAILS_FILE = "userDetails.txt"; // filename where user details are stored.

    public static void saveUser(User user) throws IOException {

        // Check if the user already exists
        if (!isUserExists(user)) {

            // Append new user details to file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DETAILS_FILE, true))) {
                bw.write(user.getUsername() + ":" + user.getPassword() + "\n");
            }
        }
    }

    public static boolean isUserExists(User user) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DETAILS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Check if the line matches the user's username and password.
                if (line.equals(user.getUsername() + ":" + user.getPassword())) {
                    return true;
                }
            }
        } catch (IOException e) { // Return false if the user is not found in the file.
            e.printStackTrace();
        }
        return false;
    }
}
