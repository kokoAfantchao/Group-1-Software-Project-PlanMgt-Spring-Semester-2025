package com.team1.dev.controllers

import com.team1.dev.entities.ProjectManager // Fixed package name for entities
import com.team1.dev.services.ProjectManagerService // Fixed package name for services
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/project-managers")
class ProjectManagerController @Autowired constructor(
    private val projectManagerService: ProjectManagerService
) {

    @GetMapping("/all")
    fun getAllProjectManagers(): List<ProjectManager> = projectManagerService.getAllProjectManagers()


    @GetMapping("/{id}")
    fun getProjectManagerById(@PathVariable id: Long): ResponseEntity<ProjectManager> {
        val projectManager = projectManagerService.getProjectManagerById(id)
        return if (projectManager != null) ResponseEntity.ok(projectManager) // Should return 200 OK if manager is found
        else ResponseEntity.notFound().build() // Should return 404 Not Found if the manager is not found.
    }


    @PostMapping("/create")
    fun createProjectManager(@RequestBody projectManager: ProjectManager): ProjectManager = projectManagerService.createProjectManager(projectManager)

    @PutMapping("/update/{id}")
    fun updateProjectManager(@PathVariable id: Long, @RequestBody projectManager: ProjectManager): ProjectManager? = projectManagerService.updateProjectManager(id, projectManager)

    @DeleteMapping("/delete/{id}")
    fun deleteProjectManager(@PathVariable id: Long): String {
        return if (projectManagerService.deleteProjectManager(id)) {
            "Project Manager with id $id deleted"
        } else {
            "Project Manager with id $id not found"
        }
    }
}
