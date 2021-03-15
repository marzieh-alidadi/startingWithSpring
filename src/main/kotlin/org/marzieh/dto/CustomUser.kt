package org.marzieh.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    var profile: UserDto,
    id: String?,
    password: String?,
    authorities: MutableCollection<out GrantedAuthority>?
) : User(id.toString(), password, authorities)