//Shahrizod Bobojonov, 114137520, Recitation 1

import java.util.InputMismatchException; //Scanner exception for invalid input
import java.util.Scanner; //import Scanner class to be able to take in input

/**
 * The PresentationManager class ties in the Slide, SlideListNode, and SlideList
 * classes all together and makes an easy-to-use console menu with various
 * commands using the methods from the other classes. Uses try-catch in order to
 * catch exceptions and give an error message before returning to the main menu
 *
 * @author Shahrizod Bobojonov
 */
public class PresentationManager {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean isOngoing = true;
        SlideList slideshow = new SlideList();
        System.out.println("Welcome to Presentation Manager!");
        while (isOngoing) {
            System.out.print("\nPlease select an option:\n" +
                                "F) Move cursor forward\n" +
                                "B) Move cursor backward\n" +
                                "D) Display cursor slide\n" +
                                "E) Edit cursor slide\n" +
                                "P) Print presentation summary\n" +
                                "A) Append new slide to tail\n" +
                                "I) Insert new slide before cursor\n" +
                                "R) Remove slide at cursor\n" +
                                "H) Reset cursor to head\n" +
                                "Q) Quit\n\n" +
                            "Select a menu option: ");
            try {
                String sChoice = stdin.nextLine();
                if (sChoice.length() != 1) throw new IllegalArgumentException();
                char choice = Character.toUpperCase(sChoice.charAt(0));
                System.out.println();
                if (choice == 'F') {
                    slideshow.cursorForward();
                    System.out.println("The cursor moved forward to slide \"" + slideshow.getCursorSlide().getTitle() + "\".");
                } else if (choice == 'B') {
                    slideshow.cursorBackward();
                    System.out.println("The cursor moved backwards to slide \"" + slideshow.getCursorSlide().getTitle() + "\".");
                } else if (choice == 'D') {
                    Slide s = slideshow.getCursorSlide();
                    String result = "=".repeat(60)+"\n";
                    result+="  " + s.getTitle()+ "\n";
                    result+="=".repeat(60)+"\n";
                    for (int i = 1; i <= s.getNumBullets(); i++) {
                        result+= i + ". " + s.getBullet(i) + "\n";
                    }
                    result+="=".repeat(60)+"\n";
                    System.out.println(result);
                } else if (choice == 'E') {
                    if (slideshow.size() == 0) throw new NullPointerException();
                    Slide s = slideshow.getCursorSlide();
                    System.out.print("Edit title, duration, or bullets? (t/d/b) ");
                    String sEdit = stdin.nextLine();
                    if (sEdit.length() > 1) throw new IllegalArgumentException();
                    char editChoice = Character.toLowerCase(sEdit.charAt(0));
                    if (editChoice != 't' && editChoice != 'd' && editChoice != 'b') throw new IllegalArgumentException();
                    if (editChoice == 't') {
                        System.out.print("Delete or edit (d/e) ");
                        String sEditType = stdin.nextLine();
                        if (sEditType.length() > 1) throw new IllegalArgumentException();
                        char editType = Character.toLowerCase(sEditType.charAt(0));
                        if (editType != 'd' && editType != 'e') throw new IllegalArgumentException();
                        if (editType == 'd') {
                            System.out.println("Title of \"" + s.getTitle() + "\" has been deleted");
                            s.setTitle("");
                        } else {
                            System.out.print("Enter new title: ");
                            String newTitle = stdin.nextLine();
                            System.out.println("Title of \"" + s.getTitle() + "\" has been changed to \"" + newTitle + "\"");
                            s.setTitle(newTitle);
                        }
                    } else if (editChoice == 'd') {
                        System.out.print("Delete or edit (d/e) ");
                        String sEditType = stdin.nextLine();
                        if (sEditType.length() > 1) throw new IllegalArgumentException();
                        char editType = Character.toLowerCase(sEditType.charAt(0));
                        if (editType != 'd' && editType != 'e') throw new IllegalArgumentException();
                        if (editType == 'd') {
                            if (s.getDuration() != -1) {
                                slideshow.updateDuration(s.getDuration() * -1);
                            }
                            s.deleteDuration();
                            System.out.println("Duration of \"" + s.getTitle() + "\" has been deleted");
                        } else {
                            System.out.print("Enter new duration: ");
                            double newDuration = stdin.nextDouble();
                            if (s.getDuration() == -1) {
                                slideshow.updateDuration(newDuration);
                            } else {
                                slideshow.updateDuration(newDuration - s.getDuration());
                            }
                            s.setDuration(newDuration);
                            System.out.println("Duration of \"" + s.getTitle() + "\" has been changed to " + s.getDuration());
                            stdin.nextLine(); //needed to clear Scanner buffer
                        }
                    } else {
                        System.out.print("Bullet index: ");
                        int bIndex = stdin.nextInt();
                        stdin.nextLine(); //needed to clear Scanner buffer
                        if (bIndex < 1 || bIndex > s.getCapacity() || bIndex > s.getNumBullets()+1) throw new IllegalArgumentException();
                        System.out.print("Delete or edit (d/e) ");
                        String sEditType = stdin.nextLine();
                        if (sEditType.length() > 1) throw new IllegalArgumentException();
                        char editType = Character.toLowerCase(sEditType.charAt(0));
                        if (editType != 'd' && editType != 'e') throw new IllegalArgumentException();
                        if (editType == 'd') {
                            if (bIndex == s.getNumBullets()+1) {
                                System.out.println("No bullet to delete");
                                throw new IllegalArgumentException();
                            }
                            s.setBullet(null, bIndex);
                            slideshow.updateNumBullets(-1);
                            System.out.println("Bullet " + bIndex + " has been deleted");
                        } else {
                            if (s.getBullet(bIndex) == null) {
                                slideshow.updateNumBullets(1);
                            }
                            System.out.print("Enter new bullet: ");
                            s.setBullet(stdin.nextLine(), bIndex);
                            System.out.println("Bullet " + bIndex + " has been edited");
                        }
                    }
                } else if (choice == 'P') {
                    String result = "Slideshow summary:\n";
                    result+="=".repeat(60)+"\n";
                    result+=String.format("%-2s%-8s%-26s%-12s%-12s"," ","Slide", "Title", "Duration", "Bullets");
                    result+="\n";
                    result+="-".repeat(60)+"\n";
                    int currentSlide = slideshow.getCursorNum();
                    slideshow.resetCursorToHead();
                    for (int i = 1; i <= slideshow.size(); i++) {
                        String arrow = "";
                        if (currentSlide == i) arrow="->";
                        result+=String.format("%-2s%-8d%-50s\n",arrow,i, slideshow.getCursorSlide());
                        if (i != slideshow.size()) slideshow.cursorForward();
                    }
                    slideshow.setCursorNum(currentSlide);
                    result+="=".repeat(60)+"\n";
                    result+="  Total:     " + slideshow.size() + " slide(s), " + String.format("%.2f", slideshow.duration()) + " minute(s), " + slideshow.numBullets() + " bullet(s) ";
                    result+="\n";
                    result+="=".repeat(60)+"\n";
                    System.out.println(result);
                } else if (choice == 'A') {
                    Slide s = addSlide(stdin);
                    slideshow.appendToTail(s);
                } else if (choice == 'I') {
                    Slide s  = addSlide(stdin);
                    slideshow.insertBeforeCursor(s);
                } else if (choice == 'R') {
                    System.out.println("Slide \"" + slideshow.removeCursor().getTitle() + "\" has been removed");
                } else if (choice == 'H') {
                    slideshow.resetCursorToHead();
                    System.out.println("Cursor has been set to head");
                } else if (choice == 'Q') {
                    System.out.println("Terminating program normally, goodbye...");
                    isOngoing = false;
                } else {
                    System.out.println("Invalid input, returning to main menu...");
                }
            } catch (IllegalArgumentException | InputMismatchException e) {
                e.printStackTrace();
                System.out.println("Invalid input");
            } catch (EndOfListException e) {
                System.out.println("You've reached the end of the list!");
            } catch (NullPointerException e) {
                System.out.println("No slide at cursor");
            }
        }
    }

    /**
     * Method to add a slide used in other methods such as in append to tail
     * and before cursor. It takes in the Scanner object in order to use the
     * same Scanner object throughout the class rather than creating several
     * instances. It prompts the user for the title, duration, and bullets of
     * the slide to be created and then creates a Slide object based on this
     * information and returns that object
     *
     * @param stdin
     *      the Scanner object to be used to take in user input
     * @return
     *      a Slide object created based on the user input
     * @throws IllegalArgumentException
     *      when the user provides invalid input (not "y"/"n")
     */
    public static Slide addSlide(Scanner stdin) throws IllegalArgumentException {
        Slide s = new Slide();
        System.out.print("Enter the slide title: ");
        s.setTitle(stdin.nextLine());
        System.out.print("Enter the duration of the slide: ");
        s.setDuration(stdin.nextDouble());
        stdin.nextLine(); //needed to clear scanner
        for (int i = 1; i <= s.getCapacity(); i++) {
            System.out.print("Bullet " + i + ": ");
            s.setBullet(stdin.nextLine(), i);
            if (i==s.getCapacity()) {
                System.out.println("No more bullets allowed. Slide is full.");
                break;
            }
            System.out.print("Add another bullet point? (y/n) ");
            String sAdd = stdin.nextLine();
            if (sAdd.length() > 1) throw new IllegalArgumentException();
            char willAdd = Character.toUpperCase(sAdd.charAt(0));
            if (willAdd == 'Y') {
                continue;
            } else if (willAdd == 'N') {
                break;
            } else {
                throw new IllegalArgumentException();
            }
        }
        System.out.println("\nSlide \"" + s.getTitle() + "\" added to presentation");
        return s;
    }

}
