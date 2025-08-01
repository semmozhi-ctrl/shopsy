package com.kartblaze.shopsy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
}
        emailService.sendVerificationEmail(user.getEmail(), verifyUrl);

        model.addAttribute("message", "Registration successful! Please check your email to verify your account.");
        return "login";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@ModelAttribute("token") String token, Model model) {
        AppUser user = userService.findByVerificationToken(token);
        if (user != null) {
            user.setEmailVerified(true);
            user.setVerificationToken(null);
            userService.save(user);
            model.addAttribute("message", "Email verified successfully!");
        } else {
            model.addAttribute("error", "Invalid verification token.");
        }
        return "login";
    }
}