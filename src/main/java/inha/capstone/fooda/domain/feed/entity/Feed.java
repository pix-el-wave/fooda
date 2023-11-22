package inha.capstone.fooda.domain.feed.entity;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import inha.capstone.fooda.domain.feed_image.entity.FeedImage;
import inha.capstone.fooda.domain.food.entity.Food;
import inha.capstone.fooda.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "feed_open", nullable = false)
    private Boolean open;

    @Column(name = "feed_menu", nullable = false)
    @Enumerated(EnumType.STRING)
    private Menu menu;

    @Column(name = "feed_like_count", nullable = false)
    private Long likeCount;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    private Set<Food> foods;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    private Set<FeedImage> feedImages;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feed)) {
            return false;
        }
        Feed that = (Feed) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Builder
    public Feed(Long id, Member member, Boolean open, Menu menu, Long likeCount) {
        this.id = id;
        this.member = member;
        this.open = open;
        this.menu = menu;
        this.likeCount = likeCount;
    }
}
