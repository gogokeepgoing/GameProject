package FlashGameProject;

import javax.swing.JTextField;

public class LoginDTO {

    private String id, password;
    
//    public LoginDTO(JTextField jtId, JTextField jtPassword) {
//        this.id=jtId.getText();
//        this.password = jtPassword.getText();
//    }

   public LoginDTO() {
      this.id= id;
        this.password = password;
      // TODO Auto-generated constructor stub
   }

   public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
       return "LoginDTO [id=" + id +", password = " + password + "]";
    }
}