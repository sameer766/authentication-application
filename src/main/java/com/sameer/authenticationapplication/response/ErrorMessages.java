package com.sameer.authenticationapplication.response;

public enum ErrorMessages {
  MISSING_REQUIRED_FIELD("Missing required field please check the docs"),
  RECORD_ALREADY_EXISTS("Record already exists"),
  INTERNAL_SERVER_ERROR("Internal Server Error"),
  NO_RECORD_FOUND("No Record Found"),
  AUTHENTICATION_FAILED("Authentication Failed"),
  COULD_NOT_UPDATE_RECORD("Could Not Update Record"),
  COULD_NOT_DELETE_RECORD("Could Not Delete Record"),
  EMAIL_ADDRESS_NOT_VERIFIED("Email Address not verified");

  private String errorMessage;

  ErrorMessages(String s) {
    this.errorMessage = s;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
