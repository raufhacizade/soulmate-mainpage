package com.elte.soulmate.mainpage.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.elte.soulmate.mainpage.domain.LikeHistory} entity.
 */
public class LikeHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Long whoLikeId;

    @NotNull
    private Long likedPersonId;

    private LocalDate sendDate;

    private PersonDTO whoLiked;

    private PersonDTO likedPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWhoLikeId() {
        return whoLikeId;
    }

    public void setWhoLikeId(Long whoLikeId) {
        this.whoLikeId = whoLikeId;
    }

    public Long getLikedPersonId() {
        return likedPersonId;
    }

    public void setLikedPersonId(Long likedPersonId) {
        this.likedPersonId = likedPersonId;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    public PersonDTO getWhoLiked() {
        return whoLiked;
    }

    public void setWhoLiked(PersonDTO whoLiked) {
        this.whoLiked = whoLiked;
    }

    public PersonDTO getLikedPerson() {
        return likedPerson;
    }

    public void setLikedPerson(PersonDTO likedPerson) {
        this.likedPerson = likedPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikeHistoryDTO)) {
            return false;
        }

        LikeHistoryDTO likeHistoryDTO = (LikeHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, likeHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeHistoryDTO{" +
            "id=" + getId() +
            ", whoLikeId=" + getWhoLikeId() +
            ", likedPersonId=" + getLikedPersonId() +
            ", sendDate='" + getSendDate() + "'" +
            ", whoLiked=" + getWhoLiked() +
            ", likedPerson=" + getLikedPerson() +
            "}";
    }
}
