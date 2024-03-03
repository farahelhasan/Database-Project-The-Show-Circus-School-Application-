package com.example.circusschool;

public class Trainer {

        String Email;
        String FirstName;
        String Gender;
        String LastName;
        int Phone;
        int SSN;
        String BankAccount;

        static   String SEmail;
        static String SFirstName;
        static String SGender;
        static String SLastName;
        static int SPhone;
        static int Sssn;
        static String SBankAccount;
        public Trainer (int SSN, String FirstName , String LastName, String Gender, String Email, int Phone , String BankAccount){
            this.SSN = SSN;
            this.BankAccount= BankAccount;
            this.Email = Email;
            this.FirstName = FirstName;
            this.Gender = Gender;
            this.LastName = LastName;
            this.Phone = Phone;

        }


        public String getBankAccount() {
        return BankAccount;
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


