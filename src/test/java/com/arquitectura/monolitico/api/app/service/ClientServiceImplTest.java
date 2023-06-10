package com.arquitectura.monolitico.api.app.service;

import com.arquitectura.monolitico.api.app.data.CityData;
import com.arquitectura.monolitico.api.app.data.ClientData;
import com.arquitectura.monolitico.api.app.data.IdentificationTypeData;
import com.arquitectura.monolitico.api.app.data.PhotoData;
import com.arquitectura.monolitico.api.app.dto.ClientDTO;
import com.arquitectura.monolitico.api.app.dto.ClientRequestDTO;
import com.arquitectura.monolitico.api.app.entity.City;
import com.arquitectura.monolitico.api.app.entity.Client;
import com.arquitectura.monolitico.api.app.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.entity.Photo;
import com.arquitectura.monolitico.api.app.exceptions.IdentificationType.IdentificationTypeIdNumberException;
import com.arquitectura.monolitico.api.app.exceptions.IdentificationType.IdentificationTypeNotFoundException;
import com.arquitectura.monolitico.api.app.exceptions.city.CityNotFoundException;
import com.arquitectura.monolitico.api.app.exceptions.client.*;
import com.arquitectura.monolitico.api.app.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.mapper.ClientMapper;
import com.arquitectura.monolitico.api.app.mapper.ClientRequestMapper;
import com.arquitectura.monolitico.api.app.repository.CityRepository;
import com.arquitectura.monolitico.api.app.repository.ClientRepository;
import com.arquitectura.monolitico.api.app.repository.IdentificationTypeRepository;
import com.arquitectura.monolitico.api.app.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private IdentificationTypeRepository identificationTypeRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private City city1, city2, city3, city4;
    private IdentificationType identificationType1, identificationType2;
    private Client client1, client2, client3;
    private Photo photo;
    private ClientDTO clientDTO1,clientDTO2,clientDTO3;
    private ClientRequestDTO clientRequestDTO1,clientRequestDTO2;

    private List<Client> listAll;
    private List<Client> listTypeUno;
    private List<ClientDTO> listTypeUnoDTO;
    private List<ClientDTO> listAllDTO;

    private MockMultipartFile fileMock;
    private MockMultipartFile fileMockEmpty;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        fileMock = new MockMultipartFile("data", "filename.txt", "text/plain", "sdfsdsc".getBytes());
        fileMockEmpty =new MockMultipartFile("data", "", "", "".getBytes());
        city1 = CityData.cityArmenia;
        city2 = CityData.cityBogota;
        city3 = CityData.cityManizales;
        city4 = CityData.cityPereira;
        identificationType1= IdentificationTypeData.identificationTypeCedula;
        identificationType2=IdentificationTypeData.identificationTypeCedula;
        client1= ClientData.client;
        client2=ClientData.clientUno;
        client3=ClientData.clientDos;
        photo= PhotoData.photoUno;

        clientDTO1 = ClientMapper.INSTANCE.mapToDto(client1);
        clientDTO2=ClientMapper.INSTANCE.mapToDto(client2);
        clientDTO3=ClientMapper.INSTANCE.mapToDto(client3);
        clientRequestDTO1= ClientRequestMapper.INSTANCE.EntityToDTO(client1);
        clientRequestDTO2= ClientRequestMapper.INSTANCE.EntityToDTO(client2);
        listAll=new ArrayList<Client>();
        listAllDTO=new ArrayList<ClientDTO>();
        listTypeUno=new ArrayList<Client>();
        listTypeUnoDTO=new ArrayList<ClientDTO>();
        listAll.add(client1);
        listAll.add(client2);
        listAll.add(client3);
        listAllDTO= ClientMapper.INSTANCE.mapToDto(listAll);
        listTypeUno.add(client1);
        listTypeUno.add(client2);
        listTypeUnoDTO=ClientMapper.INSTANCE.mapToDto(listTypeUno);

    }
    @Test
    void saveClient() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        when(clientRepository.save(any(Client.class))).thenReturn(client1);
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);
        assertEquals(clientRequestDTO1,clientService.saveClient(clientRequestDTO1,fileMock));
    }
    @Test
    void saveClientGlobalDataRequiredException() throws IOException {

        assertThrows(GlobalDataRequiredException.class,()->clientService.saveClient(null,this.fileMock));

    }
    @Test
    void saveClientClientDataIdentificationExistException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        assertThrows(ClientDataIdentificationExistException.class,()->clientService.saveClient(clientRequestDTO1,fileMock));
    }
    @Test
    void saveClientClientDataMailExistException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.ofNullable(client1));
        assertThrows(ClientDataMailExistException.class,()->clientService.saveClient(clientRequestDTO1,fileMock));
    }
    @Test
    void saveClientIdentificationTypeNotFoundException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IdentificationTypeNotFoundException.class,()->clientService.saveClient(clientRequestDTO1,fileMock));

    }
    @Test
    void saveClientCityNotFoundException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CityNotFoundException.class,()->clientService.saveClient(clientRequestDTO1,fileMock));

    }
    @Test
    void saveClientClientMinAgeException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        clientRequestDTO1.setAge("17");
        assertThrows(ClientAgeException.class,()->clientService.saveClient(clientRequestDTO1,fileMock));
        clientRequestDTO1.setAge("20");
    }
    @Test
    void saveClientClientMaxAgeException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        clientRequestDTO1.setAge("131");
        assertThrows(ClientAgeException.class,()->clientService.saveClient(clientRequestDTO1,fileMock));
        clientRequestDTO1.setAge("20");
    }
    @Test
    void saveClientNotPhoto() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        when(clientRepository.save(any(Client.class))).thenReturn(client1);
        assertEquals(clientRequestDTO1,clientService.saveClient(clientRequestDTO1,fileMockEmpty));
    }
    @Test
    void updateClient() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        when(clientRepository.save(any(Client.class))).thenReturn(client1);
        assertEquals(clientRequestDTO1,clientService.updateClient("1007730758",clientRequestDTO1));
    }
    @Test
    void updateClientRequestNull() {
        assertThrows(GlobalDataRequiredException.class,()-> clientService.updateClient(client1.getIdentification(),null));
    }
    @Test
    void updateClientIdentificationNull() {
        assertThrows(GlobalDataRequiredException.class,()-> clientService.updateClient(null,clientRequestDTO1));
    }
    @Test
    void updateClientIdentificationAndRequestNull() {
        assertThrows(GlobalDataRequiredException.class,()-> clientService.updateClient(null,null));
    }
    @Test
    void updateClientClientNotFoundIdentificationException() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdentificationException.class,()->clientService.updateClient("1",clientRequestDTO1));
    }
    @Test
    void updateClientClientDataIdentificationExistException() {
        clientRequestDTO1.setIdentification("1007730759");
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        assertThrows(ClientDataIdentificationExistException.class,()->clientService.updateClient("1007730758",clientRequestDTO1));
        clientRequestDTO1.setIdentification("1007730758");
    }
    @Test
    void updateClientClientDataMailExistException() {
        clientRequestDTO1.setMail("santiago@gmail.com");
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(clientRepository.findByMail(anyString())).thenReturn(Optional.ofNullable(client1));
        assertThrows(ClientDataMailExistException.class,()->clientService.updateClient("1007730758",clientRequestDTO1));
        clientRequestDTO1.setMail("santi.posda.1321@gmail.com");
    }
    @Test
    void updateClientIdentificationTypeNotFoundException() throws IOException{
        clientRequestDTO1.setIdentificationTypeId(190l);
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IdentificationTypeNotFoundException.class,()->clientService.updateClient("1007730758",clientRequestDTO1));
        clientRequestDTO1.setIdentificationTypeId(1l);
    }
    @Test
    void updateClientCityNotFoundException() {
        clientRequestDTO1.setCityId(190l);
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CityNotFoundException.class,()->clientService.updateClient("1007730758",clientRequestDTO1));
        clientRequestDTO1.setIdentificationTypeId(1l);
    }
    @Test
    void updateClientCityNotFoundExceptionMinAge() {
        clientRequestDTO1.setAge("17");
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        assertThrows(ClientAgeException.class,()->clientService.updateClient("1007730758",clientRequestDTO1));
        clientRequestDTO1.setAge("20");
    }
    @Test
    void updateClientCityNotFoundExceptionMaxAge() {
        clientRequestDTO1.setAge("135");
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(identificationTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(identificationType1));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(city1));
        assertThrows(ClientAgeException.class,()->clientService.updateClient("1007730758",clientRequestDTO1));
        clientRequestDTO1.setAge("20");
    }
    @Test
    void deleteClient() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(client1.getId())).thenReturn(Optional.ofNullable(photo));

        clientService.deleteClient(anyString());
        verify(clientRepository).delete(any(Client.class));
        verify(photoRepository).delete(any(Photo.class));
    }
    @Test
    void deleteClientClientPhotoNull() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.empty());

        clientService.deleteClient(anyString());
        verify(clientRepository).delete(any(Client.class));
    }
    @Test
    void deleteClientClientNotFoundIdentificationException() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdentificationException.class,()-> clientService.deleteClient("324234324"));
    }

    @Test
    void listAllClients(){
        when(clientRepository.findAll()).thenReturn(listAll);
        assertEquals(3,clientService.listAllClients().size());
        assertArrayEquals(listAllDTO.toArray(),clientService.listAllClients().toArray());
    }

    @Test
    void findById() throws IOException {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.ofNullable(client1));
        assertEquals(clientDTO1, clientService.findById(client1.getId().toString()));
    }
    @Test
    void findByIdNotId() throws IOException {
        assertThrows(GlobalDataRequiredException.class,()->clientService.findById(null));
    }
    @Test
    void findByIdClientIdNumberException() throws IOException {
        assertThrows(ClientIdNumberException.class,()->clientService.findById("df3"));
    }
    @Test
    void findByIdClientNotFoundIdException() throws IOException {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdException.class,()->clientService.findById("1"));
    }

    @Test
    void findByIdentificationAndIdentificationType()throws IOException {
        when(clientRepository.findByIdentificationAndIdentificationType(anyString(),any(IdentificationType.class))).thenReturn(Optional.ofNullable(client1));
        assertEquals(clientDTO1,clientService.findByIdentificationAndIdentificationType("1007730758","1"));
    }
    @Test
    void findByIdentificationAndIdentificationTypeGlobalDataRequiredExceptionNull()throws IOException {
        assertThrows(GlobalDataRequiredException.class,()-> clientService.findByIdentificationAndIdentificationType(null,null));
    }
    @Test
    void findByIdentificationAndIdentificationTypeGlobalDataRequiredExceptionNullIdentification()throws IOException {
        assertThrows(GlobalDataRequiredException.class,()-> clientService.findByIdentificationAndIdentificationType(null,"1"));
    }
    @Test
    void findByIdentificationAndIdentificationTypeGlobalDataRequiredExceptionNullIdentificationType()throws IOException {
        assertThrows(GlobalDataRequiredException.class,()-> clientService.findByIdentificationAndIdentificationType("1007730758",null));
    }
    @Test
    void findByIdentificationAndIdentificationTypeNumberException()throws IOException {
        assertThrows(ClientIdentificationTypeNumberException.class,()-> clientService.findByIdentificationAndIdentificationType("12345","12sd2"));
    }
    @Test
    void findByIdentificationAndIdentificationTypeClientNotFoundIdenTypeException()throws IOException {
        when(clientRepository.findByIdentificationAndIdentificationType(anyString(),any(IdentificationType.class))).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdenTypeException.class,()-> clientService.findByIdentificationAndIdentificationType("324234324","1"));
    }

    @Test
    void listAllClientsIdentificationType() throws IOException{
        when(clientRepository.findByIdentificationType(any(IdentificationType.class))).thenReturn(List.copyOf(listTypeUno));
        assertEquals(2,clientService.listAllClientsIdentificationType("1").size());
        assertArrayEquals(listTypeUnoDTO.toArray(),clientService.listAllClientsIdentificationType("1").toArray());
    }
    @Test
    void listAllClientsIdentificationTypeNotIdIdentificationType()  throws IOException{
       assertThrows(GlobalDataRequiredException.class,()->clientService.listAllClientsIdentificationType(null));
    }
    @Test
    void listAllClientsIdentificationTypeIdNumberException()  throws IOException{
        assertThrows(IdentificationTypeIdNumberException.class,()->clientService.listAllClientsIdentificationType("df3"));
    }
}