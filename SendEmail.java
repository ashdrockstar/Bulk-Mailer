import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
import javax.swing.JOptionPane;

class SendEmail{  
	public void send(String user,String password,
			String sendTo[],String subject,String msg,String imagePath){  

		int i;
		for(i=0;sendTo[i]!=null;i++)
		{
			String to=sendTo[i];//change accordingly  
			//1) get the session object     
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getDefaultInstance(props,  
					new javax.mail.Authenticator() {  
				protected PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication(user,password);  
				}  
			});  

			//2) compose message     
			try{  
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress(user));  
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				message.setSubject(subject);  

				//3) create MimeBodyPart object and set your message text     
				BodyPart messageBodyPart1 = new MimeBodyPart();  
				messageBodyPart1.setText(msg);  

				//4) create new MimeBodyPart object and set DataHandler object to this object      
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();  

				String filename = imagePath;//change accordingly  
				DataSource source = new FileDataSource(filename);  
				messageBodyPart2.setDataHandler(new DataHandler(source));  
				messageBodyPart2.setFileName("Picture.jpg");  


				//5) create Multipart object and add MimeBodyPart objects to this object      
				Multipart multipart = new MimeMultipart();  
				multipart.addBodyPart(messageBodyPart1);  
				multipart.addBodyPart(messageBodyPart2);  

				//6) set the multiplart object to the message object  
				message.setContent(multipart );  

				//7) send message  
				Transport.send(message);  

				//JOptionPane.showMessageDialog(null, "Mail sent to "+sendTo[i]);
			}catch (MessagingException ex) {
                            System.out.print(ex);
                        
                        
                        }  
		}  
                
                JOptionPane.showMessageDialog(null,i+" Mails Sent!!");
	}
}  