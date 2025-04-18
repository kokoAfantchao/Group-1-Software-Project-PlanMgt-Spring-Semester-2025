package com.team1.dev.controllers

import com.team1.dev.entities.ProjectEffort
import com.team1.dev.services.ProjectEffortService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/project-efforts")
class ProjectEffortController(private val projectEffortService: ProjectEffortService) {

    @GetMapping
    fun getAllProjectEfforts(): List<ProjectEffort> = projectEffortService.getAllProjectEfforts()

    @GetMapping("/{id}")
    fun getProjectEffortById(@PathVariable id: Int): ResponseEntity<ProjectEffort> {
        val projectEffort = projectEffortService.getProjectEffortById(id)
        return if (projectEffort != null) ResponseEntity.ok(projectEffort)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createProjectEffort(@RequestBody projectEffort: ProjectEffort): ProjectEffort =
        projectEffortService.createProjectEffort(projectEffort)

    @PutMapping("/{id}")
    fun updateProjectEffort(
        @PathVariable id: Int,
        @RequestBody projectEffort: ProjectEffort
    ): ResponseEntity<ProjectEffort> {
        val updatedEffort = projectEffortService.updateProjectEffort(id, projectEffort)
        return if (updatedEffort != null) ResponseEntity.ok(updatedEffort)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProjectEffort(@PathVariable id: Int): ResponseEntity<Void> {
        return if (projectEffortService.deleteProjectEffort(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }
}
