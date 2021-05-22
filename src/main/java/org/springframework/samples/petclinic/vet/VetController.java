/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import com.google.gson.Gson;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class VetController {

    private final VetRepository vets;

    public VetController(VetRepository clinicService) {
        this.vets = clinicService;
    }

    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        model.put("vets", vets.getVetList());
        return "vets/vetList";
    }
    
    @GetMapping(value = "/plinioDaOBoga")
    @ResponseBody
    public String plinioDaOBoga()
    {
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        return new Gson().toJson(vets);
    }

    @GetMapping({ "/vets" })
    public @ResponseBody Vets showResourcesVetList() {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        return vets;
    }
    
    @GetMapping({"/addVets"})
    public ModelAndView addVets(Vet vet) {
        ModelAndView view = new ModelAndView();
        view.setViewName("vets/addVet");
        return view;
    }
    
    @PostMapping(value = "/saveVet")
    @Transactional
    public ModelAndView save(@Valid Vet vet)
    {
        try {
            
            this.vets.save(vet);
            
        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
        
        return new ModelAndView("redirect:/vets.html");
    }

}
