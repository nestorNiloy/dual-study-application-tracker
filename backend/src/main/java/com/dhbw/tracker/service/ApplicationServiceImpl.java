package com.dhbw.tracker.service;

import com.dhbw.tracker.exception.ResourceNotFoundException;
import com.dhbw.tracker.model.Application;
import com.dhbw.tracker.model.ApplicationStatus;
import com.dhbw.tracker.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Concrete implementation of ApplicationService.
 *
 * This layer is the single point of business logic in the application.
 * It sits between the Controller (HTTP concerns) and the Repository
 * (data persistence concerns), ensuring neither layer bleeds into the other.
 *
 * Constructor injection is used instead of @Autowired field injection,
 * which is the recommended approach in Spring best practices as it
 * makes dependencies explicit and the class easier to unit test.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    @Override
    public Application updateApplication(Long id, Application applicationDetails) {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + id));

        existingApplication.setCompanyName(applicationDetails.getCompanyName());
        existingApplication.setPositionTitle(applicationDetails.getPositionTitle());
        existingApplication.setStatus(applicationDetails.getStatus());
        existingApplication.setApplicationDate(applicationDetails.getApplicationDate());
        existingApplication.setNotes(applicationDetails.getNotes());

        return applicationRepository.save(existingApplication);
    }

    @Override
    public void deleteApplication(Long id) {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application not found with id: " + id));

        applicationRepository.delete(existingApplication);
    }
}
