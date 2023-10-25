package inha.capstone.fooda.domain.feed.entity;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import inha.capstone.fooda.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Boolean open;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Menu menu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feed)) return false;
        Feed that = (Feed) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Builder
    public Feed(Long id, Member member, Boolean open, Menu menu) {
        this.id = id;
        this.member = member;
        this.open = open;
        this.menu = menu;
    }
}
