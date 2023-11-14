package com.dogsearch.demo.auth;

import com.dogsearch.demo.config.JwtService;
import com.dogsearch.demo.dto.person.PersonSaveDTO;
import com.dogsearch.demo.impl.PersonServiceImpl;
import com.dogsearch.demo.impl.RoleServiceImpl;
import com.dogsearch.demo.model.Person;
import com.dogsearch.demo.model.Role;
import com.dogsearch.demo.repository.PersonRepo;
import com.dogsearch.demo.repository.RoleRepo;
import com.dogsearch.demo.util.param.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PersonRepo personRepo;
    private final RoleServiceImpl roleService;
    private final PersonServiceImpl personService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        List<Role> roles = roleService.find(2L, "User");
        Person person = new Person(
                request.getName(),
                passwordEncoder.encode(request.getPassword()),
                request.getCity(),
                request.getNeighbourhood(),
                request.getPhoneNumber(),
                request.getEmail(),
                roles
        );
        try {
            personService.save(person);
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("Erro: ".concat(e.getLocalizedMessage()))
                    .build();
        }

        Map<String, Object> extraClaims = generateExtraClaims(person);
        var jwtToken = jwtService.generateToken(extraClaims, person);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .statusCode(HttpStatus.OK)
                .personId(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return AuthenticationResponse.builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("Erro: E-mail ou senha inválidos")
                    .build();
        }

        var person = personRepo.findPersonByEmail(request.getEmail()).orElseThrow();
        Map<String, Object> extraClaims = generateExtraClaims(person);
        var jwtToken = jwtService.generateToken(extraClaims, person);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .statusCode(HttpStatus.OK)
                .personId(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .build();
    }

    private static Map<String, Object> generateExtraClaims(Person person) {
        List<Role> roles = person.getRoles().stream().collect(Collectors.toList());
        Map<String, Object> rolesMap = roles.stream().collect(Collectors.toMap(role -> "role_".concat(role.getId().toString()), Role::getDescription));
        return rolesMap;
    }
}
