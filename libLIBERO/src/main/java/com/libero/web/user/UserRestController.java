package com.libero.web.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.libero.service.domain.User;
import com.libero.service.user.UserService;

//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//Constructor
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	//method
	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(@RequestBody Map<String, Object> params, HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+params.get("userId"));
		User user = userService.getUser((String) params.get("userId"));
		
		if( ((String)params.get("password")).equals(user.getPassword())){
			session.setAttribute("user", user);
		}
		
		return user;
	}
	
	@RequestMapping(value="json/emailSend",method=RequestMethod.GET)
	public Map emailSend(@RequestParam("userId") String userId) throws Exception{
		System.out.println(" ---------------------------------------");
		System.out.println("[ /user/json/emailCheck/"+userId+" : GET]");
		System.out.println(" ---------------------------------------");
		
		////email보내는 함수 작성
		System.out.println("\n\n"+userId+"\n\n"); 
		String verCode = mailSender(userId);
		
		System.out.println("\n\n[ "+verCode+" ]\n\n");
		
		Map map = new HashMap();
		map.put("verifCode", verCode);
		
		////리턴 값 설정 해주기.
		return map;
	}
	
	public String mailSender(String userId) throws AddressException, MessagingException {
		
		String host = "smtp.naver.com"; 
		final String username = "wjddbstp"; //네이버 아이이디 
		final String password = "mnbv4845!!"; //네이버 비번 
		int port=465; 
		String verCode=UserRestController.getAlphaNumericString();
		String recipient = userId; //받는 사람 이메일 주소 
		String subject = "이메일 인증입니다."; //메일 제목 
		String body = "libLIBERO Email Verification Code\n\t\t"+"[ "+ verCode+" ]"; //메일 내용
		Properties props = System.getProperties(); // 메일 제목, 내용을 담을 properties 만들기. 
		
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String un=username; 
			String pw=password; 
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() { 
				return new javax.mail.PasswordAuthentication(un, pw);
				} }); 
		session.setDebug(true); //for debug 
		Message messageContent = new MimeMessage(session); 
		//MimeMessage 
			
			String email = "wjddbstp@naver.com";	
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			messageContent.setFrom(emailAddr); 
			 // 보내기 
		
			//발신자  이메일 세팅
			messageContent.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //수신자 세팅 
			messageContent.setSubject(subject); //제목 세팅 
			messageContent.setText(body); //내용 세팅
			Transport.send(messageContent);	
		//javax.mail.Transport.send() 이용하는 거임
		return verCode;
	}
	
	
///////////////////////////////////////////랜덤 코드 생성기
	static String getAlphaNumericString() { 
		
		
		 int n = 10; //length of the number
        
		
		// chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString.charAt(index)); 
        }
        return sb.toString(); 
    } 
	
	
	
}