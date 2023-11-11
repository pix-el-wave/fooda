package inha.capstone.fooda.domain.feed_image.entity;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import inha.capstone.fooda.domain.feed.entity.Feed;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class FeedImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Column(name = "feed_image_file_name", nullable = false)
    private String fileName;

    @Column(name = "feed_image_file_name_stored", nullable = false)
    private String fileNameStored;

    @Column(name = "feed_image_url", nullable = false)
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedImage)) {
            return false;
        }
        FeedImage that = (FeedImage) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Builder
    public FeedImage(Long id, Feed feed, String fileName, String fileNameStored, String url) {
        this.id = id;
        this.feed = feed;
        this.fileName = fileName;
        this.fileNameStored = fileNameStored;
        this.url = url;
    }
}
