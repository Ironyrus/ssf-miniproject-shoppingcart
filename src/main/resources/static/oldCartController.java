package springcart.app;

import java.io.BufferedWriter;

//mvn spring-boot:run -Dspring-boot.run.arguments="--port=8080 --dataDir=./opt/tmp/data"

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class oldCartController {

    private static final Logger logger = LoggerFactory.getLogger(AppApplication.class);

    @Autowired
    showCart showCart;

    @GetMapping("/")
    public String showPage(
        Model model,
        @ModelAttribute Cart cart
    ) {
        return "index";
    }


    ///DELETE
    @PostMapping("/showCart")
    public String showCart(
    @ModelAttribute Cart cart,
    Model model) {
        //First, we check whether directories and file for the user exists.
        //If it doesn't exist, create the directories and file.
        // Path path = Paths.get("./src/main/resources/static/" + cart.getName() + ".txt");
        // try {
        //     System.out.println("Directory exists or no: " + Files.exists(path)); //Check whether directory exists
        //     if(!Files.exists(path)) {
        //         Files.createFile(path); //If file doesn't exist, create it
        //         System.out.println("New path created at " + path.toAbsolutePath());
        //         //File exists for the first time, add item name and quantity headers
        //         BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
        //         writer.write("item name,quantity");
        //         writer.close();
        //     }
        // } catch (IOException e) {
        //     logger.error(e.getMessage());
        // }

        //NEW TO REFACTOR ABOVE FUNCTION
        Path path = Paths.get("./src/main/resources/static/" + cart.getName() + ".txt");
        showCart.readFile(path); //function in showCart.java
        //NEW TO REFACTOR ABOVE FUNCTION

        //NEW TO REFACTOR THE BELOW FUNCTION
        List<String> currLine = null;
        String[] output = null;
        if(cart.getItem().equals("")) { //If item is left empty, return the username's current cart 
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>  Name entered, item not entered. Generating cart...  >>>>>>>>>>>>>>>>>>>>>>>>");
            showCart.generateCart(path, cart);
        } else {
            cart.setItem(cart.getItem().toLowerCase()); //Makes sure that item will always be recognized
            showCart.addutuCart(path, cart);
        }

        model.addAttribute("name", cart.getName());
        model.addAttribute("data", cart.getOutput());

        return "index";
        //NEW TO REFACTOR THE BELOW FUNCTION
    }

    //     //At this point, the file exists.
    //     List<String> currLine = null;
    //     String[] output = null;
    //     if(cart.getItem().equals("")) { //If item is left empty, return the username's current cart   .
    //         System.out.println("Item list empty.");
    //         try {
    //             int count = 0;
    //             currLine = Files.readAllLines(path); //Reading all lines from file.
    //             output = new String[currLine.size()];//Initializing String
    //             for (String thing : currLine) {
    //                 System.out.println("READING FROM FILE: " + thing + "\n");
    //                 //Populating String[] array with lines from the file.
    //                 output[count] = thing.split(",")[0] //A pple X 5
    //                 .substring(0, 1)
    //                 .toUpperCase() + thing.split(",")[0]
    //                 .substring(1) +  " X " + thing
    //                 .split(",")[1];                    
    //                 count++;
    //             }
    //         } catch (Exception e) {
    //             logger.error(e.getMessage());
    //         }
    //     } else {
    //         cart.setItem(cart.getItem().toLowerCase()); //Makes sure that item will always be recognized
    //         try {
    //             currLine = Files.readAllLines(path);
    //         } catch (Exception e) {
    //             logger.error(e.getMessage());
    //         }
    //         if(currLine!=null) {
    //             output = new String[currLine.size()];
    //             for (int i = 1; i < currLine.size(); i++) {
    //                 System.out.println("Reading from file: " + currLine.get(i));
    //                 if(currLine.get(i).split(",")[0].equals(cart.getItem())) {        //If item exists, increment the quantity
    //                     String[] temp = currLine.get(i).split(",");
    //                     int tempInt = Integer.parseInt(temp[1]) + 1; //Convert to int to increment
    //                     temp[1] = "" + tempInt; //Convert back to String
    //                     currLine.set(i, temp[0] + "," + temp[1]);
    //                 } 
    //             }
                
    //             if(!currLine.toString().contains(cart.getItem())) {
    //                 currLine.add(cart.getItem() + ",1");
    //             }

    //             int count = 0; 
    //             try {
    //                 output = new String[currLine.size()];
    //                 BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
    //                 for (String thing : currLine) {
    //                     System.out.println("New: " + thing + "\n");
    //                     writer.write(thing + "\n"); //Updating to file.
    //                     //Preparing to write out to HTML
    //                     output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                         
    //                     count++;
    //                 }
    //                 writer.close();
    //             } catch (Exception e) {
    //                 logger.error(e.getMessage());
    //             }
                
    //         }
    //     }
    //      //Makes it so that page does not print first row of .txt file ("item name, quantity")
    //      String[] output2 = new String[output.length - 1];
    //      for (int i = 1; i < output.length; i++) {
    //          output2[i-1] = output[i];
    //      }
 
    //      cart.setOutput(output2);
    //      model.addAttribute("name", cart.getName());
    //      model.addAttribute("data", output2);
    //      System.out.println("Name: "+ cart.getName());
    //      System.out.println("Item: "+ cart.getItem());

    //      showCart.setOutput(output2);
    //      showCart.setDeleteItem(cart.getItem());
    //      System.out.println("**********************************");
    //      return "index";
    //  }
        ///DELETE


    // @GetMapping("/showCart")
    // public String showCart(
    // Model model,
    // @RequestParam(name="name") String name,
    // @RequestParam(name="item", required=false) String item) {
    //     //First, we check whether directories and file for the user exists.
    //     //If it doesn't exist, create the directories and file.
    //     Path path = Paths.get("./src/main/resources/static/" + name + ".txt");
    //     try {
    //         System.out.println("Directory exists or no: " + Files.exists(path)); //Check whether directory exists
    //         if(!Files.exists(path)) {
    //             Files.createFile(path); //If file doesn't exist, create it
    //             System.out.println("New path created at " + path.toAbsolutePath());
    //             //File exists for the first time, add item name and quantity headers
    //             BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
    //             writer.write("item name,quantity");
    //             writer.close();
    //         }
    //     } catch (IOException e) {
    //         logger.error(e.getMessage());
    //     }

    //     //At this point, the file exists.
    //     List<String> currLine = null;
    //     String[] output = null;
    //     if(item.equals("")) { //If item is left empty, return the username's current cart   .
    //         System.out.println("Item list empty.");
    //         try {
    //             int count = 0;
    //             currLine = Files.readAllLines(path);
    //             output = new String[currLine.size()];
    //             for (String thing : currLine) {
    //                 System.out.println("Newer: " + thing + "\n");
    //                 output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                    
    //                 count++;
    //             }
    //         } catch (Exception e) {
    //             logger.error(e.getMessage());
    //         }
    //     } else {
    //         item = item.toLowerCase(); //Makes sure that item will always be recognized
    //         try {
    //             currLine = Files.readAllLines(path);
    //         } catch (Exception e) {
    //             logger.error(e.getMessage());
    //         }
    //         if(currLine!=null) {
    //             output = new String[currLine.size()];
    //             for (int i = 1; i < currLine.size(); i++) {
    //                 System.out.println("Reading from file: " + currLine.get(i));
    //                 if(currLine.get(i).split(",")[0].equals(item)) {        //If item exists, increment the quantity
    //                     String[] temp = currLine.get(i).split(",");
    //                     int tempInt = Integer.parseInt(temp[1]) + 1; //Convert to int to increment
    //                     temp[1] = "" + tempInt; //Convert back to String
    //                     currLine.set(i, temp[0] + "," + temp[1]);
    //                 } 
    //             }
                
    //             if(!currLine.toString().contains(item)) {
    //                 currLine.add(item + ",1");
    //             }

    //             int count = 0; 
    //             try {
    //                 output = new String[currLine.size()];
    //                 BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
    //                 for (String thing : currLine) {
    //                     System.out.println("New: " + thing + "\n");
    //                     writer.write(thing + "\n"); //Updating to file.
    //                     //Preparing to write out to HTML
    //                     output[count] = thing.split(",")[0].substring(0, 1).toUpperCase() + thing.split(",")[0].substring(1) +  " X " + thing.split(",")[1];                         
    //                     count++;
    //                 }
    //                 writer.close();
    //             } catch (Exception e) {
    //                 logger.error(e.getMessage());
    //             }
                
    //         }
    //     }

    //     //Makes it so that page does not print "item name,quantity" 
    //     String[] output2 = new String[output.length - 1];
    //     for (int i = 1; i < output.length; i++) {
    //         output2[i-1] = output[i];
    //     }

    //     model.addAttribute("name", name);
    //     model.addAttribute("data", output2);
    //     System.out.println("Name: "+ name);
    //     System.out.println("Item: "+ item);
    //     return "index";
    // }

    @RequestMapping(value="delete", params="btnSubmit", method=RequestMethod.POST)
    public String delete(
        Model model,
        @ModelAttribute Cart cart,
        @RequestParam("deletedItem") int deletedItem) { //When user presses the 3rd delete button, return 
        System.out.println("Inside delete method");
        System.out.println(deletedItem);
        cart.setName(cart.getName());
        System.out.println(cart.getName());
        System.out.println(cart.getItem());
        System.out.println(showCart.getOutput()[deletedItem-1]);
        return "index";
    }
}