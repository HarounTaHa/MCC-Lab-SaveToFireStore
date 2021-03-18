package com.example.storetofirestore.model;

public class PersonDetails {
        String name;
        String number;
        String address;

        public PersonDetails() {

        }

        public PersonDetails(String name, String number, String address) {
                this.name = name;
                this.number = number;
                this.address = address;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getNumber() {
                return number;
        }

        public void setNumber(String number) {
                this.number = number;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }
}
