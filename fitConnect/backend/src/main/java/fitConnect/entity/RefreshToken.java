package fitConnect.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(name="user_id",nullable = false,unique = true)
    private String userId;

    @Column(name="refresh_token",nullable = false)
    private String refreshToken;

    public RefreshToken(String userId,String refreshToken){
        this.userId=userId;
        this.refreshToken=refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken=refreshToken;
        return this;
    }
}
