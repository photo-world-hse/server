package photo.world.domain.photosession.repository

import photo.world.common.profile.ProfileType
import photo.world.domain.photosession.entity.ProfileData

interface PhotosessionProfileRepository {

    fun getProfileInfo(email: String, profileType: ProfileType): ProfileData
}