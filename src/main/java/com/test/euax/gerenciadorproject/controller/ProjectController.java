package com.test.euax.gerenciadorproject.controller;

import com.test.euax.gerenciadorproject.exception.ResourceNotFoundException;
import com.test.euax.gerenciadorproject.model.Project;
import com.test.euax.gerenciadorproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ProjectController {
  @Autowired
  private ProjectRepository roomRepository;

  @GetMapping("/rooms")
  public List<Project> getAllRooms() {
    return roomRepository.findAll();
  }

  @GetMapping("/rooms/{id}")
  public ResponseEntity <Project> getRoomById(@PathVariable(value = "id") Long roomId)
    throws ResourceNotFoundException {
    Project project = roomRepository.findById(roomId)
      .orElseThrow(() -> new ResourceNotFoundException("Room not found :: " + roomId));
    return ResponseEntity.ok().body(project);
  }

  @PostMapping("/rooms")
  public Project createRoom(@Valid @RequestBody Project project) {
    return roomRepository.save(project);
  }

  @PutMapping("/rooms/{id}")
  public ResponseEntity <Project> updateRoom(@PathVariable(value = "id") Long roomId,
                                             @Valid @RequestBody Project projectDetails) throws ResourceNotFoundException {
    Project project = roomRepository.findById(roomId)
      .orElseThrow(() -> new ResourceNotFoundException("Room not found for this id :: " + roomId));

    project.setName(projectDetails.getName());
    project.setDate(projectDetails.getDate());
    project.setStartHour(projectDetails.getStartHour());
    project.setEndHour(projectDetails.getEndHour());
    final Project updateProject = roomRepository.save(project);
    return ResponseEntity.ok(updateProject);
  }

  @DeleteMapping("/rooms/{id}")
  public Map < String, Boolean > deleteRoom(@PathVariable(value = "id") Long roomId)
    throws ResourceNotFoundException {
    Project project = roomRepository.findById(roomId)
      .orElseThrow(() -> new ResourceNotFoundException("Room not found for this id :: " + roomId));

    roomRepository.delete(project);
    Map< String, Boolean > response = new HashMap< >();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}

