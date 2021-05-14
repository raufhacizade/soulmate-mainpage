package com.elte.soulmate.mainpage.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LikeHistory.
 */
@Entity
@Table(name = "like_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LikeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "who_like_id", nullable = false)
    private Long whoLikeId;

    @NotNull
    @Column(name = "liked_person_id", nullable = false)
    private Long likedPersonId;

    @Column(name = "send_date")
    private LocalDate sendDate;

    @ManyToOne
    private Person whoLiked;

    @ManyToOne
    private Person likedPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LikeHistory id(Long id) {
        this.id = id;
        return this;
    }

    public Long getWhoLikeId() {
        return this.whoLikeId;
    }

    public LikeHistory whoLikeId(Long whoLikeId) {
        this.whoLikeId = whoLikeId;
        return this;
    }

    public void setWhoLikeId(Long whoLikeId) {
        this.whoLikeId = whoLikeId;
    }

    public Long getLikedPersonId() {
        return this.likedPersonId;
    }

    public LikeHistory likedPersonId(Long likedPersonId) {
        this.likedPersonId = likedPersonId;
        return this;
    }

    public void setLikedPersonId(Long likedPersonId) {
        this.likedPersonId = likedPersonId;
    }

    public LocalDate getSendDate() {
        return this.sendDate;
    }

    public LikeHistory sendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    public Person getWhoLiked() {
        return this.whoLiked;
    }

    public LikeHistory whoLiked(Person person) {
        this.setWhoLiked(person);
        return this;
    }

    public void setWhoLiked(Person person) {
        this.whoLiked = person;
    }

    public Person getLikedPerson() {
        return this.likedPerson;
    }

    public LikeHistory likedPerson(Person person) {
        this.setLikedPerson(person);
        return this;
    }

    public void setLikedPerson(Person person) {
        this.likedPerson = person;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikeHistory)) {
            return false;
        }
        return id != null && id.equals(((LikeHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeHistory{" +
            "id=" + getId() +
            ", whoLikeId=" + getWhoLikeId() +
            ", likedPersonId=" + getLikedPersonId() +
            ", sendDate='" + getSendDate() + "'" +
            "}";
    }
}
