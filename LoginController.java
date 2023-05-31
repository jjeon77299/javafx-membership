package application;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {
	/*
	체크박스 : adminCheckBox
	사용자 아이디 : useridTextField
	사용자 암호 : passwordPasswordField
	로그인 버튼 : loginButton
	회원가입 버튼 : registrationButton
	취소 버튼 : cancelButton
	창닫기 버튼 : closeButton
	 */
	@FXML
	private CheckBox adminCheckBox;
	@FXML
	private TextField useridTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private Button loginButton;
	@FXML
	private Button registrationButton;
	@FXML
	private Button cancelButton;
	@FXML
	private	Button closeButton;
	@FXML
	private	Label loginMessageLable;
	//사용자 로그인 체크
	boolean isUserLogin = false;
	
	// 창 닫기 버튼 클릭 이벤트
	public void closeButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void loginButtonOnAction(ActionEvent e) {
		loginMessageLable.setText("사용자 아이디와 암호를 검사합니다...");
		
		if(loginButton.getText().equals("로그인") == true) {
			if(useridTextField.getText().isBlank() == false
					&& 
					passwordPasswordField.getText().isBlank() == false) { //아이디와 암호가 입력되어 있는지 체크
				if(adminCheckBox.isSelected() == true){ //관리자 로그인
					loginMessageLable.setText("관리자 로그인...");
					validateAdminLogin();
				} else { //일반 사용자 로그인
					loginMessageLable.setText("사용자 로그인...");
					validateMemberLogin();
				}
			} else { //아이디와 암호가 없음
				loginMessageLable.setText("아이디와 암호를 입력하주세요");
			}
		} else if(loginButton.getText().equals("로그아웃") == true) {
			loginMessageLable.setText("로그아웃...");
			logout();
		}
	}
	
	public void adminCheckBoxOnAction(ActionEvent e) {
		if(adminCheckBox.isSelected() == true) { //관리자 체크 On
			registrationButton.setText("관리자 회원관리");
			registrationButton.setDisable(true);
			useridTextField.setText("");
			passwordPasswordField.setText("");
		} else { //관리자 체크 Off(일반 사용자)
			registrationButton.setText("회원가입");
			registrationButton.setDisable(false);
			useridTextField.setText("");
			passwordPasswordField.setText("");
		}
	}
	
	public void registrationButtonOnAction(ActionEvent e) {
		try {
			if(adminCheckBox.isSelected() == true) { //관리자 로그인 부분
			Parent root1 = FXMLLoader.load(getClass().getResource("membership.fxml"));
			Stage membershipStage = new Stage();
			membershipStage.setTitle("관리자 회원관리 모듈");
			membershipStage.setScene(new Scene(root1,600,400));
			membershipStage.getIcons().add(new Image("application/instagram"));
			membershipStage.show();
			} else { //사용자 로그인 부분
				if(isUserLogin == true) { //회원정보 수정 모듈 (사용자 로그인 성공)
					Parent root2 = FXMLLoader.load(getClass().getResource("modification.fxml"));
					Stage modificationStage = new Stage();
					modificationStage.setTitle("사용자 회원정보 수정 모듈");
					modificationStage.setScene(new Scene(root2,600,400));
					modificationStage.getIcons().add(new Image("application/instagram"));
					modificationStage.show();
				} else { // 회원가입 모듈 (사용자 로그인 안된 상황)
					Parent root3 = FXMLLoader.load(getClass().getResource("registration.fxml"));
					Stage registrationStage = new Stage();
					registrationStage.setTitle("사용자 회원가입 모듈");
					registrationStage.setScene(new Scene(root3,600,400));
					registrationStage.getIcons().add(new Image("application/instagram"));
					registrationStage.show();
				}
			}
		} catch (Exception err) {
			
		}
	}
	
	public void cancelButtonOnAction(ActionEvent e) {
		adminCheckBox.setSelected(false);
		useridTextField.setText("");
		passwordPasswordField.setText("");
	}
	
	public boolean isAdminLogin() {
		DBConnection connDB = new DBConnection();
		Connection conn = connDB.getConnection();
		
		//관리자 로그인 체크
		String sql = "select admin_id, admin_password "
					+ "from admin_accounts "
					+ "where admin_id=? and admin_password=?";
		//로그인 성공 여부
		boolean result = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, useridTextField.getText());
			pstmt.setString(2, passwordPasswordField.getText());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.executeQuery();
			
			//검사
			//select 문에서 관리자 아이디와 암호로 디비 row를 찾았는가?
			if(rs.next()) {
				result = true;
			}
			
			
			//소멸자
			pstmt.close();
			rs.close();
			conn.close();
		} catch(Exception err) {
			
		}
		return result;
	}
	
	public boolean isMemberLogin() {
		DBConnection connDB = new DBConnection();
		Connection conn = connDB.getConnection();
		
		//관리자 로그인 체크
		String sql = "select user_id, user_password "
					+ "from member_accounts "
					+ "where user_id=? and user_password=?";
		//로그인 성공 여부
		boolean result = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, useridTextField.getText());
			pstmt.setString(2, passwordPasswordField.getText());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.executeQuery();
			
			//검사
			//select 문에서 관리자 아이디와 암호로 디비 row를 찾았는가?
			if(rs.next()) {
				result = true;
				isUserLogin = true;
				Main.global_user_id = useridTextField.getText();
			}
			
			
			//소멸자
			pstmt.close();
			rs.close();
			conn.close();
		} catch(Exception err) {
			
		}
		return result;
	}
	
	void validateAdminLogin() {
		if(isAdminLogin() == true) { //관리자 로그인 성공
			loginMessageLable.setText("관리자 로그인 성공!! 환영합니다^^");
			useridTextField.setText("");
			passwordPasswordField.setText("");
			registrationButton.setDisable(false);
			loginButton.setText("로그아웃");
		} else { //관리자 로그인 실패
			loginMessageLable.setText("관리자 아이디 또는 암호가 잘못됐습니다!");
		}
	}
	
	void validateMemberLogin() {
		if(isMemberLogin() == true) { //사용자 로그인 성공
			loginMessageLable.setText("사용자 로그인 성공!! 환영합니다^^");
			useridTextField.setText("");
			passwordPasswordField.setText("");
			loginButton.setText("로그아웃");
			registrationButton.setText("회원정보 수정");
		} else { //사용자 로그인 실패
			loginMessageLable.setText("사용자 아이디 또는 암호가 잘못됐습니다!");
		}
	}
	
	void logout() {
		loginButton.setText("로그인");
		registrationButton.setText("회원가입");
		adminCheckBox.setSelected(false);
		isUserLogin = false;
		Main.global_user_id = null;
	}
}
