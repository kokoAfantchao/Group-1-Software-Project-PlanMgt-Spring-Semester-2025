package com.team1.dev.services

import com.team1.dev.entities.ProjectMember
import com.team1.dev.repositories.ProjectMemberRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProjectMemberService(private val projectMemberRepository: ProjectMemberRepository) {

    fun getAllProjectMembers(): List<ProjectMember> = projectMemberRepository.findAll()

    fun getAllProjectMembersSortedByProjectId(): List<ProjectMember> = projectMemberRepository.findAll(Sort.by("projectId"))

    fun getProjectMemberById(id: Int): ProjectMember? = projectMemberRepository.findById(id).orElse(null)

    fun createProjectMember(projectMember: ProjectMember): ProjectMember = projectMemberRepository.save(projectMember)

    fun updateProjectMember(id: Int, projectMember: ProjectMember): ProjectMember? {
        return if (projectMemberRepository.existsById(id)) {
            projectMemberRepository.save(projectMember.copy(id = id))
        } else null
    }

    fun deleteProjectMember(id: Int): Boolean {
        return if (projectMemberRepository.existsById(id)) {
            projectMemberRepository.deleteById(id)
            true
        } else false
    }
}
