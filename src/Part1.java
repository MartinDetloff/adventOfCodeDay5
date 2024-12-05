import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Part1 {

    private BufferedReader br;
    private HashMap<Integer, ArrayList<Integer>> comeAfterNums = new HashMap<>(); // maps an integer to all the numbers it must come after
    private HashMap<Integer, ArrayList<Integer>> comeBeforeNums = new HashMap<>(); // maps an integer to all the number it must come before
    private int counter = 0;
    public Part1(BufferedReader br){
        this.br = br;
    }

    private void loopThroughFile() throws IOException {
        String st;
        while ((st = br.readLine()) != null ){
            // if we are at the point where we want to start reading stop
            if (st.length() == 0){
                break;
            }

            String[] myNums = st.split("\\|");

            // check if the list for that num is empty. if yes, make one
            comeAfterNums.putIfAbsent(Integer.parseInt(myNums[1]), new ArrayList<>());
            comeAfterNums.get(Integer.parseInt(myNums[1])).add(Integer.parseInt(myNums[0]));

            // check if the list for that num is empty. if yes, make one
            comeBeforeNums.putIfAbsent(Integer.parseInt(myNums[0]), new ArrayList<>());
            comeBeforeNums.get(Integer.parseInt(myNums[0])).add(Integer.parseInt(myNums[1]));

//            printOutAfterNums();
//            printOutBeforeNums();

        }
        handleUpdates(); // Method to handle the updates
    }

    /**
     * Method to print out the numbers after
     */
    private void printOutAfterNums(){
        for (Integer num : comeAfterNums.keySet()){
            System.out.println("The number: " + num);
            ArrayList<Integer> tempList = comeAfterNums.get(num);

            for (Integer num1 : tempList){
                System.out.println(num1 + " Must come before " + num);
            }
        }
    }

    /**
     * Method to print out the numbers before
     */
    private void printOutBeforeNums(){
        for (Integer num : comeBeforeNums.keySet()){
            System.out.println("The number: " + num);
            ArrayList<Integer> tempList = comeBeforeNums.get(num);

            for (Integer num1 : tempList){
                System.out.println(num1 + " Must come after " + num);
            }
        }
    }

    /**
     * Method to handle the updates
     * @throws IOException
     */
    private void handleUpdates() throws IOException {
        String st;
        while ((st = br.readLine()) != null){
            String[] arrayOfNumsOnLine = st.split(",");
            ArrayList<Integer> myArray = turnStringArrayIntoInt(arrayOfNumsOnLine); // turn it into int array

            if (checkForGoodUpdate(myArray)){
//                counter++;
            }
        }
        System.out.println("This is the total good things " + counter);
    }


    /**
     * Method to check if a certain line is a good array
     * @param myArray the array
     */
    private boolean checkForGoodUpdate(ArrayList<Integer> myArray){
        for (int i = 0; i < myArray.size(); i ++){
            int tempIndex = i;
            int num = myArray.get(tempIndex);

            if (!allAfterAreFine(myArray, tempIndex, num) || !allBeforeAreFine(myArray, tempIndex, num)){
                return false;
            }
        }
        counter += myArray.get(myArray.size() / 2);
        return true;
    }

    /**
     * Method to check all the numbers after, and see if they are okay
     * @param myArray the array
     * @param index the index
     * @return true if all after are fine
     */
    private boolean allAfterAreFine(ArrayList<Integer> myArray, int index, int num){
        // we are already at the end
        if (index == myArray.size() - 1){
            return true;
        }
        ArrayList<Integer> numsAfter = comeBeforeNums.get(num);

        // inc by 1
        index += 1;

        for (int i = index; i < myArray.size(); i ++){
            if (!numsAfter.contains(myArray.get(i))){
                return false;
            }
        }


        return true;
    }

    /**
     * Method to check all the numbers before, and see if they are okay
     * @param myArray the array
     * @param index the index
     * @return true if all before are fine
     */
    private boolean allBeforeAreFine(ArrayList<Integer> myArray, int index, int num){
        // we are already at the beginning
        if (index == 0){
            return true;
        }
        ArrayList<Integer> numsBefore = comeAfterNums.get(num);
        // dec by 1
        index -= 1;

        for (int i = index; i >= 0; i --){
            if (!numsBefore.contains(myArray.get(i))){
                return false;
            }
        }

        return true;
    }


    /**
     * Method to convert the array of string to nums
     * @param arrayOfNumsOnLine the string array
     * @return arraylist of nums
     */
    private ArrayList<Integer> turnStringArrayIntoInt(String[] arrayOfNumsOnLine){
        ArrayList<Integer> nums = new ArrayList<>();
        for (String s : arrayOfNumsOnLine){
            nums.add(Integer.parseInt(s));
        }
        return nums;
    }





    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\19365\\OneDrive\\Documents\\GitHub\\adventOfCodeDay5\\src\\input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        Part1 part1 = new Part1(br);

        part1.loopThroughFile();

    }
}
