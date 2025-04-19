package com.team1.dev.services

import com.team1.dev.entities.ProjectManager
import com.team1.dev.repositories.ProjectManagerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ProjectManagerService  @Autowired constructor(
    private val projectManagerRepository: ProjectManagerRepository
) {

    fun getAllProjectManagers(): List<ProjectManager> = projectManagerRepository.findAll()
    fun getAllProjectManagersSortedByProjectId(): List<ProjectManager> = projectManagerRepository.findAll(Sort.by("projectId"))

    fun getProjectManagerById(id: Long): ProjectManager? = projectManagerRepository.findById(id).orElse(null)

    fun createProjectManager(projectManager: ProjectManager): ProjectManager = projectManagerRepository.save(projectManager)

    fun updateProjectManager(id: Long, updatedProjectManager: ProjectManager): ProjectManager? {
        return if (projectManagerRepository.existsById(id)) {
            projectManagerRepository.save(updatedProjectManager)
        } else {
            null
        }
    }

    fun deleteProjectManager(id: Long): Boolean {
        return if (projectManagerRepository.existsById(id)) {
            projectManagerRepository.deleteById(id)
            true
        } else {
            false
        }
    }

}
