package com.cg.tournament.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.tournament.Constants;
import com.cg.tournament.DAO.UserRepository;
import com.cg.tournament.DTOs.AuthedUser;
import com.cg.tournament.exceptions.BadRequestException;
import com.cg.tournament.exceptions.ConflictException;
import com.cg.tournament.exceptions.ForbiddenException;
import com.cg.tournament.exceptions.UnauthorizedException;
import com.cg.tournament.models.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> validateUser(User user) throws UnauthorizedException {
        if (user.getUsername() != null)
            user.setUsername(user.getUsername().toLowerCase());
        else
            throw new UnauthorizedException("Username required");
        if (!userRepository.existsByUsername(user.getUsername()))
            throw new UnauthorizedException("Username or password incorrect");
        if (user.getPassword() == null || user.getPassword().length() < 1)
            throw new UnauthorizedException("Password required");
        User userFromDB = userRepository.findOneByUsername(user.getUsername());
        if (!BCrypt.checkpw(user.getPassword(), userFromDB.getPassword()))
            throw new UnauthorizedException("Username or password incorrect");
        return new ResponseEntity<Object>(userFromDB, HttpStatus.OK);
    }

    public ResponseEntity<?> registerUser(User user) throws BadRequestException, ConflictException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        
        boolean val = false;
        int count = 0;
        int count2 = 0;
        String s = user.getPassword();
        if(s == null) {
        	throw new BadRequestException("Password is not Provided");
        }
        int n = s.length();
        for(int i=0; i<n;i++){
            if(Character.isUpperCase(s.charAt(i)))
                count+=1;
            if(Character.isLowerCase(s.charAt(i))) 
            count2+=1;
        }
        if(count == 0 || count2 == 0)
        		val = true;
        
            
        if (user.getEmailAddress() != null)
            user.setEmailAddress(user.getEmailAddress().toLowerCase());
        else
            throw new BadRequestException("Email address required");
        if (user.getUsername().length() > 30 || user.getUsername().length() < 3)
            throw new BadRequestException("Username must be between 3 and 30 characters");
        if (user.getUsername().isBlank() || user.getUsername() == null)
            throw new BadRequestException("Invalid username");
        if (user.getFullName().length() > 60 || user.getFullName().length() < 3)
            throw new BadRequestException("Full name must be between 3 and 60 characters");
        if (user.getEmailAddress().length() > 100)
            throw new BadRequestException("Email address must be 100 characters or less");
        if (!pattern.matcher(user.getEmailAddress()).matches())
            throw new BadRequestException("Invalid email format");
        if (userRepository.existsByEmailAddress(user.getEmailAddress()))
            throw new ConflictException("Email address already in use");
        if (userRepository.existsByUsername(user.getUsername()))
            throw new ConflictException("Username already in use");
        if(n < 6 || !p.matcher(s).find() || val)
        	throw new BadRequestException("Password do not match requirement");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        return new ResponseEntity<Object>(generateJWTToken(userRepository.save(user)), HttpStatus.OK);
    }

    public ResponseEntity<?> validateToken(String authHeader) throws ForbiddenException {
        String token;
        try {
            token = authHeader.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ForbiddenException("Authorization header must be: Bearer [token]");
        }
        Jws<Claims> jwt = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token);
        AuthedUser authedUser = new AuthedUser(userRepository.findOneByUsername(jwt.getBody().get("username").toString()));
        return new ResponseEntity<Object>(authedUser, HttpStatus.OK);
    }

    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public boolean isUserByUserId(Long userId) {
        return userRepository.existsById(userId);
    }

    public boolean isUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private String generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .compact();
    }
}
