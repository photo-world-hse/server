package photo.world.domain.photosession.repository

import photo.world.domain.photosession.entity.Photosession

interface PhotosessionRepository {

    fun getById(photosessionId: String): Photosession

    fun saveById(photosessionId: String, photosession: Photosession)

    fun saveNew(photosession: Photosession): String

    fun removeById(photosessionId: String)

    fun getAllInvitations(email: String): List<Pair<String, Photosession>>

    fun getAllActiveForUser(email: String): List<Pair<String, Photosession>>

    fun getAllArchive(email: String): List<Pair<String, Photosession>>

    fun getAll(email: String): List<Pair<String, Photosession>>
}