package FlashGameProject;

public class JoinDTO {
      private String id,username,password,passwordre ,email, phone;
        
         
         @Override
         public String toString() {
            return "LoginDTO [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
                  + ", birth=" + ", phone=" + phone + ", passwordre=" + passwordre;
         }
         public String getId() {
            return id;
         }
         public void setId(String id) {
            this.id = id;
         }
         public String getUsername() {
            return username;
         }
         public void setUsername(String username) {
            this.username = username;
         }
         public String getPassword() {
            return password;
         }
         public void setPassword(String password) {
            this.password = password;
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
         public String getPasswordre() {
            return passwordre;
         }
         public void setPasswordre(String passwordre) {
            this.passwordre = passwordre;
         }
   }