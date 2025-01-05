package com.franchiseworld.jalad.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class AuthoritiesConverter implements AttributeConverter<Collection<? extends GrantedAuthority>, String> {

    private static final String AUTHORITIES_SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return "";
        }
        return authorities.stream()
                .map(authority -> authority.getAuthority().replace("ROLE_", "")) // Remove "ROLE_" prefix before storing
                .collect(Collectors.joining(AUTHORITIES_SEPARATOR));
    }

    @Override
    public Collection<? extends GrantedAuthority> convertToEntityAttribute(String authoritiesString) {
        if (authoritiesString == null || authoritiesString.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(authoritiesString.split(AUTHORITIES_SEPARATOR))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add "ROLE_" prefix when retrieving
                .collect(Collectors.toList());
    }
}
