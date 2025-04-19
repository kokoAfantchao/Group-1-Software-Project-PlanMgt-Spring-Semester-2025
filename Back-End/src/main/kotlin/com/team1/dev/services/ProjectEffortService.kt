package com.team1.dev.services

import com.team1.dev.entities.ProjectEffort
import com.team1.dev.repositories.ProjectEffortRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProjectEffortService(private val projectEffortRepository: ProjectEffortRepository) {

    fun getAllProjectEfforts(): List<ProjectEffort> = projectEffortRepository.findAll()
    fun getAllEffortsSortedByProjectId(): List<ProjectEffort> = projectEffortRepository.findAll(Sort.by("projectId"))

    fun getProjectEffortById(id: Int): ProjectEffort? = projectEffortRepository.findById(id).orElse(null)

    fun createProjectEffort(projectEffort: ProjectEffort): ProjectEffort = projectEffortRepository.save(projectEffort)

    fun updateProjectEffort(id: Int, projectEffort: ProjectEffort): ProjectEffort? {
        return if (projectEffortRepository.existsById(id)) {
            projectEffortRepository.save(projectEffort.copy(id = id))
        } else null
    }

    fun deleteProjectEffort(id: Int): Boolean {
        return if (projectEffortRepository.existsById(id)) {
            projectEffortRepository.deleteById(id)
            true
        } else false
    }
}
