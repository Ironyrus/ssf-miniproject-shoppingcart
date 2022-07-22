package springcart.app;

//mvn spring-boot:run -Dspring-boot.run.arguments="--port=8080 --dataDir=./opt/tmp/data"

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

@Controller
public class cartController {

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

        Path path = Paths.get("./src/main/resources/static/" + cart.getName() + ".txt");
        showCart.readFile(path); //function in showCart.java

        if(cart.getItem().equals("")) { //If item is left empty, return the username's current cart 
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>  Name entered, item not entered. Generating cart...  >>>>>>>>>>>>>>>>>>>>>>>>");
            showCart.generateCart(path, cart);
        } else {
            cart.setItem(cart.getItem().toLowerCase()); //Makes sure that item will always be recognized
            showCart.addutuCart(path, cart);
        }

        model.addAttribute("name", cart.getName());
        model.addAttribute("data", cart.getOutput());
        //showCart.setOutput(cart.getOutput()); //This ensures delete() method below works.
        return "index";
    }

    @RequestMapping(value="delete", params="btnSubmit", method=RequestMethod.POST)
    public String delete(
        Model model,
        @ModelAttribute Cart cart,
        @RequestParam("deletedItem") int deletedItem) { //When user presses the 3rd delete button, return "2"

        Path path = Paths.get("./src/main/resources/static/" + cart.getName() + ".txt");
        showCart.readFile(path); //function in showCart.java
        // if(cart.getItem().equals("") || cart.getItem() == null) { //If item is left empty, return the username's current cart 
        //     System.out.println(">>>>>>>>>>>>>>>>>>>>>>  Name entered, item not entered. Generating cart...  >>>>>>>>>>>>>>>>>>>>>>>>");
        //     showCart.generateCart(path, cart);
        // }

        showCart.deleteItem(path, deletedItem, cart); // DELETING THE ITEM!

        System.out.println("<<<  Inside delete method  >>>");
        System.out.println("Index deleted: " + (deletedItem - 1));
        cart.setName(cart.getName());
        System.out.println("Current user: "+ cart.getName());
        // System.out.println(showCart.getOutput()[deletedItem-1]);

        model.addAttribute("name", cart.getName()); //Name field will be populated after deleting item
        model.addAttribute("data", cart.getOutput()); //Writing out cart to HTML
        return "index";
    }

    @RequestMapping(value="sort", params="btnSubmitSort", method=RequestMethod.POST)
    public String sort(
                Model model,
                @ModelAttribute Cart cart,
                @RequestParam("itemToSort") int sortedItem) {
                    
                    Path path = Paths.get("./src/main/resources/static/" + cart.getName() + ".txt");
                    showCart.readFile(path); //function in showCart.java
                    System.out.println(sortedItem);

                    showCart.sortCart(path, sortedItem, cart);
                    model.addAttribute("name", cart.getName()); //Name field will be populated after deleting item
                    model.addAttribute("data", cart.getOutput()); //Writing out cart to HTML
                    return "index";
    }

    @RequestMapping(value="edit", params="btnSubmitEdit", method=RequestMethod.POST)
    public String edit(
            Model model,
            @ModelAttribute Cart cart,
            @RequestParam("editItem") int editItem) {
        
                Path path = Paths.get("./src/main/resources/static/" + cart.getName() + ".txt");
                showCart.readFile(path);
                System.out.println("Item to edit: " + editItem);
                System.out.println("New Item: " + cart.getItem());
                showCart.editItem(path, editItem, cart);

                cart.setItem("");
                model.addAttribute("name", cart.getName());
                model.addAttribute("data", cart.getOutput());

                return "index";
    }
}