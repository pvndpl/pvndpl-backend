package ru.pvndpl.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.pvndpl.model.SimpleUserAuthInfo
import ru.pvndpl.repository.RoleRepository
import ru.pvndpl.repository.UserRepository
import ru.pvndpl.util.Role

@Service
class JwtUserDetailsService(
    var userRepository: UserRepository,
    var roleRepository: RoleRepository
) : UserDetailsService {


    override fun loadUserByUsername(username: String?): UserDetails {

        val user: SimpleUserAuthInfo? = userRepository.findByUsername(username);

        val roles: List<Role>? = user?.let { roleRepository.fetchUserRoles(it.id) }

        if (user != null) {

            return User(user.username, user.password, roles)

        } else {

            throw Exception("Ban in load user by username")
        }
    }
}