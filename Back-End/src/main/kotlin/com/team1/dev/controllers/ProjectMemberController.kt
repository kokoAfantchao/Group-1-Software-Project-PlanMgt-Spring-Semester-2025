package com.team1.dev.controllers

import com.team1.dev.entities.ProjectMember
import com.team1.dev.services.ProjectMemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/project-members")
class ProjectMemberController(private val projectMemberService: ProjectMemberService) {

    @GetMapping
    fun getAllProjectMembers(): List<ProjectMember> = projectMemberService.getAllProjectMembers()

    @GetMapping("/sortedByProjectId")
    fun getAllProjectMembersSortedByProjectId(): List<ProjectMember> =
        projectMemberService.getAllProjectMembersSortedByProjectId()
    @GetMapping("/{id}")
    fun getProjectMemberById(@PathVariable id: Int): ResponseEntity<ProjectMember> {
        val projectMember = projectMemberService.getProjectMemberById(id)
        return if (projectMember != null) ResponseEntity.ok(projectMember)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createProjectMember(@RequestBody projectMember: ProjectMember): ProjectMember =
        projectMemberService.createProjectMember(projectMember)

    @PutMapping("/{id}")
    fun updateProjectMember(
        @PathVariable id: Int,
        @RequestBody projectMember: ProjectMember
    ): ResponseEntity<ProjectMember> {
        val updatedMember = projectMemberService.updateProjectMember(id, projectMember)
        return if (updatedMember != null) ResponseEntity.ok(updatedMember)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProjectMember(@PathVariable id: Int): ResponseEntity<Void> {
        return if (projectMemberService.deleteProjectMember(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }
}
