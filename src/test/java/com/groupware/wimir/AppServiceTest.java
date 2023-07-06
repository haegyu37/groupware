package com.groupware.wimir;


import com.groupware.wimir.constant.AppStatus;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.AppRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.LineRepository;
import com.groupware.wimir.service.AppService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AppServiceTest {

    private AppService appService;

    @Mock
    private AppRepository appRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private LineRepository lineRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        appService = new AppService(appRepository, documentRepository, lineRepository);
    }

    @Test
    public void testSaveApproval() {
        // Create an App object
        App app = new App();
        app.setId(1L);

        // Mock the save method of appRepository
        when(appRepository.save(any(App.class))).thenReturn(app);

        // Call the saveApproval method
        App savedApp = appService.saveApproval(app);

        // Verify that the save method was called
        verify(appRepository, times(1)).save(app);

        // Check that the returned App object is the same as the one saved
        assertEquals(app, savedApp);
    }

    @Test
    public void testGetAllApprovals() {
        // Create a list of App objects
        List<App> appList = Arrays.asList(new App(), new App(), new App());

        // Mock the findAll method of appRepository
        when(appRepository.findAll()).thenReturn(appList);

        // Call the getAllApprovals method
        List<App> retrievedAppList = appService.getAllApprovals();

        // Verify that the findAll method was called
        verify(appRepository, times(1)).findAll();

        // Check that the returned list is the same as the one mocked
        assertEquals(appList, retrievedAppList);
    }

    @Test
    public void testGetApprovalById() {
        // Create an App object
        App app = new App();
        app.setId(1L);

        // Mock the findById method of appRepository
        when(appRepository.findById(1L)).thenReturn(Optional.of(app));

        // Call the getApprovalById method
        Optional<App> retrievedApp = appService.getApprovalById(1L);


        // Verify that the findById method was called
        verify(appRepository, times(1)).findById(1L);

        // Check that the returned App object is the same as the one mocked
        assertTrue(retrievedApp.isPresent());
        assertEquals(app, retrievedApp.get());
    }

    @Test
    public void testGetApprovalById_NotFound() {
        // Mock the findById method of appRepository to return an empty Optional
        when(appRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the getApprovalById method and expect ResourceNotFoundException
//        assertThrows(ResourceNotFoundException.class, () -> appService.getApprovalById(1L));

        // Verify that the findById method was called
//        verify(appRepository, times(1)).findById(1L);

        // 예외를 검증하는 코드
        Long invalidId = 999L; // 존재하지 않는 ID
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            appService.getApprovalById(invalidId);
        });
    }

    @Test
    public void testDeleteApproval() {
        // Call the deleteApproval method
        appService.deleteApproval(1L);

        // Verify that the deleteById method was called
        verify(appRepository, times(1)).deleteById(1L);
    }

    // TODO: Add more test cases for the approveDocument and returnedDocument methods
}
