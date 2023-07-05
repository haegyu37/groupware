package com.groupware.wimir;
import com.groupware.wimir.constant.AppStatus;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.AppRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.AppService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AppServiceTest {

    @Test
    public void testSaveApproval() {
        // Mock repositories
        AppRepository appRepository = Mockito.mock(AppRepository.class);
        DocumentRepository documentRepository = Mockito.mock(DocumentRepository.class);

        // Create an instance of AppService
        Line line = new Line();

        AppService appService = new AppService(appRepository, documentRepository, line);

        // Create an App object
        App app = new App();
        app.setId(1L);

        // Mock the save method of appRepository
        when(appRepository.save(any(App.class))).thenReturn(app);

        // Call the saveApproval method
        App savedApp = appService.saveApproval(app);

        // Verify that the save method was called
        Mockito.verify(appRepository, Mockito.times(1)).save(app);

        // Check that the returned App object is the same as the one saved
        assertEquals(app, savedApp);
    }

    @Test
    public void testGetAllApprovals() {
        // Mock repository
        AppRepository appRepository = Mockito.mock(AppRepository.class);

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, null, null);

        // Create a list of App objects
        List<App> appList = Arrays.asList(new App(), new App(), new App());

        // Mock the findAll method of appRepository
        when(appRepository.findAll()).thenReturn(appList);

        // Call the getAllApprovals method
        List<App> retrievedAppList = appService.getAllApprovals();

        // Verify that the findAll method was called
        Mockito.verify(appRepository, Mockito.times(1)).findAll();

        // Check that the returned list is the same as the one mocked
        assertEquals(appList, retrievedAppList);
    }

    @Test
    public void testGetApprovalById() {
        // Mock repository
        AppRepository appRepository = Mockito.mock(AppRepository.class);

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, null, null);

        // Create an App object
        App app = new App();
        app.setId(1L);

        // Mock the findById method of appRepository
        when(appRepository.findById(1L)).thenReturn(Optional.of(app));

        // Call the getApprovalById method
        App retrievedApp = appService.getApprovalById(1L);

        // Verify that the findById method was called
        Mockito.verify(appRepository, Mockito.times(1)).findById(1L);

        // Check that the returned App object is the same as the one mocked
        assertEquals(app, retrievedApp);
    }

    @Test
    public void testGetApprovalById_NotFound() {
        // Mock repository
        AppRepository appRepository = Mockito.mock(AppRepository.class);

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, null, null);

        // Mock the findById method of appRepository to return an empty Optional
        when(appRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the getApprovalById method and expect ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> appService.getApprovalById(1L));

        // Verify that the findById method was called
        Mockito.verify(appRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testDeleteApproval() {
        // Mock repository
        AppRepository appRepository = Mockito.mock(AppRepository.class);

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, null, null);

        // Call the deleteApproval method
        appService.deleteApproval(1L);

        // Verify that the deleteById method was called
        Mockito.verify(appRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testApproveDocument() { //이샛기부터 안됨
        // Mock repositories
        AppRepository appRepository = Mockito.mock(AppRepository.class);
        DocumentRepository documentRepository = Mockito.mock(DocumentRepository.class);

        // Mock the findById method of appRepository
        when(appRepository.findById(1L)).thenReturn(Optional.empty());

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, documentRepository, null);

        // Create an App object
        App app = new App();
        app.setId(1L);
        app.setAppStatus(AppStatus.approving);
//        app.setDocId(1L);

        // Create a Document object
        Document document = new Document();
        document.setId(1L);

        // Mock the getApprovalById method of appService
        when(appService.getApprovalById(1L)).thenReturn(app);

        // Mock the findById method of documentRepository
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        // Call the approveDocument method
        appService.approveDocument(1L);

        // Verify that the getApprovalById method was called
        Mockito.verify(appService, Mockito.times(1)).getApprovalById(1L);

        // Verify that the findById method of documentRepository was called
        Mockito.verify(documentRepository, Mockito.times(1)).findById(1L);

        // Verify that the save method of appRepository was called
        Mockito.verify(appRepository, Mockito.times(1)).save(app);

        // Verify that the save method of documentRepository was called
        Mockito.verify(documentRepository, Mockito.times(1)).save(document);

        // Check that the appStatus and step are updated correctly
        assertEquals(AppStatus.approved, app.getAppStatus());
        assertEquals(1, document.getApp().getLine().getStep());
    }

    @Test
    public void testApproveDocument_NotApprovingStatus() {
        // Mock repository
        AppRepository appRepository = Mockito.mock(AppRepository.class);

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, null, null);

        // Create an App object with an appStatus other than 'approving'
        App app = new App();
        app.setAppStatus(AppStatus.approved);

        // Mock the getApprovalById method of appService to return the above App object
        when(appService.getApprovalById(1L)).thenReturn(app);

        // Call the approveDocument method and expect IllegalStateException
        assertThrows(IllegalStateException.class, () -> appService.approveDocument(1L));

        // Verify that the getApprovalById method was called
        Mockito.verify(appService, Mockito.times(1)).getApprovalById(1L);
    }

    @Test
    public void testReturnedDocument() {
        // Mock repository
        AppRepository appRepository = Mockito.mock(AppRepository.class);

        // Create an instance of AppService
        AppService appService = new AppService(appRepository, null, null);

        // Create an App object
        App app = new App();
        app.setId(1L);
        app.setAppStatus(AppStatus.approving);

        // Mock the getApprovalById method of appService
        when(appService.getApprovalById(1L)).thenReturn(app);

        // Call the returnedDocument method
        appService.returnedDocument(1L);

        // Verify that the getApprovalById method was called
        Mockito.verify(appService, Mockito.times(1)).getApprovalById(1L);

        // Verify that the save method of appRepository was called
        Mockito.verify(appRepository, Mockito.times(1)).save(app);

        // Check that the appStatus is updated to 'returned'
        assertEquals(AppStatus.returned, app.getAppStatus());
    }
}
