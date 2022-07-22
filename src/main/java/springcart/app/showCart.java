package springcart.app;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("userCart")
public class showCart {
    private static final Logger logger = LoggerFactory.getLogger(showCart.class);

    private String[] output;
    private String deleteItem;
    private Path path;

    public String getDeleteItem() {
        return deleteItem;
    }

    public void setDeleteItem(String deleteItem) {
        this.deleteItem = deleteItem;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String[] getOutput() {
        return output;
    }

    public void setOutput(String[] output) {
        this.output = output;
    }

    public void showCart() {
    }

    public void sortCart(Path path, int itemToSort, Cart cart) {
        List<String> currLine = null;
        String[] output = null;
        try {
            currLine = Files.readAllLines(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        System.out.println("index to move up: " + itemToSort);
        for (String item : currLine) {
            System.out.println("1. " + item);
        }
        
        String removedItem;
        if(itemToSort>=2){
            removedItem = currLine.remove(itemToSort);        
            currLine.add(itemToSort-1, removedItem+"");

        }
        
        for (String item : currLine) {
            System.out.println("2. " + item);
        }

        int count = 0;
        try {
            output = new String[currLine.size()];      //at this point, currLine has [apple,oranges,bananas]
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
            for (String thing : currLine) {
                writer.write(thing + "\n"); //Updating to file.
                //Preparing to write out to HTML
                output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                         
                count++;
            }
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //Makes it so that page does not print first row of .txt file ("item name, quantity")
        String[] output2 = new String[output.length - 1];
         for (int i = 1; i < output.length; i++) {
             output2[i-1] = output[i];
         }
         output = output2.clone();

         cart.setOutput(output);

    }

    public void editItem(Path path, int editItem, Cart cart) {
        List<String> currLine = null;
        String[] output = null;
        try {
            currLine = Files.readAllLines(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        System.out.println("In editItem method: " + cart.getItem());

        String temp = currLine.get(editItem).split(",")[1];
        currLine.set(editItem, cart.getItem() + "," + temp);
        for (String string : currLine) {
            System.out.println(string);
        }

        int count = 0;
        try {
            output = new String[currLine.size()];      //at this point, currLine has [apple,oranges,bananas]
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
            for (String thing : currLine) {
                writer.write(thing + "\n"); //Updating to file.
                //Preparing to write out to HTML
                output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                         
                count++;
            }
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //Makes it so that page does not print first row of .txt file ("item name, quantity")
        String[] output2 = new String[output.length - 1];
         for (int i = 1; i < output.length; i++) {
             output2[i-1] = output[i];
         }
         output = output2.clone();

         cart.setOutput(output);
    }

    public void deleteItem(Path path, int deletedItem, Cart cart) {
        List<String> currLine = null;
        String[] output = null;
        try {
            currLine = Files.readAllLines(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        currLine.remove(deletedItem);

        int count = 0;
        try {
            output = new String[currLine.size()];      //at this point, currLine has [apple,oranges,bananas]
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
            for (String thing : currLine) {
                writer.write(thing + "\n"); //Updating to file.
                //Preparing to write out to HTML
                output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                         
                count++;
            }
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //Makes it so that page does not print first row of .txt file ("item name, quantity")
        String[] output2 = new String[output.length - 1];
         for (int i = 1; i < output.length; i++) {
             output2[i-1] = output[i];
         }
         output = output2.clone();

         cart.setOutput(output);
    }

    public void readFile(Path path) {
        try {
            System.out.println("Directory exists: " + Files.exists(path)); //Check whether directory exists
            if(!Files.exists(path)) {
                Files.createFile(path); //If file doesn't exist, create it
                System.out.println("New path/file created at " + path.toAbsolutePath());
                //File exists for the first time, add item name and quantity headers
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
                writer.write("item name,quantity");
                writer.close();
            }
        } catch (Exception e) {
                logger.error(e.getMessage());

        }
    }

    public void generateCart(Path path, Cart cart) {
        List<String> currLine = null;
        String[] output = null;
        try {
            int count = 0;
            currLine = Files.readAllLines(path); //Reading all lines from file.
            output = new String[currLine.size()];//Initializing String[array]
            for (String thing : currLine) {
                System.out.println("(GENERATE CART) READING FROM FILE: " + thing + "\n");
                //Populating String[] array with lines from the file.
                output[count] = thing.split(",")[0]            //A pple X 5
                .substring(0, 1)
                .toUpperCase() + thing.split(",")[0]           //Making first letter of each cart item uppercase
                .substring(1) +  " X " + thing
                .split(",")[1];                    
                count++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //Makes it so that page does not print first row of .txt file ("item name, quantity")
        String[] output2 = new String[output.length - 1];
        for (int i = 1; i < output.length; i++) {
             output2[i-1] = output[i];
         }
         output = output2.clone();

         cart.setOutput(output);
    }

    public void addutuCart(Path path, Cart cart) {
        List<String> currLine = null;
        String[] output = null;
        try {
            currLine = Files.readAllLines(path);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if(currLine != null) {       //the file that's read is NOT empty
            for (int i = 1; i < currLine.size(); i++) {             //ignores the first row, ie "item name,quantity"
                System.out.println("(ADDUTUCART) READING FROM FILE: " + currLine.get(i));
                if(currLine.get(i).split(",")[0].equals(cart.getItem())) {       //If item exists, increment quantity by 1
                    String[] temp = currLine.get(i).split(",");                  //eg apple,1 -> [apple,1]
                    int tempInt = Integer.parseInt(temp[1]) + 1;                       //Convert to int to increment
                    temp[1] = "" + tempInt;                                            //Convert back to String
                    currLine.set(i, temp[0] + "," + temp[1]);                          //replace apple,1 with apple,2 because cart.getItem() is apple
                }
            }

            System.out.println(" testttttttt"+ currLine.toString());
            //Adding new item
            if(!currLine.toString().contains(cart.getItem())) { //if [apple,oranges] does NOT contain "bananas", 
                currLine.add(cart.getItem() + ",1");            //add "bananas,1"
            } else {
                boolean flag = false;
                for (int i = 0; i < currLine.size(); i++) {
                    if(!currLine.get(i).split(",")[0].equals(cart.getItem())) {
                        flag = false;
                    } else {
                        flag = true;
                        break;
                    }
                }
                if(flag == false)
                    currLine.add(cart.getItem() + ",1");            //add "bananas,1"
            }

            int count = 0;
            try {
                output = new String[currLine.size()];      //at this point, currLine has [apple,oranges,bananas]
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
                for (String thing : currLine) {
                    System.out.println("New: " + thing + "\n");
                    writer.write(thing + "\n"); //Updating to file.
                    //Preparing to write out to HTML
                    output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                         
                    count++;
                }
                writer.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        
        //Makes it so that page does not print first row of .txt file ("item name, quantity")
        String[] output2 = new String[output.length - 1];
         for (int i = 1; i < output.length; i++) {
             output2[i-1] = output[i];
         }
         output = output2.clone();

         cart.setOutput(output);
    }

}
