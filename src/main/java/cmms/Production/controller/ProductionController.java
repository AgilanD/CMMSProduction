package cmms.Production.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Production")
public class ProductionController {

    @GetMapping("/Checking")
    public String Message(){
        return "SuccessFully Connect the ProductionServices";
    }

}
