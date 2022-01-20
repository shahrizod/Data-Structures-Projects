//Shahrizod Bobojonov 114137520 Recitation 1

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class ties the previous two classes together into a storage manager,
 * which allows us to simulate a storage locker facility, adding and removing
 * boxes, seeing what's stored, and searching for certain boxes or clients. In
 * addition, since the other two classes are serializable, we use that to load
 * in the "storage.obj" file, representing the previous state of the storage
 * locker facility. We can also then save to this file when quitting the program.
 */
public class StorageManager {
    private static StorageTable table = new StorageTable();
    public static void main(String[] args) {
        System.out.println("Hello, and welcome to Rocky Stream Storage Manager");
        Scanner stdin = new Scanner(System.in);
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream("storage.obj");
            ObjectInputStream inStream = null;
            inStream = new ObjectInputStream(fileIn);
            table = (StorageTable) inStream.readObject();
            inStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nNo existing file found, using new storage");
        }
        while (true) {
            System.out.println("\nP - Print all storage boxes\n" +
                                "A - Insert into storage box\n" +
                                "R - Remove contents from a storage box\n" +
                                "C - Select all boxes owned by a particular client\n" +
                                "F - Find a box by ID and display its owner and contents\n" +
                                "Q - Quit and save workspace\n" +
                                "X - Quit and delete workspace\n");
            System.out.print("Please select an option: ");
            String answer;
            try {
                answer = stdin.nextLine();
                if (answer.equalsIgnoreCase("P")) {
                    System.out.printf("\n%-13s%-31s%-20s\n", "Box#", "Contents", "Owner");
                    System.out.println("-".repeat(64));
                    for (Integer key : table.keySet()) {
                        Storage val = table.get(key);
                        System.out.printf("%-13d%-31s%-20s\n", val.getId(), val.getContents(), val.getClient());
                    }
                } else if (answer.equalsIgnoreCase("A")) {
                    System.out.print("Please enter id: ");
                    int id = stdin.nextInt();
                    while (id < 0 || table.containsKey(id)) {
                        System.out.print("Invalid input, please try again: ");
                        id = stdin.nextInt();
                    }
                    stdin.nextLine(); //clear buffer
                    System.out.print("Please enter client: ");
                    String client = stdin.nextLine();
                    while (client == null || client.isBlank()) {
                        System.out.print("Invalid input, please try again: ");
                        client = stdin.nextLine();
                    }
                    System.out.print("Please enter contents: ");
                    String contents = stdin.nextLine();
                    while (contents == null || contents.isBlank()) {
                        System.out.print("Invalid input, please try again: ");
                        contents = stdin.nextLine();
                    }
                    try {
                        table.putStorage(id, new Storage(id, client, contents));
                        System.out.println("\nStorage " + id + " set!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Uh-oh something went wrong, returning to main menu");
                    }
                } else if (answer.equalsIgnoreCase("R")) {
                    System.out.print("Please enter id: ");
                    int id = stdin.nextInt();
                    stdin.nextLine(); //clear buffer
                    Storage val = table.remove(id);
                    if (val == null)
                        System.out.println("No box at location, returning to main menu");
                    else
                        System.out.println("Box " + id + " is now removed");
                } else if (answer.equalsIgnoreCase("C")) {
                    System.out.print("Please enter the name of the client: ");
                    String client = stdin.nextLine();
                    System.out.printf("\n%-13s%-31s%-20s\n", "Box#", "Contents", "Owner");
                    System.out.println("-".repeat(64));
                    for (Integer key : table.keySet()) {
                        Storage val = table.get(key);
                        if (val.getClient().equals(client))
                            System.out.printf("%-13d%-31s%-20s\n", val.getId(), val.getContents(), val.getClient());
                    }
                } else if (answer.equalsIgnoreCase("F")) {
                    System.out.print("Please enter id: ");
                    int id = stdin.nextInt();
                    while (id < 0) {
                        System.out.print("Invalid input, please try again: ");
                        id = stdin.nextInt();
                    }
                    stdin.nextLine(); //clear buffer
                    Storage box = table.getStorage(id);
                    if (box == null) {
                        System.out.println("No box at location, returning to main menu");
                        continue;
                    }
                    System.out.println("Box " + id);
                    System.out.println("Contents: " + box.getContents());
                    System.out.println("Owner: " + box.getClient());
                } else if (answer.equalsIgnoreCase("Q")) {
                    try {
                        FileOutputStream fileOut = new FileOutputStream("storage.obj");
                        ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
                        outStream.writeObject(table);
                        outStream.close();
                        System.out.println("Storage manager is quitting, current storage is saved for next session");
                    } catch (IOException e) {
                        System.out.println("Uh-oh, something went wrong, returning to main menu");
                        continue;
                    }
                    break;
                } else if (answer.equalsIgnoreCase("X")) {
                    System.out.println("Storage manager is quitting, all data is being erased");
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, returning to main menu");
                stdin.nextLine(); //clear buffer
            }
        }
    }
}
