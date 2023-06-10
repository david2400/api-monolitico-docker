package com.arquitectura.monolitico.api.app.service;

import com.arquitectura.monolitico.api.app.data.ClientData;
import com.arquitectura.monolitico.api.app.data.PhotoData;
import com.arquitectura.monolitico.api.app.dto.PhotoDTO;
import com.arquitectura.monolitico.api.app.dto.PhotoRequestDTO;
import com.arquitectura.monolitico.api.app.entity.Client;
import com.arquitectura.monolitico.api.app.entity.Photo;
import com.arquitectura.monolitico.api.app.exceptions.client.ClientNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoClientExistException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoNotFoundIdException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoNotFoundUpdateException;
import com.arquitectura.monolitico.api.app.mapper.PhotoMapper;
import com.arquitectura.monolitico.api.app.mapper.PhotoRequestMapper;
import com.arquitectura.monolitico.api.app.repository.ClientRepository;
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
class PhotoServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PhotoRepository photoRepository;


    @InjectMocks
    private  PhotoServiceImpl photoService;

    private Client client1, client2, client3,client4;
    private Photo photo1,photo2,photo3;

    private List<Photo> listPhotos;

    private MockMultipartFile fileMock;
    private MockMultipartFile fileMockEmpty;

    private PhotoDTO photoDTO1, photoDTO2,photoDTO3;

    private PhotoRequestDTO photoRequestDTO1,photoRequestDTO2,photoRequestDTO3;

    private List<PhotoDTO> listPhotosDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        fileMockEmpty =new MockMultipartFile("data", "", "", "".getBytes());
        fileMock = new MockMultipartFile("data", "filename.txt", "text/plain", "sdfsdsc".getBytes());

        listPhotos = new ArrayList<Photo>();
        listPhotosDTO=new ArrayList<PhotoDTO>();

        client1= ClientData.client;
        client2=ClientData.clientUno;
        client3=ClientData.clientDos;
        client4=ClientData.clientTres;

        photo1= PhotoData.photoUno;
        photo2= PhotoData.photoDos;
        photo3= PhotoData.photoCuatro;

        photoDTO1= PhotoMapper.INSTANCE.mapToDto(photo1);
        photoDTO2= PhotoMapper.INSTANCE.mapToDto(photo2);
        photoDTO3= PhotoMapper.INSTANCE.mapToDto(photo3);

        listPhotos.add(photo1);
        listPhotos.add(photo2);
        listPhotos.add(photo3);

        photoRequestDTO1= PhotoRequestMapper.INSTANCE.EntityToDTO(photo1);
        photoRequestDTO2= PhotoRequestMapper.INSTANCE.EntityToDTO(photo2);
        photoRequestDTO3=PhotoRequestMapper.INSTANCE.EntityToDTO(photo3);

        listPhotosDTO=PhotoMapper.INSTANCE.mapToDto(listPhotos);
    }

    @Test
    void listAllPhotos() {
        when(photoRepository.findAll()).thenReturn(listPhotos);
        assertEquals(3,photoService.listAllPhotos().size());
        assertArrayEquals(listPhotosDTO.toArray(),photoService.listAllPhotos().toArray());
    }

    @Test
    void getPhotoByClientIdentification() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.ofNullable(photo1));
        assertEquals(photoDTO1,photoService.getPhotoByClientIdentification("1007730758"));
    }
    @Test
    void getPhotoByClientIdentificationGlobalDataRequiredException()throws IOException {
        assertThrows(GlobalDataRequiredException.class,()->photoService.getPhotoByClientIdentification(null));
    }
    @Test
    void getPhotoByClientIdentificationClientNotFoundIdentificationException()throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdentificationException.class,()->photoService.getPhotoByClientIdentification("12345"));
    }
    @Test
    void getPhotoByPhotNotFoundIdentificationException() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.empty());
        assertThrows(PhotNotFoundIdentificationException.class,()->photoService.getPhotoByClientIdentification("1777730756"));
    }

    @Test
    void getPhotoById() throws IOException {
        when(photoRepository.findById(anyString())).thenReturn(Optional.ofNullable(photo1));
        assertEquals(photoDTO1,photoService.getPhotoById(photo1.getId()));
    }
    @Test
    void getPhotoByIdGlobalDataRequiredException() throws IOException{
        assertThrows(GlobalDataRequiredException.class,()-> photoService.getPhotoById(null));
    }
    @Test
    void getPhotoByIdPhotoNotFoundIdException()throws IOException {
        when(photoRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(PhotoNotFoundIdException.class,()-> photoService.getPhotoById(anyString()));
    }

    @Test
    void save() throws IOException {

        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client4));
        when(photoRepository.findByIdClient(client4.getId())).thenReturn(Optional.empty());
        assertNull(photoService.save(fileMock, "1007730759"));
    }
    @Test
    void saveGlobalDataRequiredException() {
        assertThrows(GlobalDataRequiredException.class,()-> photoService.save(fileMock,null));
    }
    @Test
    void saveGlobalDataRequiredExceptionIdentificationNotNull() {
        assertThrows(GlobalDataRequiredException.class,()-> photoService.save(fileMockEmpty,"d"));
    }
    @Test
    void saveClientNotFoundIdentificationException() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdentificationException.class,()-> photoService.save(fileMock,"1007730759"));

    }
    @Test
    void saveClientNotFoundPhotoClientExistException() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client4));
        when(photoRepository.findByIdClient(client4.getId())).thenReturn(Optional.ofNullable(photo1));
        assertThrows(PhotoClientExistException.class,()-> photoService.save(fileMock,"1007730759"));

    }

    @Test
    void deleteById() throws IOException {
        when(photoRepository.findById(anyString())).thenReturn(Optional.ofNullable(photo1));

        photoService.deleteById(anyString());
        verify(photoRepository).delete(any(Photo.class));

    }
    @Test
    void deleteByIdGlobalDataRequiredException() {
        assertThrows(GlobalDataRequiredException.class,()-> new PhotoServiceImpl().deleteById(null));

    }
    @Test
    void deleteByIdPhotoNotFoundIdException() {
      when(photoRepository.findById(anyString())).thenReturn(Optional.empty());
      assertThrows(PhotoNotFoundIdException.class,()->photoService.deleteById(anyString()));

    }

    @Test
    void deleteByIdentification() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.ofNullable(photo1));

        photoService.deleteByIdentification(anyString());
        verify(photoRepository).delete(any(Photo.class));


    }
    @Test
    void deleteByIdentificationGlobalDataRequiredException() throws IOException{
        assertThrows(GlobalDataRequiredException.class,()-> new PhotoServiceImpl().deleteByIdentification(null));
    }
    @Test
    void deleteByIdentificationClientNotFoundIdentificationException() throws IOException{
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdentificationException.class,()-> photoService.deleteByIdentification(client1.getIdentification()));
    }
    @Test
    void deleteByIdentificationfindByIdClient() throws IOException{
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.empty());
        assertThrows(PhotoNotFoundIdException.class,()-> photoService.deleteByIdentification(client1.getIdentification()));
    }
    @Test
    void update() throws IOException {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client1));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.ofNullable(photo1));
        assertNull(photoService.update(fileMock, "1007730759"));
    }
    @Test
    void updateGlobalDataRequiredException() {
        assertThrows(GlobalDataRequiredException.class,()-> photoService.update(fileMock,null));
    }
    @Test
    void updateGlobalDataRequiredExceptionIdentificationNotNull() {
        assertThrows(GlobalDataRequiredException.class,()-> photoService.update(fileMockEmpty,"d"));
    }
    @Test
    void updateClientNotFoundIdentificationException() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundIdentificationException.class,()-> photoService.update(fileMock,client1.getIdentification()));
    }
    @Test
    void updatePhotoNotFoundUpdateException() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.ofNullable(client4));
        when(photoRepository.findByIdClient(anyLong())).thenReturn(Optional.empty());
        assertThrows(PhotoNotFoundUpdateException.class,()-> photoService.update(fileMock,client4.getIdentification()));
    }





}


