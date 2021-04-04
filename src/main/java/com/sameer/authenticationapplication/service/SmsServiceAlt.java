//package com.sameer.authenticationapplication.service;//package com.sameer.authenticationapplication.service;
//
//
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//import com.amazonaws.services.simpleemail.model.*;
//import com.sameer.authenticationapplication.shared.UserDto;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SmsServiceAlt {
//
////  @Value("${ip_val:54-197-47-150}")
////  private String ip_val;
////  @Value("${port:0}")
////  private int port;
//
//  private final String ip_val = "54-197-47-150";
//  private final int port = 0;
//
//  private String TEXT_BODY;
//  private String PASSWORD_RESET_TEXT_BODY;
//  private String PASSWORD_RESET_HTML_BODY;
//  private String HTML_BODY;
//
//
//  private void intitializeLocalValue() {
//
//    HTML_BODY = "<h1>Please verify your email address</h1>"
//        + "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
//        + " click on the following link: "
//        + "<a href='http://localhost:"
//        + port
//        + "/email-verification.html?token=$tokenValue'>"
//        + "Final step to complete your registration"
//        + "</a><br/><br/>"
//        + "Thank you! And we are waiting for you inside!";
//
//
//    TEXT_BODY = " Please Verify your email-address"
//        + "click on the link http://localhost:"
//        + port
//        + "/email-verification.html?token=$tokenValue";
//
//
//    PASSWORD_RESET_TEXT_BODY = " A request to update your password"
//        + "Hi firstName!"
//        + "click on the link http://localhost:"
//        + port
//        + "/password-reset.html?token=$tokenValue";
//
//    PASSWORD_RESET_HTML_BODY = "<h1>A request to update your password</h1>"
//        + "<p>Hi firstName! "
//        + " click on the following link: "
//        + "<a href='http://localhost:"
//        + port
//        + "/password-reset.html?token=$tokenValue'>"
//        + "password verification link"
//        + "</a><br/><br/>"
//        + "Thank you! ";
//  }
//
//
//  private void intitializeAWSValue() {
//
//
//    TEXT_BODY = " Please Verify your email-address"
//        + "click on the link http://ec2-"
//        + ip_val
//        + ".compute-1.amazonaws.com:8080/verification-service/email-verification.html?token=$tokenValue";
//
//    HTML_BODY = "<h1>Please verify your email address</h1>"
//        + "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
//        + " click on the following link: "
//        + "<a href='http://ec2-"
//        + ip_val
//        + ".compute-1.amazonaws.com:8080/verification-service/email-verification.html?token=$tokenValue'>"
//        + "Final step to complete your registration"
//        + "</a><br/><br/>"
//        + "Thank you! And we are waiting for you inside!";
//
//
//    PASSWORD_RESET_TEXT_BODY = " A request to update your password"
//        + "Hi firstName!"
//        + "click on the link http://ec2-"
//        + ip_val
//        + ".compute-1.amazonaws.com:8080/verification-service/password-reset.html?token=$tokenValue";
//
//    PASSWORD_RESET_HTML_BODY = "<h1>A request to update your password</h1>"
//        + "<p>Hi firstName! "
//        + " click on the following link: "
//        + "<a href='http://ec2-"
//        + ip_val
//        + ".compute-1.amazonaws.com:8080/verification-service/password-reset.html?token=$tokenValue'>"
//        + "password verification link"
//        + "</a><br/><br/>"
//        + "Thank you! ";
//  }
//
//  final String FROM = "pandesameer76@gmail.com";
//  final String SUBJECT = "One last step to complete Verification";
//  final String PASSWORD_RESET_SUBJECT = "Reset password confirmation";
//
//
//  public void verifyEmail(UserDto userDto) {
////    intitializeLocalValue();
//    intitializeAWSValue();
//    System.setProperty("aws.accessKeyId", "AKIAYDSA5CSIJTCXKCKX");
//    System.setProperty("aws.secretKey", "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");
//    AmazonSimpleEmailService amazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder.standard()
//        .withRegion(Regions.US_EAST_1).build();
//    String htmlBodyToken = HTML_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
//    String textBodyToken = TEXT_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
//
//
//    SendEmailRequest request = new SendEmailRequest()
//        .withDestination(
//            new Destination().withToAddresses(userDto.getEmail()))
//        .withMessage(new Message()
//                         .withBody(new Body()
//                                       .withHtml(new Content()
//                                                     .withCharset("UTF-8").withData(htmlBodyToken))
//                                       .withText(new Content()
//                                                     .withCharset("UTF-8").withData(textBodyToken)))
//                         .withSubject(new Content()
//                                          .withCharset("UTF-8").withData(SUBJECT)))
//        .withSource(FROM);
//    amazonSimpleEmailService.sendEmail(request);
//    System.out.println("Email sent!");
//  }
//
//
//  public boolean sendPasswordResetRequest(String firstName, String email, String token) {
//
//    boolean returnVal = false;
//    System.setProperty("aws.accessKeyId", "AKIAYDSA5CSIJTCXKCKX");
//    System.setProperty("aws.secretKey", "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");
//    AmazonSimpleEmailService amazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder.standard()
//        .withRegion(Regions.US_EAST_1).build();
//    String htmlBodyToken = PASSWORD_RESET_HTML_BODY.replace("firstName", firstName)
//        .replace("$tokenValue", token);
//    String textBodyToken = PASSWORD_RESET_TEXT_BODY.replace("firstName", firstName)
//        .replace("$tokenValue", token);
//
//
//    SendEmailRequest request = new SendEmailRequest()
//        .withDestination(
//            new Destination().withToAddresses(email))
//        .withMessage(new Message()
//                         .withBody(new Body()
//                                       .withHtml(new Content()
//                                                     .withCharset("UTF-8").withData(htmlBodyToken))
//                                       .withText(new Content()
//                                                     .withCharset("UTF-8").withData(textBodyToken)))
//                         .withSubject(new Content()
//                                          .withCharset("UTF-8").withData(PASSWORD_RESET_SUBJECT)))
//        .withSource(FROM);
//    SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(request);
//    if (sendEmailResult != null
//        && sendEmailResult.getMessageId() != null
//        && !sendEmailResult.getMessageId().isEmpty()) {
//      returnVal = true;
//      System.out.println("Email sent!");
//    }
//    return returnVal;
//  }
//}
