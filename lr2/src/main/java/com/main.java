package com;

import com.phone.Phone;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;


@Component
@EnableAsync
public class main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("com");

        Phone phone = context.getBean(Phone.class);

        Scanner sc = new Scanner(System.in);
        String command;
        String state;

        String help =
                "\nhelp\t\t\t\tshow this text\n" +
                        "addMoney <float>\tadd money to the phone`s balance\n" +
                        "showBalance\t\t\tshow your phone`s balance\n" +
                        "answerCall\t\t\tanswer to incoming call\n" +
                        "call <number>\t\tcall to number (only if balance more then 0.5)\n" +
                        "endCall\t\t\t\tend talking\n" +
                        "turnOff\t\t\t\tturn off your phone\n";

        String telephoneNumber;
        float deposit = 0;
        Instant start = Instant.now();
        Instant finish;

        System.out.println(help);
        System.out.println("Phone is turn on!");

        while (true) {
            System.out.print("Enter command: ");

            command = sc.next();

            switch (command) {
                case "showBalance":
                    System.out.println("Your balance: " + phone.getBalance());
                    break;

                case "turnOff":
                    System.out.println("Turning off... Good bye!");
                    return;

                case "addMoney":
                    deposit = sc.nextFloat();

                    if (phone.addMoney(deposit)) {
                        System.out.print("The balance is replenished!\n");
                    } else {
                        System.out.print("Something went wrong! " +
                                "May be you entered a negative amount or a zero?\n");
                    }
                    break;

                case "call":
                    telephoneNumber = sc.next();

                    if (phone.call(telephoneNumber) == null) {
                        System.out.println("Can not call!");
                    } else {
                        start = Instant.now();
                        System.out.println("Calling on number: " + telephoneNumber);
                    }
                    break;

                case "answerCall":

                    if (phone.clickAnswer() == null) {
                        System.out.println("Can not answer!");
                    } else {
                        System.out.println("Answer...");
                        start = Instant.now();
                    }
                    break;

                case "endCall":
                    state = phone.clickEnd();
                    if (state == null) {
                        System.out.println("Can not end call!");
                    } else {
                        finish = Instant.now();

                        long elapsed = -(Duration.between(finish, start).toMillis()) / 1000;
                        System.out.println("Your phone conversation lasted: " + elapsed + " seconds");

                        System.out.println("Call is end.");
                    }
                    break;

                case "help":
                    System.out.println(help);
                    break;

                default:
                    System.out.println("You entered the wrong command\n" +
                            "Enter 'help' to get information about the program.");
                    break;
            }
        }
    }
}