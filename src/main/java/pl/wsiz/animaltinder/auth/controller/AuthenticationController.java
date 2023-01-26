package pl.wsiz.animaltinder.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wsiz.animaltinder.auth.domain.CustomUserDetails;
import pl.wsiz.animaltinder.auth.dto.request.AuthenticationRequest;
import pl.wsiz.animaltinder.auth.dto.response.AuthenticationResponse;
import pl.wsiz.animaltinder.auth.service.JwtUserDetailsService;
import pl.wsiz.animaltinder.auth.util.JwtUtil;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));
    }

}
