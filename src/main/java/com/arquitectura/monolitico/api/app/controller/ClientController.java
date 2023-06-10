package com.arquitectura.monolitico.api.app.controller;


import com.arquitectura.monolitico.api.app.dto.ClientDTO;
import com.arquitectura.monolitico.api.app.dto.ClientRequestDTO;
import com.arquitectura.monolitico.api.app.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,RequestMethod.PATCH})
@RequestMapping(value = "api/clients", consumes = MediaType.ALL_VALUE)
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "get all clients")
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<ClientDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body( this.clientService.listAllClients());
    }

    @Operation(summary = "get all clients by id of identificationType")
    @GetMapping("/identicationType/{identificationType}")
    @ResponseBody
    public ResponseEntity<List<ClientDTO>> findByIdentificationType(@PathVariable(value = "identificationType") String identificationType) throws IOException{
        return ResponseEntity.status(HttpStatus.OK).body(this.clientService.listAllClientsIdentificationType(identificationType));
    }

    @Operation(summary = "get client by id")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ClientDTO> findById(@PathVariable(value = "id") String id) throws IOException{
        return ResponseEntity.status(HttpStatus.OK).body(this.clientService.findById(id));
    }

    @Operation(summary = "get client by identification and identificationType")
    @GetMapping("/identiAndOrType/")
   // @RequestBody
    public ResponseEntity<ClientDTO> findByIdentificationAndIdentificationType(@RequestParam(value = "identification",required = false) String identification, @RequestParam(value = "identificationType",required = false) String identificationType) throws IOException{
        return ResponseEntity.status(HttpStatus.OK).body(this.clientService.findByIdentificationAndIdentificationType(identification,identificationType));
    }

    @Operation(summary = "save a client")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ClientRequestDTO>   save (@RequestPart(value = "photo",required = false) MultipartFile photo, @Valid @RequestPart ClientRequestDTO clientRequestDTO, BindingResult bindingResult) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientService.saveClient(clientRequestDTO,photo));
    }

    @Operation(summary = "update a client")
    @PutMapping("/{identification}")
    @ResponseBody
    public ResponseEntity<ClientRequestDTO> update(@PathVariable(value="identification") String identification,@Valid @RequestBody ClientRequestDTO clientRequestDTO,BindingResult bindingResult) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientService.updateClient(identification,clientRequestDTO));
    }

    @Operation(summary = "delate a client")
    @DeleteMapping("/{identification}")
    @ResponseBody
    public ResponseEntity<String> delete (@PathVariable(value = "identification") String identification) throws  IOException{
         return ResponseEntity.status(HttpStatus.OK).body(this.clientService.deleteClient(identification));
    }
}
