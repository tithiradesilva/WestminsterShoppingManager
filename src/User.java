public class User {

        private String username; // Initialize variables
        private String password;

        public User(String username, String password) { // Constructors
            this.username = username;
            this.password = password;
        }

        // Getter for username
        public String getUsername() {
            return username;
        }

        // Getter for password
        public String getPassword() {
            return password;
        }
}
