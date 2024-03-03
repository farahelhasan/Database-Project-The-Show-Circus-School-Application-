package com.example.circusschool;

public class Student {
      String Address;
      String Email;
      String FirstName;
      String Gender;
      String LastName;
      int Phone;
      int SSN;

      static String SAddress;
      static   String SEmail;
      static String SFirstName;
      static String SGender;
      static String SLastName;
      static int SPhone;
      static int Sssn;
    public Student (int SSN, String FirstName , String LastName, String Gender, String Email, int Phone, String Address ){
        this.SSN = SSN;
        this.Address = Address;
        this.Email = Email;
        this.FirstName = FirstName;
        this.Gender = Gender;
        this.LastName = LastName;
         this.Phone = Phone;

    }

    public Student(){

    }
    public String getAddress() {
        return Address;
    }

    public int getPhone() {
        return Phone;
    }

    public int getSSN() {
        return SSN;
    }

    public String getEmail() {
        return Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getGender() {
        return Gender;
    }

    public String getLastName() {
        return LastName;
    }

}
