package photo.world.data.user.repository.spring.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.profile.DataTag

@Repository("SpringDataPostgresqlTagRepository")
interface SpringDataPostgresqlTagRepository : JpaRepository<DataTag, String> {

    @Query("""
        select t from DataTag t
        where t.isPhotographerTag = true
    """)
    fun findAllPhotographerTags(): List<DataTag>

    @Query("""
        select t from DataTag t
        where t.isVisagistTag = true
    """)
    fun findAllVisagistTags(): List<DataTag>

    @Query("""
        select t from DataTag t
        where t.isModelTag = true
    """)
    fun findAllModelTags(): List<DataTag>
}