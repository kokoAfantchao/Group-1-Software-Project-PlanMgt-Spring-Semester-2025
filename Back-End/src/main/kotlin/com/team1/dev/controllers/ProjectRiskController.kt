package com.team1.dev.Controllers

import com.team1.dev.entities.ProjectRisk
import com.team1.dev.services.ProjectRiskService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/project-risks")
class ProjectRiskController(private val projectRiskService: ProjectRiskService) {

    @GetMapping
    fun getAllProjectRisks(): List<ProjectRisk> = projectRiskService.getAllRisks()

    @GetMapping("/sortedByProjectId")
    fun getAllProjectRisksSortedByProjectId(): List<ProjectRisk> =
        projectRiskService.getAllRisksSortedByProjectId()

    @GetMapping("/{id}")
    fun getProjectRiskById(@PathVariable id: Int): ResponseEntity<ProjectRisk> {
        val projectRisk = projectRiskService.getRiskById(id)
        return if (projectRisk != null) ResponseEntity.ok(projectRisk)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createProjectRisk(@RequestBody projectRisk: ProjectRisk): ProjectRisk =
        projectRiskService.createRisk(projectRisk)

    @PutMapping("/{id}")
    fun updateProjectRisk(
        @PathVariable id: Int,
        @RequestBody projectRisk: ProjectRisk
    ): ResponseEntity<ProjectRisk> {
        val updatedRisk = projectRiskService.updateRisk(id, projectRisk)
        return if (updatedRisk != null) ResponseEntity.ok(updatedRisk)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProjectRisk(@PathVariable id: Int): ResponseEntity<Void> {
        return if (projectRiskService.deleteRisk(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }
}
