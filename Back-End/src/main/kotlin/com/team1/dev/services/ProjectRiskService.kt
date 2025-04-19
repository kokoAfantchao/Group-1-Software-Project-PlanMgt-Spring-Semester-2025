package com.team1.dev.services

import com.team1.dev.entities.ProjectRisk
import com.team1.dev.repositories.ProjectRiskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProjectRiskService @Autowired constructor(
    private val projectRiskRepository: ProjectRiskRepository

) {

    fun getAllRisks(): List<ProjectRisk> = projectRiskRepository.findAll()

    fun getAllRisksSortedByProjectId(): List<ProjectRisk> = projectRiskRepository.findAll(Sort.by("projectId"))
    fun getRiskById(id: Int): ProjectRisk? = projectRiskRepository.findById(id).orElse(null)

    fun createRisk(risk: ProjectRisk): ProjectRisk = projectRiskRepository.save(risk)

    fun updateRisk(id: Int, updatedRisk: ProjectRisk): ProjectRisk? {
        return if (projectRiskRepository.existsById(id)) {
            projectRiskRepository.save(updatedRisk)
        } else {
            null
        }
    }

    fun deleteRisk(id: Int): Boolean {
        return if (projectRiskRepository.existsById(id)) {
            projectRiskRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
